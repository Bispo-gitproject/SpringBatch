
package com.example.ProcessaAlunosAprovRepro.configuration;

import com.example.ProcessaAlunosAprovRepro.Entities.boletimAlunosEntity;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Bean
    public FlatFileItemReader<boletimAlunosEntity> reader() {
        return new FlatFileItemReaderBuilder<boletimAlunosEntity>()
                .name("alunoItemReader")
                .resource(new FileSystemResource("C:\\Users\\Boletim.csv"))
                .delimited()
                .names(new String[]{"nome", "classe", "materia", "anoCorrente", "numeroChamada", "notaBimestre1", "notaBimestre2", "notaBimestre3", "notaBimestre4"})
                .targetType(boletimAlunosEntity.class)
                .linesToSkip(1) // Pula a primeira linha (cabeçalho)
                .build();
    }

    @Bean
    public Step step1(
            @Qualifier("compositeItemWriter") ClassifierCompositeItemWriter<boletimAlunosEntity> compositeItemWriter
    ) {
        return stepBuilderFactory.get("step1")
                .<boletimAlunosEntity, boletimAlunosEntity>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(compositeItemWriter)
                .build();
    }


    @Bean
    public AlunoItemProcessor processor() {
        return new AlunoItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<boletimAlunosEntity> aprovadosWriter() {
        return new JdbcBatchItemWriterBuilder<boletimAlunosEntity>()
                .dataSource(dataSource)
                .sql("INSERT INTO aprovados (nome, classe, numeroChamada, mediaFinal, materia, ano, status) VALUES (:nome, :classe, :numeroChamada, :mediaFinal, :materia, :anoCorrente, :status)")
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<boletimAlunosEntity> reprovadosWriter() {
        return new JdbcBatchItemWriterBuilder<boletimAlunosEntity>()
                .dataSource(dataSource)
                .sql("INSERT INTO reprovados (nome, classe, numeroChamada, mediaFinal, materia, ano, status) VALUES (:nome, :classe, :numeroChamada, :mediaFinal, :materia, :anoCorrente, :status)")
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .build();
    }

    @Bean
    public ClassifierCompositeItemWriter<boletimAlunosEntity> compositeItemWriter(
            @Qualifier("aprovadosWriter") JdbcBatchItemWriter<boletimAlunosEntity> aprovadosWriter,
            @Qualifier("reprovadosWriter") JdbcBatchItemWriter<boletimAlunosEntity> reprovadosWriter
    ) {
        ClassifierCompositeItemWriter<boletimAlunosEntity> compositeItemWriter = new ClassifierCompositeItemWriter<>();
        compositeItemWriter.setClassifier(item -> {
            if ("Aprovado".equals(item.getStatus())) {
                return aprovadosWriter;
            } else {
                return reprovadosWriter;
            }
        });
        return compositeItemWriter;
    }


    @Bean
    public Job importAlunoJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importAlunoJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(step1)
                .build();
    }
}
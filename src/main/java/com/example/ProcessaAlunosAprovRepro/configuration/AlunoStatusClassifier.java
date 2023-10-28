package com.example.ProcessaAlunosAprovRepro.configuration;
import com.example.ProcessaAlunosAprovRepro.Entities.boletimAlunosEntity;
import org.springframework.batch.item.ItemWriter;
import org.springframework.classify.Classifier;
import org.springframework.stereotype.Component;

@Component
public class AlunoStatusClassifier implements Classifier<boletimAlunosEntity, ItemWriter<? super boletimAlunosEntity>> {
    private final ItemWriter<boletimAlunosEntity> aprovadosWriter;
    private final ItemWriter<boletimAlunosEntity> reprovadosWriter;

    public AlunoStatusClassifier(ItemWriter<boletimAlunosEntity> aprovadosWriter, ItemWriter<boletimAlunosEntity> reprovadosWriter) {
        this.aprovadosWriter = aprovadosWriter;
        this.reprovadosWriter = reprovadosWriter;
    }

    @Override
    public ItemWriter<? super boletimAlunosEntity> classify(boletimAlunosEntity aluno) {
        if ("Aprovado".equals(aluno.getStatus())) {
            return aprovadosWriter;
        } else {
            return reprovadosWriter;
        }
    }
}

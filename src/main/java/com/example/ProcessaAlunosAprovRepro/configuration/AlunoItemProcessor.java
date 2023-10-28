package com.example.ProcessaAlunosAprovRepro.configuration;

import com.example.ProcessaAlunosAprovRepro.Entities.boletimAlunosEntity;
import org.springframework.batch.item.ItemProcessor;

public class AlunoItemProcessor implements ItemProcessor<boletimAlunosEntity, boletimAlunosEntity> {
    @Override
    public boletimAlunosEntity process(boletimAlunosEntity aluno) {
        // Calcule a média das notas dos alunos
        double media = (aluno.getNotaBimestre1() + aluno.getNotaBimestre2() + aluno.getNotaBimestre3() + aluno.getNotaBimestre4()) / 4;

        // Determine se o aluno está aprovado ou reprovado
        if (media >= 6.0) {
            aluno.setMediaFinal(media);
            aluno.setStatus("Aprovado");
        } else {
            aluno.setMediaFinal(media);
            aluno.setStatus("Reprovado");
        }

        // Retorne o objeto atualizado
        return aluno;
    }
}

package com.example.ProcessaAlunosAprovRepro.Repositories;

import com.example.ProcessaAlunosAprovRepro.Entities.boletimAlunosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<boletimAlunosEntity, Long> {
}

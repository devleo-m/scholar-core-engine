package com.study.apisistemaeducacional.Service;

import com.study.apisistemaeducacional.Entity.AlunoEntity;
import java.util.List;

public interface AlunoService {
    AlunoEntity criarAluno(AlunoEntity aluno);
    AlunoEntity obterAlunoPorId(Long id);
    AlunoEntity atualizarAluno(Long id, AlunoEntity aluno);
    List<AlunoEntity> listarTodosAlunos();
    void deletarAluno(Long id);
}

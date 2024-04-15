package com.study.apisistemaeducacional.Service;

import com.study.apisistemaeducacional.Entity.TurmaEntity;

import java.util.List;

public interface TurmaService {
    TurmaEntity criarTurma(TurmaEntity turma);
    TurmaEntity obterTurmaPorId(Long id);
    TurmaEntity atualizarTurma(Long id, TurmaEntity turma);
    List<TurmaEntity> listarTodasTurmas();
    void deletarTurma(Long id);
}

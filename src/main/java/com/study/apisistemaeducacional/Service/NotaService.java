package com.study.apisistemaeducacional.Service;

import com.study.apisistemaeducacional.Entity.NotaEntity;

import java.util.List;

public interface NotaService {
    List<NotaEntity> listarNotaPorAluno(Long id);
    NotaEntity criarNota(NotaEntity nota);
    NotaEntity obterNotaPorId(Long id);
    NotaEntity atualizarNota(Long id, NotaEntity nota);
    void deletarNota(Long id);
}

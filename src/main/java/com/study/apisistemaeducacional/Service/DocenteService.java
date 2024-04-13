package com.study.apisistemaeducacional.Service;

import com.study.apisistemaeducacional.Entity.DocenteEntity;

import java.util.List;

public interface DocenteService {
    DocenteEntity criarDocente(DocenteEntity docente);
    DocenteEntity obterDocentePorId(Long id);
    DocenteEntity atualizarDocente(Long id, DocenteEntity docente);
    List<DocenteEntity> listarTodosDocentes();
    void deletarDocente(Long id);
}
package com.study.apisistemaeducacional.Service;

import com.study.apisistemaeducacional.Controller.dto.request.CriarDocenteRequest;
import com.study.apisistemaeducacional.Controller.dto.response.CriarDocenteResponse;
import com.study.apisistemaeducacional.Entity.DocenteEntity;

import java.util.List;

public interface DocenteService {
    CriarDocenteResponse criarDocente(CriarDocenteRequest request);
    public CriarDocenteResponse obterDocentePorId(Long id);
    DocenteEntity atualizarDocente(Long id, DocenteEntity docente);
    List<DocenteEntity> listarTodosDocentes();
    void deletarDocente(Long id);
}
package com.study.apisistemaeducacional.Service;

import com.study.apisistemaeducacional.Controller.dto.request.DocenteRequest;
import com.study.apisistemaeducacional.Controller.dto.response.DocenteResponse;
import com.study.apisistemaeducacional.Entity.DocenteEntity;

import java.util.List;

public interface DocenteService {
    DocenteResponse criarDocente(DocenteRequest request);
    DocenteResponse obterDocentePorId(Long id);
    DocenteEntity atualizarDocente(Long id, DocenteRequest request);
    List<DocenteResponse> listarTodosDocentes();
    void deletarDocente(Long id);
}
package com.study.apisistemaeducacional.Service;

import com.study.apisistemaeducacional.Controller.dto.request.NotaRequest;
import com.study.apisistemaeducacional.Controller.dto.response.NotaPorAlunoResponse;
import com.study.apisistemaeducacional.Controller.dto.response.NotaResponse;
import com.study.apisistemaeducacional.Controller.dto.response.NotaTotalResponse;

import java.util.List;

public interface NotaService {
    List<NotaPorAlunoResponse> listarNotaPorAluno(Long id);
    NotaResponse criarNota(NotaRequest request);
    NotaResponse obterNotaPorId(Long id);
    NotaResponse atualizarNota(Long id, NotaRequest request);
    void deletarNota(Long id);
    NotaTotalResponse calcularNotaTotal(Long idAluno);
}

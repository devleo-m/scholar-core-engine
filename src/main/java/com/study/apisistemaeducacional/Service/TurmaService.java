package com.study.apisistemaeducacional.Service;

import com.study.apisistemaeducacional.Controller.dto.request.TurmaRequest;
import com.study.apisistemaeducacional.Controller.dto.response.TurmaPorIdResponse;
import com.study.apisistemaeducacional.Controller.dto.response.TurmaResponse;

import java.util.List;

public interface TurmaService {
    TurmaResponse criarTurma(TurmaRequest request);
    TurmaPorIdResponse obterTurmaPorId(Long id);
    TurmaResponse atualizarTurma(Long id, TurmaRequest request);
    List<TurmaResponse> listarTodasTurmas();
    void deletarTurma(Long id);
}

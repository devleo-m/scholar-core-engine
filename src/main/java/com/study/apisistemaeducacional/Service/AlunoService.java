package com.study.apisistemaeducacional.Service;

import com.study.apisistemaeducacional.Controller.dto.request.AlunoRequest;
import com.study.apisistemaeducacional.Controller.dto.response.AlunoResponse;

import java.util.List;

public interface AlunoService {
    AlunoResponse criarAluno(AlunoRequest aluno);
    AlunoResponse obterAlunoPorId(Long id);
    AlunoResponse atualizarAluno(Long id, AlunoRequest request);
    List<AlunoResponse> listarTodosAlunos();
    void deletarAluno(Long id);
}

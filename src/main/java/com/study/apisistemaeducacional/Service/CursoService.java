package com.study.apisistemaeducacional.Service;

import com.study.apisistemaeducacional.Entity.CursoEntity;
import java.util.List;

public interface CursoService {
    CursoEntity criarCurso(CursoEntity curso);
    CursoEntity obterCursoPorId(Long id);
    CursoEntity atualizarCurso(Long id, CursoEntity curso);
    List<CursoEntity> listarTodosCursos();
    void deletarCurso(Long id);
}

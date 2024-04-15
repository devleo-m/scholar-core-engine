package com.study.apisistemaeducacional.Controller;

import com.study.apisistemaeducacional.Entity.CursoEntity;
import com.study.apisistemaeducacional.Service.CursoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cursos")
public class CursoController {
    private final CursoService cursoService;

    /**
     * Endpoint para criar um novo Curso.
     *
     * @param curso O Curso a ser adicionado.
     * @return O Curso criado.
     */
    @PostMapping
    public ResponseEntity<CursoEntity> criarCurso(@RequestBody CursoEntity curso) {
        log.info("POST /api/cursos -> Adicionando novo Curso: {}", curso);
        CursoEntity novocurso = cursoService.criarCurso(curso);
        log.debug("POST /api/cursos -> Novo Curso adicionado: {}", novocurso);
        return ResponseEntity.status(HttpStatus.CREATED).body(novocurso);
    }

    /**
     * Endpoint para obter um Curso pelo ID.
     *
     * @param id O ID do Curso a ser obtido.
     * @return O Curso encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CursoEntity> obterCursoPorId(@PathVariable Long id) {
        log.info("GET /api/cursos/{} -> Obtendo Curso por ID", id);
        CursoEntity curso = cursoService.obterCursoPorId(id);
        log.debug("GET /api/cursos/{} -> Curso encontrado: {}", id, curso);
        return ResponseEntity.status(HttpStatus.OK).body(curso);
    }

    /**
     * Endpoint para atualizar um Curso pelo ID.
     *
     * @param id O ID do Curso a ser obtido.
     * @param cursoAtualizado o Curso que sera atualizado
     * @return O Curso novo atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CursoEntity> atualizarCurso(@PathVariable Long id, @RequestBody CursoEntity cursoAtualizado) {
        log.info("PUT /api/cursos/{} -> Atualizando Curso com o ID: {}", id, id);
        CursoEntity curso = cursoService.atualizarCurso(id, cursoAtualizado);
        log.debug("PUT /api/cursos/{} -> Curso atualizado: {}", id, curso);
        return ResponseEntity.status(HttpStatus.OK).body(curso);
    }

    /**
     * Endpoint para deletar um Curso pelo ID.
     *
     * @param id O ID do Curso a ser deletado.
     * @return O Curso deletado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCurso(@PathVariable Long id) {
        log.info("DELETE /api/cursos/{} -> Deletando Curso com o ID: {}", id, id);
        cursoService.deletarCurso(id);
        log.debug("DELETE /api/cursos/{} -> Curso deletada com sucesso", id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para listar todos Cursos.
     *
     * @return todos os Cursos.
     */
    @GetMapping
    public ResponseEntity<List<CursoEntity>> listarCursos() {
        log.info("GET /api/cursos -> Listando todos os Cursos");
        List<CursoEntity> cursos = cursoService.listarTodosCursos();
        log.debug("GET /api/cursos -> Total de Cursos encontrados: {}", cursos.size());
        return ResponseEntity.status(HttpStatus.OK).body(cursos);
    }
}

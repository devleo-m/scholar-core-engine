package com.study.apisistemaeducacional.Controller;

import com.study.apisistemaeducacional.Entity.DocenteEntity;
import com.study.apisistemaeducacional.Entity.TurmaEntity;
import com.study.apisistemaeducacional.Service.TurmaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/turmas")
public class TurmaController {

    private final TurmaService turmaService;

    /**
     * Endpoint para criar um nova turma.
     *
     * @param turma para adicionar a nova turma.
     * @return A turma criado.
     */
    @PostMapping
    public ResponseEntity<TurmaEntity> criarTurma(@RequestBody TurmaEntity turma) {
        log.info("POST /api/Turmas -> Adicionando nova Turma: {}", turma);
        TurmaEntity novoTurma = turmaService.criarTurma(turma);
        log.debug("POST /api/Turmas -> Nova Turma adicionada: {}", novoTurma);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoTurma);
    }

    /**
     * Endpoint para obter uma turma pelo ID.
     *
     * @param id O ID da turma a ser obtido.
     * @return A turma encontrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TurmaEntity> obterTurmaPorId(@PathVariable Long id) {
        log.info("GET /api/turmas/{} -> Obtendo turma por ID", id);
        TurmaEntity turma = turmaService.obterTurmaPorId(id);
        log.debug("GET /api/turmas/{} -> turma encontrada: {}", id, turma);
        return ResponseEntity.status(HttpStatus.OK).body(turma);
    }

    /**
     * Endpoint para atualizar uma turma pelo ID.
     *
     * @param id O ID da turma a ser obtido.
     * @param turmaAtualizada parametro que sera atualizado
     * @return a turma nova atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TurmaEntity> atualizarTurma(@PathVariable Long id, @RequestBody TurmaEntity turmaAtualizada) {
        log.info("PUT /api/turmas/{} -> Atualizando Turma com o ID: {}", id, id);
        TurmaEntity turma = turmaService.atualizarTurma(id, turmaAtualizada);
        log.debug("PUT /api/turmas/{} -> Turma atualizado: {}", id, turma);
        return ResponseEntity.status(HttpStatus.OK).body(turma);
    }

    /**
     * Endpoint para deletar uma turma pelo ID.
     *
     * @param id O ID da turma a ser deletado.
     * @return a turma deletado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTurma(@PathVariable Long id) {
        log.info("DELETE /api/turmas/{} -> Deletando turma com o ID: {}", id, id);
        turmaService.deletarTurma(id);
        log.debug("DELETE /api/turmas/{} -> turma deletada com sucesso", id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para listar todas as turmas.
     *
     * @return todas turmas criadas.
     */
    @GetMapping
    public ResponseEntity<List<TurmaEntity>> listarDocentes() {
        log.info("GET /api/turmas -> Listando todas as turmas");
        List<TurmaEntity> turma = turmaService.listarTodasTurmas();
        log.debug("GET /api/turmas -> Total de turmas encontradas: {}", turma.size());
        return ResponseEntity.status(HttpStatus.OK).body(turma);
    }
}

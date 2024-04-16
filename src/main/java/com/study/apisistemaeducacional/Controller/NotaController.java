package com.study.apisistemaeducacional.Controller;

import com.study.apisistemaeducacional.Entity.NotaEntity;
import com.study.apisistemaeducacional.Service.NotaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notas")
public class NotaController {
    private final NotaService notaService;

    /**
     * Endpoint para listar todas as notas por aluno(id).
     *
     * @return todas as notas criadas por aluno.
     */
    @GetMapping("/aluno/{id}")
    public ResponseEntity<List<NotaEntity>> listarNotasPorAluno(@PathVariable Long id) {
        log.info("GET /api/notas -> Listando todas as notas por aluno");
        List<NotaEntity> notas = notaService.listarNotaPorAluno(id);
        log.debug("GET /api/notas -> Total de notas encontradas por aluno: {}", notas.size());
        return ResponseEntity.status(HttpStatus.OK).body(notas);
    }

    /**
     * Endpoint para criar um nova nota.
     *
     * @param nota para adicionar uma nova nota.
     * @return A nota criado.
     */
    @PostMapping
    public ResponseEntity<NotaEntity> criarNota(@RequestBody NotaEntity nota) {
        log.info("POST /api/notas -> Adicionando nova nota: {}", nota);
        NotaEntity novoNota = notaService.criarNota(nota);
        log.debug("POST /api/notas -> Nova nota adicionada: {}", novoNota);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoNota);
    }

    /**
     * Endpoint para obter uma nota pelo ID.
     *
     * @param id O ID da nota a ser obtido.
     * @return A nota encontrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NotaEntity> obterNotaPorId(@PathVariable Long id) {
        log.info("GET /api/notas/{} -> Obtendo nota por ID", id);
        NotaEntity nota = notaService.obterNotaPorId(id);
        log.debug("GET /api/notas/{} -> nota encontrada: {}", id, nota);
        return ResponseEntity.status(HttpStatus.OK).body(nota);
    }

    /**
     * Endpoint para atualizar uma nota pelo ID.
     *
     * @param id O ID da nota a ser obtido.
     * @param notaAtualizada parametro que sera atualizado
     * @return a nota nova atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<NotaEntity> atualizarTurma(@PathVariable Long id, @RequestBody NotaEntity notaAtualizada) {
        log.info("PUT /api/notas/{} -> Atualizando nota com o ID: {}", id, id);
        NotaEntity nota = notaService.atualizarNota(id, notaAtualizada);
        log.debug("PUT /api/notas/{} -> Nota atualizado: {}", id, nota);
        return ResponseEntity.status(HttpStatus.OK).body(nota);
    }

    /**
     * Endpoint para deletar uma nota pelo ID.
     *
     * @param id O ID da nota a ser deletado.
     * @return a nota deletado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarNota(@PathVariable Long id) {
        log.info("DELETE /api/notas/{} -> Deletando nota com o ID: {}", id, id);
        notaService.deletarNota(id);
        log.debug("DELETE /api/notas/{} -> Nota deletada com sucesso", id);
        return ResponseEntity.noContent().build();
    }
}

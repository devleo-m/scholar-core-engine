package com.study.apisistemaeducacional.Controller;

import com.study.apisistemaeducacional.Controller.dto.request.NotaRequest;
import com.study.apisistemaeducacional.Controller.dto.response.ApiResponse;
import com.study.apisistemaeducacional.Controller.dto.response.NotaPorAlunoResponse;
import com.study.apisistemaeducacional.Controller.dto.response.NotaResponse;
import com.study.apisistemaeducacional.Controller.dto.response.NotaTotalResponse;
import com.study.apisistemaeducacional.Entity.AlunoEntity;
import com.study.apisistemaeducacional.Exception.NotFoundException;
import com.study.apisistemaeducacional.Repository.AlunoRepository;
import com.study.apisistemaeducacional.Service.NotaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public ResponseEntity<List<NotaPorAlunoResponse>> listarNotasPorAlunos(@PathVariable Long id) {
        log.info("GET /api/notas -> Listando todas as notas por aluno");
        List<NotaPorAlunoResponse> notas = notaService.listarNotaPorAluno(id);
        log.debug("GET /api/notas -> Total de notas encontradas por aluno: {}", notas.size());
        return ResponseEntity.status(HttpStatus.OK).body(notas);
    }

    /**
     * Endpoint para criar um nova nota.
     *
     * @param  request adicionar uma nova nota.
     * @return A nota criado.
     */
    @PostMapping
    public ResponseEntity<NotaResponse> criarNota(@RequestBody NotaRequest request) {
        log.info("POST /api/notas -> Adicionando nova nota: {}", request);
        NotaResponse novoNota = notaService.criarNota(request);
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
    public ResponseEntity<NotaResponse> obterNotaPorId(@PathVariable Long id) {
        log.info("GET /api/notas/{} -> Obtendo nota por ID", id);
        NotaResponse nota = notaService.obterNotaPorId(id);
        log.debug("GET /api/notas/{} -> nota encontrada: {}", id, nota);
        return ResponseEntity.status(HttpStatus.OK).body(nota);
    }

    /**
     * Endpoint para atualizar uma nota pelo ID.
     *
     * @param id O ID da nota a ser obtido.
     * @param request parametro que sera atualizado
     * @return a nota nova atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<NotaResponse> atualizarTurma(@PathVariable Long id, @RequestBody NotaRequest request) {
        log.info("PUT /api/notas/{} -> Atualizando nota com o ID: {}", id, id);
        NotaResponse novaNota = notaService.atualizarNota(id, request);
        log.debug("PUT /api/notas/{} -> Nota atualizado: {}", id, novaNota);
        return ResponseEntity.status(HttpStatus.OK).body(novaNota);
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

    /**
     * Endpoint para calcular pontuacao do aluno.
     *
     * @param id O ID do aluno.
     * @return a nota do aluno calculada.
     */
    @GetMapping("/aluno/{id}/pontuacao")
    public ResponseEntity<NotaTotalResponse> calcularPontuacao(@PathVariable Long id) {
        log.info("GET /api/alunos/{}/pontuacao -> Calculando pontuação para o aluno", id);
        NotaTotalResponse pontuacao = notaService.calcularNotaTotal(id);
        log.debug("GET /api/alunos/{}/pontuacao -> Pontuação calculada: {}", id, pontuacao);
        return ResponseEntity.status(HttpStatus.OK).body(pontuacao);
    }

    //Metodos exclusivo dos alunos:

    /**
     * Endpoint para calcular pontuacao total do aluno.
     *
     * @param id O ID do aluno.
     * @return a nota do aluno calculada.
     */
    @GetMapping("/aluno/pontuacao/total/{id}")
    public ResponseEntity<NotaTotalResponse> calcularNotaTotalAluno(@PathVariable Long id) {
        log.info("GET /api/alunos/{}/pontuacao -> Calculando pontuação para o aluno", id);
        NotaTotalResponse pontuacao = notaService.calcularNotaTotalAluno(id);
        log.debug("GET /api/alunos/{}/pontuacao -> Pontuação calculada: {}", id, pontuacao);
        return ResponseEntity.status(HttpStatus.OK).body(pontuacao);
    }

    //listarNotaDoAluno
    /**
     * Endpoint para listar todas as notas por aluno(id).
     *
     * @return todas as notas criadas por aluno.
     */
    @GetMapping("/aluno/lista/notas/{id}")
    public ResponseEntity<List<NotaPorAlunoResponse>> listarNotaDoAluno(@PathVariable Long id) {
        log.info("GET /api/notas -> Listando todas as notas por aluno");
        List<NotaPorAlunoResponse> notas = notaService.listarNotaDoAluno(id);
        log.debug("GET /api/notas -> Total de notas encontradas por aluno: {}", notas.size());
        return ResponseEntity.status(HttpStatus.OK).body(notas);
    }
}

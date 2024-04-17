package com.study.apisistemaeducacional.Controller;

import com.study.apisistemaeducacional.Controller.dto.request.AlunoRequest;
import com.study.apisistemaeducacional.Controller.dto.response.AlunoResponse;
import com.study.apisistemaeducacional.Service.AlunoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alunos")
public class AlunoController {
    private final AlunoService alunoService;

    /**
     * Endpoint para criar um novo aluno.
     *
     * @param request O aluno a ser adicionado.
     * @return O aluno criado.
     */
    @PostMapping
    public ResponseEntity<AlunoResponse> criarAluno(@RequestBody AlunoRequest request) {
        log.info("POST /api/alunos -> Adicionando novo Aluno: {}", request);
        AlunoResponse novoaluno = alunoService.criarAluno(request);
        log.debug("POST /api/alunos -> Novo Aluno adicionado: {}", novoaluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoaluno);
    }

    /**
     * Endpoint para obter um aluno pelo ID.
     *
     * @param id O ID do aluno a ser obtido.
     * @return O aluno encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponse> obterAlunoPorId(@PathVariable Long id) {
        log.info("GET /api/alunos/{} -> Obtendo Aluno por ID", id);
        AlunoResponse aluno = alunoService.obterAlunoPorId(id);
        log.debug("GET /api/alunos/{} -> Aluno encontrado: {}", id, aluno);
        return ResponseEntity.status(HttpStatus.OK).body(aluno);
    }

    /**
     * Endpoint para atualizar um aluno pelo ID.
     *
     * @param id O ID do aluno a ser obtido.
     * @param request o aluno que sera atualizado
     * @return O aluno novo atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AlunoResponse> atualizarAluno(@PathVariable Long id, @RequestBody AlunoRequest request) {
        log.info("PUT /api/alunos/{} -> Atualizando Aluno com o ID: {}", id, id);
        AlunoResponse aluno = alunoService.atualizarAluno(id, request);
        log.debug("PUT /api/alunos/{} -> Aluno atualizado: {}", id, aluno);
        return ResponseEntity.status(HttpStatus.OK).body(aluno);
    }

    /**
     * Endpoint para deletar um aluno pelo ID.
     *
     * @param id O ID do aluno a ser deletado.
     * @return O aluno deletado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAluno(@PathVariable Long id) {
        log.info("DELETE /api/alunos/{} -> Deletando Aluno com o ID: {}", id, id);
        alunoService.deletarAluno(id);
        log.debug("DELETE /api/alunos/{} -> Aluno deletada com sucesso", id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para listar todos Alunos.
     *
     * @return todos os Alunos.
     */
    @GetMapping
    public ResponseEntity<List<AlunoResponse>> listarAlunos() {
        log.info("GET /api/alunos -> Listando todos os Alunos");
        List<AlunoResponse> alunos = alunoService.listarTodosAlunos();
        log.debug("GET /api/alunos -> Total de Alunos encontrados: {}", alunos.size());
        return ResponseEntity.status(HttpStatus.OK).body(alunos);
    }
}

package com.study.apisistemaeducacional.Controller;

import com.study.apisistemaeducacional.Controller.dto.request.DocenteRequest;
import com.study.apisistemaeducacional.Controller.dto.response.DocenteResponse;
import com.study.apisistemaeducacional.Entity.DocenteEntity;
import com.study.apisistemaeducacional.Service.DocenteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/docentes")
public class DocenteController {
    private final DocenteService docenteService;

    /**
     * Endpoint para criar um novo docente.
     *
     * @param request O docente a ser adicionado.
     * @return O docente criado.
     */
    @PostMapping
    public ResponseEntity<DocenteResponse> criarDocente(@RequestBody DocenteRequest request) {
        log.info("POST /api/docentes -> Adicionando novo Docente: {}", request);
        DocenteResponse response = docenteService.criarDocente(request);
        log.debug("POST /api/docentes -> Novo Docente adicionado: {}", response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Endpoint para obter um docente pelo ID.
     *
     * @param id O ID do docente a ser obtido.
     * @return O docente encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DocenteResponse> obterDocentePorId(@PathVariable Long id) {
        log.info("GET /api/docentes/{} -> Obtendo docentes por ID", id);
        DocenteResponse docente = docenteService.obterDocentePorId(id);
        log.debug("GET /api/docentes/{} -> docente encontrado: {}", id, docente);
        return ResponseEntity.status(HttpStatus.OK).body(docente);
    }

    /**
     * Endpoint para atualizar um docente pelo ID.
     *
     * @param id O ID do docente a ser obtido.
     * @param request o docente que sera atualizado
     * @return O docente novo atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DocenteResponse> atualizarDocente(@PathVariable Long id, @RequestBody DocenteRequest request) {
        log.info("PUT /api/docentes/{} -> Atualizando Docente com o ID: {}", id, id);
        DocenteResponse docente = docenteService.atualizarDocente(id, request);
        log.debug("PUT /api/docentes/{} -> Docente atualizado: {}", id, docente);
        return ResponseEntity.status(HttpStatus.OK).body(docente);
    }

    /**
     * Endpoint para deletar um docente pelo ID.
     *
     * @param id O ID do docente a ser deletado.
     * @return O docente deletado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDocente(@PathVariable Long id) {
        log.info("DELETE /api/docentes/{} -> Deletando Docente com o ID: {}", id, id);
        docenteService.deletarDocente(id);
        log.debug("DELETE /api/docentes/{} -> Docente deletada com sucesso", id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para listar todos docentes.
     *
     * @return todos os docentes.
     */
    @GetMapping
    public ResponseEntity<List<DocenteResponse>> listarDocentes() {
        log.info("GET /api/docentes -> Listando todos os Docentes!");
        List<DocenteResponse> usuariosDTO = docenteService.listarTodosDocentes();
        log.debug("GET /api/docentes -> Total de Docentes encontradas: {}", usuariosDTO.size());
        return ResponseEntity.status(HttpStatus.OK).body(usuariosDTO);
    }
}

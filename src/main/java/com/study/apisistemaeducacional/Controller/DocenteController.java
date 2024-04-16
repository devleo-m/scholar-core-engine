package com.study.apisistemaeducacional.Controller;

import com.study.apisistemaeducacional.Controller.dto.request.CriarDocenteRequest;
import com.study.apisistemaeducacional.Controller.dto.response.CriarDocenteResponse;
import com.study.apisistemaeducacional.Entity.DocenteEntity;
import com.study.apisistemaeducacional.Entity.UsuarioEntity;
import com.study.apisistemaeducacional.Repository.DocenteRepository;
import com.study.apisistemaeducacional.Repository.UsuarioRepository;
import com.study.apisistemaeducacional.Service.DocenteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/docentes")
public class DocenteController {
    private final DocenteService docenteService;
    private final UsuarioRepository usuarioRepository;

    /**
     * Endpoint para criar um novo docente.
     *
     * @param request O docente a ser adicionado.
     * @return O docente criado.
     */
    @PostMapping
    public ResponseEntity<CriarDocenteResponse> criarDocente(@RequestBody CriarDocenteRequest request) {
        log.info("POST /api/docentes -> Adicionando novo Docente: {}", request);

        CriarDocenteResponse response = docenteService.criarDocente(request);

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
    public ResponseEntity<CriarDocenteResponse> obterDocentePorId(@PathVariable Long id) {
        log.info("GET /api/docentes/{} -> Obtendo docentes por ID", id);
        CriarDocenteResponse docente = docenteService.obterDocentePorId(id);
        log.debug("GET /api/docentes/{} -> docente encontrado: {}", id, docente);
        return ResponseEntity.status(HttpStatus.OK).body(docente);
    }


    /**
     * Endpoint para atualizar um docente pelo ID.
     *
     * @param id O ID do docente a ser obtido.
     * @param docenteAtualizado o docente que sera atualizado
     * @return O docente novo atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DocenteEntity> atualizarDocente(@PathVariable Long id, @RequestBody DocenteEntity docenteAtualizado) {
        log.info("PUT /api/docentes/{} -> Atualizando Docente com o ID: {}", id, id);
        DocenteEntity docente = docenteService.atualizarDocente(id, docenteAtualizado);
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
    public ResponseEntity<List<DocenteEntity>> listarDocentes() {
        log.info("GET /api/disciplinas -> Listando todas as disciplinas");
        List<DocenteEntity> docentes = docenteService.listarTodosDocentes();
        log.debug("GET /api/disciplinas -> Total de disciplinas encontradas: {}", docentes.size());
        return ResponseEntity.status(HttpStatus.OK).body(docentes);
    }
}

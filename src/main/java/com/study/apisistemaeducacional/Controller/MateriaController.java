package com.study.apisistemaeducacional.Controller;

import com.study.apisistemaeducacional.Controller.dto.request.MateriaRequest;
import com.study.apisistemaeducacional.Controller.dto.response.MateriaResponse;
import com.study.apisistemaeducacional.Entity.MateriaEntity;
import com.study.apisistemaeducacional.Entity.TurmaEntity;
import com.study.apisistemaeducacional.Service.MateriaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/materias")
public class MateriaController {

    private final MateriaService materiaService;

    /**
     * Endpoint para criar um nova materia.
     *
     * @param request para adicionar a nova materia.
     * @return A materia criado.
     */
    @PostMapping
    public ResponseEntity<MateriaResponse> criarMateria(@RequestBody MateriaRequest request) {
        log.info("POST /api/Materias -> Adicionando nova Materia: {}", request);
        MateriaResponse novaMateria = materiaService.criarMateria(request);
        log.debug("POST /api/Materias -> Nova Materia adicionada: {}", novaMateria);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaMateria);
    }

    /**
     * Endpoint para obter uma Materia pelo ID.
     *
     * @param id O ID da Materia a ser obtido.
     * @return A Materia encontrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MateriaResponse> obterMateriaPorId(@PathVariable Long id) {
        log.info("GET /api/Materias/{} -> Obtendo Materia por ID", id);
        MateriaResponse materia = materiaService.obterMateriaPorId(id);
        log.debug("GET /api/Materias/{} -> Materia encontrada: {}", id, materia);
        return ResponseEntity.status(HttpStatus.OK).body(materia);
    }

    /**
     * Endpoint para atualizar uma materia pelo ID.
     *
     * @param id O ID da materia a ser obtido.
     * @param request parametro que sera atualizado
     * @return a materia nova atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MateriaResponse> atualizarMateria(@PathVariable Long id, @RequestBody MateriaRequest request) {
        log.info("PUT /api/turmas/{} -> Atualizando Turma com o ID: {}", id, id);
        MateriaResponse novaMateria = materiaService.atualizarMateria(id, request);
        log.debug("PUT /api/turmas/{} -> Turma atualizado: {}", id, novaMateria);
        return ResponseEntity.status(HttpStatus.OK).body(novaMateria);
    }

    /**
     * Endpoint para deletar uma materia pelo ID.
     *
     * @param id O ID da materia a ser deletado.
     * @return a materia deletado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMateria(@PathVariable Long id) {
        log.info("DELETE /api/materias/{} -> Deletando materia com o ID: {}", id, id);
        materiaService.deletarMateria(id);
        log.debug("DELETE /api/materias/{} -> materia deletada com sucesso", id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para listar todas as materias.
     *
     * @return todas materias criadas.
     */
    @GetMapping
    public ResponseEntity<List<MateriaResponse>> listarDocentes() {
        log.info("GET /api/materias -> Listando todas as materias");
        List<MateriaResponse> materias = materiaService.listarTodasMaterias();
        log.debug("GET /api/materias -> Total de materias encontradas: {}", materias.size());
        return ResponseEntity.status(HttpStatus.OK).body(materias);
    }
}

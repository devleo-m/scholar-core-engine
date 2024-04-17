package com.study.apisistemaeducacional.Service.Impl;

import com.study.apisistemaeducacional.Controller.dto.request.MateriaRequest;
import com.study.apisistemaeducacional.Controller.dto.request.TurmaRequest;
import com.study.apisistemaeducacional.Controller.dto.response.MateriaResponse;
import com.study.apisistemaeducacional.Controller.dto.response.TurmaResponse;
import com.study.apisistemaeducacional.Entity.CursoEntity;
import com.study.apisistemaeducacional.Entity.MateriaEntity;
import com.study.apisistemaeducacional.Entity.TurmaEntity;
import com.study.apisistemaeducacional.Exception.NotFoundException;
import com.study.apisistemaeducacional.Repository.CursoRepository;
import com.study.apisistemaeducacional.Repository.MateriaRepository;
import com.study.apisistemaeducacional.Service.MateriaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MateriaServiceImpl implements MateriaService {
    private final MateriaRepository materiaRepository;
    private final CursoRepository cursoRepository;

    /**
     * Método para criar materia.
     *
     * @param request a ser adicionado.
     * @return materia criada.
     */
    @Override
    public MateriaResponse criarMateria(MateriaRequest request) {
        log.info("Criando nova materia: {}", request);

        CursoEntity curso = cursoRepository.findById(request.cursoId())
                .orElseThrow(() -> new NotFoundException("Curso nao encontrado"));

        MateriaEntity materiaEntity = new MateriaEntity();
            materiaEntity.setNome(request.nomeMateria());
            materiaEntity.setId_curso(curso);

        MateriaEntity turma = materiaRepository.save(materiaEntity);

        MateriaResponse response = new MateriaResponse(
                turma.getId(),
                turma.getNome(),
                turma.getId_curso().getNome()
        );
        return response;
    }

    /**
     * Método para obter materia pelo ID.
     *
     * @param id ID da materia a ser obtido.
     * @return A materia encontrada.
     * @throws NotFoundException Se a materia não for encontrado.
     */
    @Override
    public MateriaResponse obterMateriaPorId(Long id) {
        log.info("Obtendo materia por ID: {}", id);
        Optional<MateriaEntity> materiaOptional = materiaRepository.findById(id);
        MateriaEntity materia = materiaOptional.orElseThrow(() -> {
            log.warn("Materia não encontrada pelo ID: {}", id);
            return new NotFoundException("Materia não encontrada com o ID: " + id);
        });
        MateriaResponse response = new MateriaResponse(
                materia.getId(),
                materia.getNome(),
                materia.getId_curso().getNome()
        );
        return response;
    }

    /**
     * Método para atualizar materia pelo id.
     *
     * @param id id da materia para ser atualizado
     * @param request atributos novos da materia
     * @verificarExistenciaDocente metodo para verificar se a materia existe
     * @return a materia atualizada
     */
    @Override
    public MateriaResponse atualizarMateria(Long id, MateriaRequest request) {
        log.info("Atualizando materia pelo ID: {}", id);
        verificarExistenciaMateria(id);

        CursoEntity curso = cursoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Curso nao encontrado"));

        MateriaEntity materiaEntity = materiaRepository.findById(id)
                .orElseThrow(() -> {
                        log.warn("Aluno não encontrado pelo ID: {}", id);
                            throw new NotFoundException("Aluno não encontrado com o ID: " + id);
                });

        materiaEntity.setNome(request.nomeMateria());
        materiaEntity.setId_curso(curso);

        MateriaEntity materia = materiaRepository.save(materiaEntity);

        return new MateriaResponse(
                materia.getId(),
                materia.getNome(),
                materia.getId_curso().getNome()
        );
    }

    /**
     * Método para listar todas as materias.
     *
     * @return Lista de todas as materias.
     */
    @Override
    public List<MateriaResponse> listarTodasMaterias() {
        log.info("Listando todas as materias!");
        List<MateriaEntity> materias = materiaRepository.findAll();
        List<MateriaResponse> materiaResponses = materias.stream()
                .map(materia -> new MateriaResponse(
                        materia.getId(),
                        materia.getNome(),
                        materia.getId_curso().getNome()))
                .collect(Collectors.toList());
        return materiaResponses;
    }

    /**
     * Deleta uma materia existente.
     *
     * @verificarExistenciaDocente metodo para verificar se a materia existe
     * @param id ID da materia a ser deletado.
     */
    @Override
    public void deletarMateria(Long id) {
        log.info("Deletando materia com o ID: {}", id);
        verificarExistenciaMateria(id);
        materiaRepository.deleteById(id);
    }

    /**
     * Verifica se a materia existe.
     *
     * @param id ID da materia a ser verificado.
     * @throws NotFoundException Se a materia não for encontrado.
     */
    private void verificarExistenciaMateria(Long id) {
        if (!materiaRepository.existsById(id)) {
            log.warn("materia não encontrada com o ID: {}", id);
            throw new NotFoundException("materia não encontrada com o ID: " + id);
        } else {
            log.info("materia encontrada com o ID: {}", id);
        }
    }
}

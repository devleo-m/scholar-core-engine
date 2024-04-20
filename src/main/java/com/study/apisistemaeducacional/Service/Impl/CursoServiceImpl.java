package com.study.apisistemaeducacional.Service.Impl;

import com.study.apisistemaeducacional.Entity.CursoEntity;
import com.study.apisistemaeducacional.Exception.NotFoundException;
import com.study.apisistemaeducacional.Repository.CursoRepository;
import com.study.apisistemaeducacional.Service.CursoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CursoServiceImpl implements CursoService {
    private final CursoRepository cursoRepository;

    /**
     * Método para criar curso.
     * @param curso a ser adicionado.
     * @return curso criado.
     */
    @Override
    public CursoEntity criarCurso(CursoEntity curso) {
        log.info("Criando novo Curso: {}", curso);
        return cursoRepository.save(curso);
    }

    /**
     * Método para obter Curso pelo ID.
     * @param id ID do Curso a ser obtido.
     * @return O Curso encontrado.
     * @throws NotFoundException Se o Curso não for encontrado.
     */
    @Override
    public CursoEntity obterCursoPorId(Long id) {
        log.info("Obtendo Curso por ID: {}", id);
        Optional<CursoEntity> CursoOptional = cursoRepository.findById(id);
        return CursoOptional.orElseThrow(() -> {
            log.warn("Curso não encontrado pelo ID: {}", id);
            return new NotFoundException("Curso não encontrado com o ID: " + id);
        });
    }

    /**
     * Método para atualizar Curso pelo id.
     * @param id id do Curso para ser atualizado
     * @param curso novos dados do Curso
     * @verificarExistenciaCurso metodo para verificar se o Curso existe
     * @return o Curso atualizado.
     */
    @Override
    public CursoEntity atualizarCurso(Long id, CursoEntity curso) {
        log.info("Atualizando Curso pelo ID: {}", id);
        verificarExistenciaCurso(id);
        curso.setId(id);
        return cursoRepository.save(curso);
    }

    /**
     * Método para listar todos os Cursos.
     * @return Lista de todos os Cursos.
     */
    @Override
    public List<CursoEntity> listarTodosCursos() {
        log.info("Listando todos os Cursos!");
        return cursoRepository.findAll();
    }

    /**
     * Deleta um Curso existente.
     * @verificarExistenciaDocente metodo para verificar se o Curso existe
     * @param id ID do Curso a ser deletado.
     */
    @Override
    public void deletarCurso(Long id) {
        log.info("Deletando Curso com o ID: {}", id);
        verificarExistenciaCurso(id);
        cursoRepository.deleteById(id);
    }

    /**
     * Verifica se o Curso existe.
     * @param id ID do Curso a ser verificado.
     * @throws NotFoundException Se o Curso não for encontrado.
     */
    private void verificarExistenciaCurso(Long id) {
        if (!cursoRepository.existsById(id)) {
            log.warn("Curso não encontrado com o ID: {}", id);
            throw new NotFoundException("Curso não encontrado com o ID: " + id);
        } else {
            log.info("Curso encontrado com o ID: {}", id);
        }
    }
}

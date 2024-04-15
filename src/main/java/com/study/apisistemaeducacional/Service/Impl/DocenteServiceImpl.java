package com.study.apisistemaeducacional.Service.Impl;

import com.study.apisistemaeducacional.Entity.DocenteEntity;
import com.study.apisistemaeducacional.Exception.NotFoundException;
import com.study.apisistemaeducacional.Repository.DocenteRepository;
import com.study.apisistemaeducacional.Security.TokenService;
import com.study.apisistemaeducacional.Service.DocenteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class DocenteServiceImpl implements DocenteService {

    private final DocenteRepository docenteRepository;
    private final TokenService tokenService;

    /**
     * Método para criar Docente.
     * @param docente O docente a ser adicionado.
     * @return docente criado.
     */
    @Override
    public DocenteEntity criarDocente(DocenteEntity docente) {
        log.info("Criando novo docente: {}", docente);
        return docenteRepository.save(docente);
    }

    /**
     * Método para obter Docente pelo ID.
     * @param id ID do Docente a ser obtido.
     * @return O docente encontrado.
     * @throws NotFoundException Se o aluno não for encontrado.
     */
    @Override
    public DocenteEntity obterDocentePorId(Long id) {
        log.info("Obtendo aluno por ID: {}", id);
        Optional<DocenteEntity> DocenteOptional = docenteRepository.findById(id);
        return DocenteOptional.orElseThrow(() -> {
            log.warn("Docente não encontrado pelo ID: {}", id);
            return new NotFoundException("Docente não encontrado com o ID: " + id);
        });
    }

    /**
     * Método para listar todos os Docentes.
     * @return Lista de todos os docentes.
     */
    @Override
    public List<DocenteEntity> listarTodosDocentes() {
        log.info("Listando todos os Docentes!");
        return docenteRepository.findAll();
    }

    /**
     * Método para atualizar Docentes pelo id.
     * @param id id do docente para ser atualizado
     * @param docente novos dados do docente
     *.@verificarExistenciaDocente metodo para verificar se o docente existe
     * @return o docente atualizado.
     */
    public DocenteEntity atualizarDocente(Long id, DocenteEntity docente){
        log.info("Atualizando docente pelo ID: {}", id);
        verificarExistenciaDocente(id);
        docente.setId(id);
        return docenteRepository.save(docente);
    }

    /**
     * Deleta um aluno existente.
     * .@verificarExistenciaDocente metodo para verificar se o docente existe
     *
     * @param id ID do aluno a ser deletado.
     * @return
     */
    @Override
    public void deletarDocente(Long id) {
        log.info("Deletando aluno com o ID: {}", id);
        verificarExistenciaDocente(id);
        docenteRepository.deleteById(id);
    }

    /**
     * Verifica se o docente existe.
     * @param id ID do docente a ser verificado.
     * @throws NotFoundException Se o docente não for encontrado.
     */
    private void verificarExistenciaDocente(Long id) {
        if (!docenteRepository.existsById(id)) {
            log.warn("Aluno não encontrado com o ID: {}", id);
            throw new NotFoundException("Aluno não encontrado com o ID: " + id);
        } else {
            log.info("Docente encontrado com o ID: {}", id);
        }
    }
}
package com.study.apisistemaeducacional.Service.Impl;

import com.study.apisistemaeducacional.Entity.TurmaEntity;
import com.study.apisistemaeducacional.Exception.NotFoundException;
import com.study.apisistemaeducacional.Repository.TurmaRepository;
import com.study.apisistemaeducacional.Service.TurmaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TurmaServiceImpl implements TurmaService {
    private final TurmaRepository turmaRepository;

    /**
     * Método para criar Turma.
     *
     * @param turma a ser adicionado.
     * @return Turma criada.
     */
    @Override
    public TurmaEntity criarTurma(TurmaEntity turma) {
        log.info("Criando nova turma: {}", turma);
        return turmaRepository.save(turma);
    }

    /**
     * Método para obter Turma pelo ID.
     *
     * @param id ID da Turma a ser obtido.
     * @return A Turma encontrada.
     * @throws NotFoundException Se a Turma não for encontrado.
     */
    @Override
    public TurmaEntity obterTurmaPorId(Long id) {
        log.info("Obtendo turma por ID: {}", id);
        Optional<TurmaEntity> turmaOptional = turmaRepository.findById(id);
        return turmaOptional.orElseThrow(() -> {
            log.warn("turma não encontrada pelo ID: {}", id);
            return new NotFoundException("turma não encontrada com o ID: " + id);
        });
    }

    /**
     * Método para atualizar Turmas pelo id.
     *
     * @param id id da Turma para ser atualizado
     * @param turma dados novos da Turma
     * @verificarExistenciaDocente metodo para verificar se a Turma existe
     * @return a Turma atualizada
     */
    @Override
    public TurmaEntity atualizarTurma(Long id, TurmaEntity turma) {
        log.info("Atualizando turma pelo ID: {}", id);
        verificarExistenciaTurma(id);
        turma.setId(id);
        return turmaRepository.save(turma);
    }

    /**
     * Método para listar todas as Turmas.
     *
     * @return Lista de todas as Turma.
     */
    @Override
    public List<TurmaEntity> listarTodasTurmas() {
        log.info("Listando todas as Turmas!");
        return turmaRepository.findAll();
    }

    /**
     * Deleta uma Turma existente.
     *
     * @verificarExistenciaDocente metodo para verificar se a Turma existe
     * @param id ID da Turma a ser deletado.
     */
    @Override
    public void deletarTurma(Long id) {
        log.info("Deletando Turma com o ID: {}", id);
        verificarExistenciaTurma(id);
        turmaRepository.deleteById(id);
    }

    /**
     * Verifica se a Turma existe.
     *
     * @param id ID da Turma a ser verificado.
     * @throws NotFoundException Se a Turma não for encontrado.
     */
    private void verificarExistenciaTurma(Long id) {
        if (!turmaRepository.existsById(id)) {
            log.warn("Turma não encontrada com o ID: {}", id);
            throw new NotFoundException("Turma não encontrada com o ID: " + id);
        } else {
            log.info("Turma encontrada com o ID: {}", id);
        }
    }
}

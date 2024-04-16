package com.study.apisistemaeducacional.Service.Impl;

import com.study.apisistemaeducacional.Entity.NotaEntity;
import com.study.apisistemaeducacional.Exception.NotFoundException;
import com.study.apisistemaeducacional.Repository.NotaRepository;
import com.study.apisistemaeducacional.Service.NotaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotaServiceImpl implements NotaService {
    private final NotaRepository notaRepository;

    /**
     * Método para listar todas as Notas por Aluno.
     *
     * @return Lista de todas as Turma.
     */
    @Override
    public List<NotaEntity> listarNotaPorAluno(Long id) {
        log.info("Listando todas as notas para o aluno com o ID: {}", id);
        return notaRepository.findAllByIdAluno(id);
    }

    /**
     * Método para criar Nota.
     *
     * @param nota a ser adicionado.
     * @return Nota criada.
     */
    @Override
    public NotaEntity criarNota(NotaEntity nota) {
        log.info("Criando nova nota: {}", nota);
        return notaRepository.save(nota);
    }

    /**
     * Método para obter Nota pelo ID.
     *
     * @param id ID da Nota a ser obtido.
     * @return A Nota encontrada.
     * @throws NotFoundException Se a Nota não for encontrado.
     */
    @Override
    public NotaEntity obterNotaPorId(Long id) {
        log.info("Obtendo nota por ID: {}", id);
        Optional<NotaEntity> notaOptional = notaRepository.findById(id);
        return notaOptional.orElseThrow(() -> {
            log.warn("Nota não encontrada pelo ID: {}", id);
            return new NotFoundException("Nota não encontrada com o ID: " + id);
        });
    }

    /**
     * Método para atualizar Nota pelo id.
     *
     * @param id id da Nota para ser atualizado
     * @param nota dados novos da Nota
     * @verificarExistenciaNota metodo para verificar se a Nota existe
     * @return a Nota atualizada
     */
    @Override
    public NotaEntity atualizarNota(Long id, NotaEntity nota) {
        log.info("Atualizando turma pelo ID: {}", id);
        verificarExistenciaNota(id);
        nota.setId(id);
        return notaRepository.save(nota);
    }

    /**
     * Deleta uma Nota existente.
     *
     * @verificarExistenciaNota metodo para verificar se a Nota existe
     * @param id ID da Nota a ser deletado.
     */
    @Override
    public void deletarNota(Long id) {
        log.info("Deletando Turma com o ID: {}", id);
        verificarExistenciaNota(id);
        notaRepository.deleteById(id);
    }

    /**
     * Verifica se a Nota existe.
     *
     * @param id ID da Nota a ser verificada.
     * @throws NotFoundException Se a Nota não for encontrada.
     */
    private void verificarExistenciaNota(Long id) {
        if (!notaRepository.existsById(id)) {
            log.warn("Nota não encontrada com o ID: {}", id);
            throw new NotFoundException("Nota não encontrada com o ID: " + id);
        } else {
            log.info("Nota encontrada com o ID: {}", id);
        }
    }
}

package com.study.apisistemaeducacional.Service.Impl;

import com.study.apisistemaeducacional.Entity.AlunoEntity;
import com.study.apisistemaeducacional.Exception.NotFoundException;
import com.study.apisistemaeducacional.Repository.AlunoRepository;
import com.study.apisistemaeducacional.Service.AlunoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AlunoServiceImpl implements AlunoService {
    private final AlunoRepository alunoRepository;

    /**
     * Método para criar aluno.
     * @param aluno a ser adicionado.
     * @return aluno criado.
     */
    @Override
    public AlunoEntity criarAluno(AlunoEntity aluno) {
        log.info("Criando novo Aluno: {}", aluno);
        return alunoRepository.save(aluno);
    }

    /**
     * Método para obter Docente pelo ID.
     * @param id ID do aluno a ser obtido.
     * @return O aluno encontrado.
     * @throws NotFoundException Se o aluno não for encontrado.
     */
    @Override
    public AlunoEntity obterAlunoPorId(Long id) {
        log.info("Obtendo aluno por ID: {}", id);
        Optional<AlunoEntity> AlunoOptional = alunoRepository.findById(id);
        return AlunoOptional.orElseThrow(() -> {
            log.warn("Docente não encontrado pelo ID: {}", id);
            return new NotFoundException("Docente não encontrado com o ID: " + id);
        });
    }

    /**
     * Método para atualizar aluno pelo id.
     * @param id id do aluno para ser atualizado
     * @param aluno novos dados do aluno
     * @verificarExistenciaAluno metodo para verificar se o aluno existe
     * @return o aluno atualizado.
     */
    @Override
    public AlunoEntity atualizarAluno(Long id, AlunoEntity aluno) {
        log.info("Atualizando docente pelo ID: {}", id);
        verificarExistenciaAluno(id);
        aluno.setId(id);
        return alunoRepository.save(aluno);
    }

    /**
     * Método para listar todos os alunos.
     * @return Lista de todos os alunos.
     */
    @Override
    public List<AlunoEntity> listarTodosAlunos() {
        log.info("Listando todos os Docentes!");
        return alunoRepository.findAll();
    }

    /**
     * Deleta um aluno existente.
     * @verificarExistenciaDocente metodo para verificar se o aluno existe
     * @param id ID do aluno a ser deletado.
     */
    @Override
    public void deletarAluno(Long id) {
        log.info("Deletando aluno com o ID: {}", id);
        verificarExistenciaAluno(id);
        alunoRepository.deleteById(id);
    }

    /**
     * Verifica se o docente existe.
     * @param id ID do docente a ser verificado.
     * @throws NotFoundException Se o docente não for encontrado.
     */
    private void verificarExistenciaAluno(Long id) {
        if (!alunoRepository.existsById(id)) {
            log.warn("Aluno não encontrado com o ID: {}", id);
            throw new NotFoundException("Aluno não encontrado com o ID: " + id);
        } else {
            log.info("Aluno encontrado com o ID: {}", id);
        }
    }
}

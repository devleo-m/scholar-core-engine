package com.study.apisistemaeducacional.Service.Impl;

import com.study.apisistemaeducacional.Controller.dto.request.TurmaRequest;
import com.study.apisistemaeducacional.Controller.dto.response.TurmaPorIdResponse;
import com.study.apisistemaeducacional.Controller.dto.response.TurmaResponse;
import com.study.apisistemaeducacional.Entity.AlunoEntity;
import com.study.apisistemaeducacional.Entity.CursoEntity;
import com.study.apisistemaeducacional.Entity.DocenteEntity;
import com.study.apisistemaeducacional.Entity.TurmaEntity;
import com.study.apisistemaeducacional.Exception.NotFoundException;
import com.study.apisistemaeducacional.Repository.CursoRepository;
import com.study.apisistemaeducacional.Repository.DocenteRepository;
import com.study.apisistemaeducacional.Repository.TurmaRepository;
import com.study.apisistemaeducacional.Service.TurmaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class TurmaServiceImpl implements TurmaService {
    private final TurmaRepository turmaRepository;
    private final DocenteRepository docenteRepository;
    private final CursoRepository cursoRepository;

    /**
     * Método para criar Turma.
     *
     * @param request a ser adicionado.
     * @return Turma criada.
     */
    @Override
    public TurmaResponse criarTurma(TurmaRequest request) {
        log.info("Criando nova turma: {}", request);

        DocenteEntity docente = docenteRepository.findById(request.docenteId())
                .orElseThrow(() -> new NotFoundException("Docente nao encontrado"));

        CursoEntity curso = cursoRepository.findById(request.cursoId())
                .orElseThrow(() -> new NotFoundException("Curso nao encontrado"));

        TurmaEntity turmaEntity = new TurmaEntity();
        turmaEntity.setNome(request.nome());
        turmaEntity.setDocente(docente);
        turmaEntity.setCurso(curso);

        TurmaEntity turma = turmaRepository.save(turmaEntity);

        TurmaResponse response = new TurmaResponse(
                turma.getId(),
                turma.getNome(),
                turma.getDocente().getNome(),
                turma.getCurso().getNome()
        );
        return response;
    }

    /**
     * Método para obter Turma pelo ID.
     *
     * @param id ID da Turma a ser obtido.
     * @return A Turma encontrada.
     * @throws NotFoundException Se a Turma não for encontrado.
     */
    @Override
    public TurmaPorIdResponse obterTurmaPorId(Long id) {
        log.info("Obtendo turma por ID: {}", id);
        Optional<TurmaEntity> TurmaOptional = turmaRepository.findById(id);
        TurmaEntity turma = TurmaOptional.orElseThrow(()-> {
            log.warn("Turma não encontrada pelo ID: {}", id);
            return new NotFoundException("Turma não encontrada com o ID: " + id);
        });

        List<AlunoEntity> alunos = turma.getAlunos();

        TurmaPorIdResponse turmaResponse = new TurmaPorIdResponse(
                turma.getId(),
                turma.getNome(),
                turma.getDocente().getNome(),
                turma.getCurso().getNome(),
                (long) alunos.size()
        );
        return turmaResponse;
    }

    /**
     * Método para atualizar Turmas pelo id.
     *
     * @param id id da Turma para ser atualizado
     * @param request dados novos da Turma
     * @verificarExistenciaDocente metodo para verificar se a Turma existe
     * @return a Turma atualizada
     */
    @Override
    public TurmaResponse atualizarTurma(Long id, TurmaRequest request) {
        log.info("Atualizando turma pelo ID: {}", id);
        verificarExistenciaTurma(id);

        DocenteEntity docente = docenteRepository.findById(request.docenteId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        CursoEntity curso = cursoRepository.findById(request.cursoId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        TurmaEntity turma = turmaRepository.findById(id)
                .orElseThrow(()-> {
                    log.warn("Turma não encontrado pelo ID: {}", id);
                    throw new NotFoundException("Turma não encontrado com o ID: " + id);
                });
        turma.setNome(request.nome());
        turma.setDocente(docente);
        turma.setCurso(curso);

        TurmaEntity turmaAtualizada = turmaRepository.save(turma);
        return new TurmaResponse(
                turmaAtualizada.getId(),
                turmaAtualizada.getNome(),
                turmaAtualizada.getDocente().getNome(),
                turmaAtualizada.getCurso().getNome()
        );
    }

    /**
     * Método para listar todas as Turmas.
     *
     * @return Lista de todas as Turma.
     */
    @Override
    public List<TurmaResponse> listarTodasTurmas() {
        log.info("Listando todas as Turmas!");
        List<TurmaEntity> turmas = turmaRepository.findAll();
        List<TurmaResponse> turmasResponse = turmas.stream()
                .map(turma -> new TurmaResponse(
                        turma.getId(),
                        turma.getNome(),
                        turma.getDocente().getNome(),
                        turma.getCurso().getNome()))
                .collect(Collectors.toList());
        return turmasResponse;
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

package com.study.apisistemaeducacional.Service.Impl;

import com.study.apisistemaeducacional.Controller.dto.request.NotaRequest;
import com.study.apisistemaeducacional.Controller.dto.response.NotaPorAlunoResponse;
import com.study.apisistemaeducacional.Controller.dto.response.NotaResponse;
import com.study.apisistemaeducacional.Controller.dto.response.NotaTotalResponse;
import com.study.apisistemaeducacional.Entity.*;
import com.study.apisistemaeducacional.Exception.NotFoundException;
import com.study.apisistemaeducacional.Repository.*;
import com.study.apisistemaeducacional.Service.NotaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotaServiceImpl implements NotaService {
    private final NotaRepository notaRepository;
    private final AlunoRepository alunoRepository;
    private final DocenteRepository docenteRepository;
    private final MateriaRepository materiaRepository;

    /**
     * Lista todas as notas associadas a um aluno específico.
     *
     * @param id O identificador único do aluno.
     * @return Uma lista de {@code NotaPorAlunoResponse} contendo as notas do aluno.
     */
    @Transactional(rollbackFor = NotFoundException.class)
    @Override
    public List<NotaPorAlunoResponse> listarNotaPorAluno(Long id) {
        log.info("Listando todas as notas para o aluno com o ID: {}", id);
        List<NotaEntity> notas = notaRepository.findAllByIdAluno(id);

        if (notas.isEmpty()) {
            log.warn("Nenhuma nota encontrada para o aluno com o ID: {}", id);
            throw new NotFoundException("Nenhuma nota encontrada para o aluno com o ID: " + id);
        }

        return notas.stream()
                .map(nota -> new NotaPorAlunoResponse(
                        nota.getValor(),
                        nota.getId_aluno().getNome(),
                        nota.getId_docente().getNome(),
                        nota.getId_materia().getNome()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Cria uma nova nota para um aluno, docente e matéria específicos.
     * A nota criada deve estar entre 0 e 10. Caso contrário, uma exceção será lançada.
     *
     * @param request O objeto {@code NotaRequest} contendo os detalhes da nota a ser criada.
     * @return Um objeto {@code NotaResponse} contendo os detalhes da nota criada.
     * @throws IllegalArgumentException Se o valor da nota não estiver entre 0 e 10.
     * @throws RuntimeException Se o aluno, docente ou matéria não forem encontrados no repositório.
     */
    @Override
    public NotaResponse criarNota(NotaRequest request) {
        log.info("Criando nova nota: {}", request);

        if (request.valorNota() < 0 || request.valorNota() > 10) {
            throw new IllegalArgumentException("A nota deve ser entre 0 e 10");
        }

        AlunoEntity aluno = alunoRepository.findById(request.alunoId())
                .orElseThrow(()-> new RuntimeException("Aluno nao encontrado"));
        DocenteEntity docente = docenteRepository.findById(request.docenteId())
                .orElseThrow(()-> new RuntimeException("Docente nao encontrado"));
        MateriaEntity materia = materiaRepository.findById(request.materiaId())
                .orElseThrow(()-> new RuntimeException("Materia nao encontrado"));

        NotaEntity notaEntity = new NotaEntity();
            notaEntity.setValor(request.valorNota());

        Date dataEntrada = request.dataNota() != null ? request.dataNota() : new Date();
        notaEntity.setData_nota(dataEntrada);

        notaEntity.setId_aluno(aluno);
        notaEntity.setId_docente(docente);
        notaEntity.setId_materia(materia);

        NotaEntity nota = notaRepository.save(notaEntity);

        NotaResponse response = new NotaResponse(
                nota.getId(),
                nota.getValor().doubleValue(),
                nota.getData_nota(),
                nota.getId_aluno().getNome(),
                nota.getId_docente().getNome(),
                nota.getId_materia().getNome()
        );
        return response;
    }

    /**
     * Obtém os detalhes de uma nota específica pelo seu ID.
     *
     * @param id O identificador único da nota a ser obtida.
     * @return Um objeto {@code NotaResponse} contendo os detalhes da nota encontrada.
     * @throws NotFoundException Se a nota com o ID fornecido não for encontrada no repositório.
     */
    @Override
    public NotaResponse obterNotaPorId(Long id) {
        log.info("Obtendo nota por ID: {}", id);
        Optional<NotaEntity> notaOptional = notaRepository.findById(id);
        NotaEntity nota = notaOptional.orElseThrow(()->{
            log.warn("Nota não encontrada pelo ID: {}", id);
            return new NotFoundException("Nota não encontrada com o ID: " + id);
        });
        NotaResponse response = new NotaResponse(
                nota.getId(),
                nota.getValor(),
                nota.getData_nota(),
                nota.getId_aluno().getNome(),
                nota.getId_docente().getNome(),
                nota.getId_materia().getNome()
        );
        return response;
    }

    /**
     * Atualiza a nota de um aluno para uma determinada matéria e docente.
     *
     * @param id O identificador único da nota a ser atualizada.
     * @param request O objeto NotaRequest contendo os detalhes da atualização.
     * @return NotaResponse contendo os detalhes da nota atualizada.
     * @throws RuntimeException Se o aluno, docente ou matéria não forem encontrados.
     * @throws NotFoundException Se a nota não for encontrada pelo ID fornecido.
     * @throws IllegalArgumentException Se o valor da nota não estiver no intervalo de 0 a 10.
     */
    @Override
    public NotaResponse atualizarNota(Long id, NotaRequest request) {
        log.info("Atualizando turma pelo ID: {}", id);
        verificarExistenciaNota(id);

        AlunoEntity aluno = alunoRepository.findById(request.alunoId())
                .orElseThrow(()-> new RuntimeException("Aluno nao encontrado"));
        DocenteEntity docente = docenteRepository.findById(request.docenteId())
                .orElseThrow(()-> new RuntimeException("Docente nao encontrado"));
        MateriaEntity materia = materiaRepository.findById(request.materiaId())
                .orElseThrow(()-> new RuntimeException("Materia nao encontrado"));

        NotaEntity notaEntity = notaRepository.findById(id)
                .orElseThrow(() -> {
                          log.warn("Nota não encontrado pelo ID: {}", id);
                          throw new NotFoundException("Nota não encontrado com o ID: " + id);
                });

        if(request.valorNota() < 0 || request.valorNota() > 10) {
            throw new IllegalArgumentException("A nota deve ser entre 0 e 10.");
        }

        Date dataEntrada = request.dataNota() != null ? request.dataNota() : new Date();
        notaEntity.setData_nota(dataEntrada);

        notaEntity.setValor(request.valorNota());
        notaEntity.setId_aluno(aluno);
        notaEntity.setId_docente(docente);
        notaEntity.setId_materia(materia);

        NotaEntity nota = notaRepository.save(notaEntity);

        return new NotaResponse(
                nota.getId(),
                nota.getValor(),
                nota.getData_nota(),
                nota.getId_aluno().getNome(),
                nota.getId_docente().getNome(),
                nota.getId_materia().getNome()
        );
    }

    /**
     * Deleta a nota de um aluno com base no ID fornecido.
     *
     * @param id O identificador único da nota a ser deletada.
     */
    @Override
    public void deletarNota(Long id) {
        log.info("Deletando Turma com o ID: {}", id);
        verificarExistenciaNota(id);
        notaRepository.deleteById(id);
    }

    /**
     * Calcula a nota total de um aluno com base em todas as notas registradas.
     *
     * @param idAluno O identificador único do aluno.
     * @return NotaTotalResponse contendo o ID do aluno, nome e a nota total calculada.
     * @throws RuntimeException Se não houver notas registradas para o aluno ou se o aluno não for encontrado.
     */
    @Override
    public NotaTotalResponse calcularNotaTotal(Long idAluno) {
        List<NotaEntity> notas = notaRepository.findAllByIdAluno(idAluno);
        Double somaNotas = notas.stream()
                .mapToDouble(NotaEntity::getValor)
                .sum();

        AlunoEntity aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado."));

        Long quantidadeMaterias = notaRepository.countMateriasByAlunoId(idAluno);

        if (quantidadeMaterias == 0) {
            throw new RuntimeException("O aluno não está matriculado em nenhuma matéria.");
        }

        Double notaTotal = (somaNotas / quantidadeMaterias) * 10;

        return new NotaTotalResponse(
                aluno.getId(),
                aluno.getNome(),
                notaTotal
        );
    }

    /**
     * Verifica a existência de uma nota pelo seu ID.
     * Se a nota não for encontrada, um aviso é registrado e uma exceção é lançada.
     * Se a nota for encontrada, uma informação é registrada.
     *
     * @param id O ID da nota a ser verificada.
     * @throws NotFoundException Se a nota com o ID especificado não for encontrada.
     */
    private void verificarExistenciaNota(Long id) {
        if (!notaRepository.existsById(id)) {
            log.warn("Nota não encontrada com o ID: {}", id);
            throw new NotFoundException("Nota não encontrada com o ID: " + id);
        } else {
            log.info("Nota encontrada com o ID: {}", id);
        }
    }

    /*
    * Metodo exclusivo para ALUNO verificar a nota total!
    */
    public NotaTotalResponse calcularNotaTotalAluno(Long idAluno) {
        List<NotaEntity> notas = notaRepository.findAllByIdAluno(idAluno);
        Double somaNotas = notas.stream()
                .mapToDouble(NotaEntity::getValor)
                .sum();

        AlunoEntity aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado."));

        Long quantidadeMaterias = notaRepository.countMateriasByAlunoId(idAluno);

        if (quantidadeMaterias == 0) {
            throw new RuntimeException("O aluno não está matriculado em nenhuma matéria.");
        }

        Double notaTotal = (somaNotas / quantidadeMaterias) * 10;

        return new NotaTotalResponse(
                aluno.getId(),
                aluno.getNome(),
                notaTotal
        );
    }

    @Override
    public List<NotaPorAlunoResponse> listarNotaDoAluno(Long id) {
        log.info("Listando todas as notas para o aluno com o ID: {}", id);
        List<NotaEntity> notas = notaRepository.findAllByIdAluno(id);

        if (notas.isEmpty()) {
            log.warn("Nenhuma nota encontrada! Aguarde seu professor lancar ela");
            throw new NotFoundException("Nenhuma nota encontrada! Aguarde seu professor lancar ela");
        }

        return notas.stream()
                .map(nota -> new NotaPorAlunoResponse(
                        nota.getValor(),
                        nota.getId_aluno().getNome(),
                        nota.getId_docente().getNome(),
                        nota.getId_materia().getNome()
                ))
                .collect(Collectors.toList());
    }
}

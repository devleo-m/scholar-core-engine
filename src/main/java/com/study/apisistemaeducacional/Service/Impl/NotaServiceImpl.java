package com.study.apisistemaeducacional.Service.Impl;

import com.study.apisistemaeducacional.Controller.dto.request.NotaRequest;
import com.study.apisistemaeducacional.Controller.dto.response.NotaPorAlunoResponse;
import com.study.apisistemaeducacional.Controller.dto.response.NotaResponse;
import com.study.apisistemaeducacional.Entity.*;
import com.study.apisistemaeducacional.Exception.NotFoundException;
import com.study.apisistemaeducacional.Repository.*;
import com.study.apisistemaeducacional.Service.NotaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    @Override
    public List<NotaPorAlunoResponse> listarNotaPorAluno(Long id) {
        log.info("Listando todas as notas para o aluno com o ID: {}", id);
        List<NotaEntity> notas = notaRepository.findAllByIdAluno(id);

        return notas.stream()
                .map(nota -> new NotaPorAlunoResponse(
                        nota.getValor(),
                        nota.getId_aluno().getNome(),
                        nota.getId_docente().getNome(),
                        nota.getId_materia().getNome()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public NotaResponse criarNota(NotaRequest request) {
        log.info("Criando nova nota: {}", request);

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

        notaEntity.setValor(request.valorNota());
        notaEntity.setData_nota(request.dataNota());
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

    @Override
    public void deletarNota(Long id) {
        log.info("Deletando Turma com o ID: {}", id);
        verificarExistenciaNota(id);
        notaRepository.deleteById(id);
    }

    private void verificarExistenciaNota(Long id) {
        if (!notaRepository.existsById(id)) {
            log.warn("Nota não encontrada com o ID: {}", id);
            throw new NotFoundException("Nota não encontrada com o ID: " + id);
        } else {
            log.info("Nota encontrada com o ID: {}", id);
        }
    }
}

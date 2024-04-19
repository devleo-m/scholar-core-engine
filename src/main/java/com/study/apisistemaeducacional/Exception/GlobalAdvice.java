package com.study.apisistemaeducacional.Exception;

import com.study.apisistemaeducacional.Exception.dto.Erro;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Erro> handleException(Exception e) {
        log.error("Erro interno do servidor", e);
        Erro erro = Erro.builder()
                .codigo(HttpStatus.INTERNAL_SERVER_ERROR) // Corrigido para 'codigo'
                .mensagem("Ocorreu um erro interno.")
                .build();
        return new ResponseEntity<>(erro, erro.getCodigo()); // Corrigido para 'getCodigo'
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Erro> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.warn("Violação de integridade de dados", e);
        Erro erro = Erro.builder()
                .codigo(HttpStatus.BAD_REQUEST)
                .mensagem("Dados inválidos fornecidos.")
                .build();
        return new ResponseEntity<>(erro, erro.getCodigo());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Erro> handleNotFoundException(NotFoundException e) {
        log.info("Recurso não encontrado", e);
        Erro erro = Erro.builder()
                .codigo(HttpStatus.NOT_FOUND)
                .mensagem(e.getMessage())
                .build();
        return new ResponseEntity<>(erro, erro.getCodigo());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Erro> handleAccessDeniedException(AccessDeniedException e) {
        log.warn("Tentativa de acesso negado", e);
        Erro erro = Erro.builder()
                .codigo(HttpStatus.FORBIDDEN)
                .mensagem("Ops! Parece que você encontrou uma porta secreta, mas ela está trancada. Não é possível entrar sem a chave mágica.")
                .build();
        return new ResponseEntity<>(erro, erro.getCodigo());
    }
}

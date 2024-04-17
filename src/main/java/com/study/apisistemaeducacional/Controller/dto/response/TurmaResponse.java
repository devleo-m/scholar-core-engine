package com.study.apisistemaeducacional.Controller.dto.response;

public record TurmaResponse(
        Long id,
        String turma,
        String docente,
        String curso
){}

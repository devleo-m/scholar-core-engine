package com.study.apisistemaeducacional.Controller.dto.response;

public record TurmaPorIdResponse(
        Long id,
        String turma,
        String docente,
        String curso,
        Long totalAlunos
){}

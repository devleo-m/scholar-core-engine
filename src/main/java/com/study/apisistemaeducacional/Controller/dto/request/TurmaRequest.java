package com.study.apisistemaeducacional.Controller.dto.request;

public record TurmaRequest(
        String nome,
        Long docenteId,
        Long cursoId
){}
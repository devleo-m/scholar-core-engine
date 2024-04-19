package com.study.apisistemaeducacional.Controller.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record AlunoRequest(
        String nome,
        @JsonFormat(pattern = "dd/MM/yyyy")
        Date nascimento,
        Long turmaId,
        Long usuarioId
){}

package com.study.apisistemaeducacional.Controller.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record AlunoResponse (
        Long id,
        String nome,
        @JsonFormat(pattern = "dd/MM/yyyy")
        Date nascimento,
        String turma,
        String login,
        String papel
){}

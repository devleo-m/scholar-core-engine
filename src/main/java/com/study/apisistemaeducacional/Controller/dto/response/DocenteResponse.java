package com.study.apisistemaeducacional.Controller.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record DocenteResponse(
        Long id,
        String nome,
        @JsonFormat(pattern = "dd/MM/yyyy")
        Date dataEntrada,
        String login,
        String papel
){}


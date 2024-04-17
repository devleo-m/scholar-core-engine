package com.study.apisistemaeducacional.Controller.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record DocenteRequest(
        String nome,
        @JsonFormat(pattern = "dd/MM/yyyy")
        Date dataEntrada,
        Long usuarioId
){}


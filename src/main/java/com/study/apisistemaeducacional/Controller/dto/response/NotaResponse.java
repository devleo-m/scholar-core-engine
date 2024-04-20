package com.study.apisistemaeducacional.Controller.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record NotaResponse(
        Long notaId,
        Double valorNota,
        @JsonFormat(pattern = "dd/MM/yyyy")
        Date dataNota,
        String alunoNome,
        String docenteNome,
        String materiaNome
){}

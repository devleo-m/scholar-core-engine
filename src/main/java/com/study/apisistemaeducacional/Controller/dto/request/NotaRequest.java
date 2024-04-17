package com.study.apisistemaeducacional.Controller.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record NotaRequest (
        Double valorNota,
        @JsonFormat(pattern = "dd/MM/yyyy")
        Date dataNota,
        Long alunoId,
        Long docenteId,
        Long materiaId
){}

package com.study.apisistemaeducacional.Controller.dto.response;


import java.util.Date;

public record NotaPorAlunoResponse(
        Double valorNota,
        String alunoNome,
        String docenteNome,
        String materiaNome
) {}

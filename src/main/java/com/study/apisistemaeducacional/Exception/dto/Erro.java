package com.study.apisistemaeducacional.Exception.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class Erro {
    private HttpStatus codigo;
    private String mensagem;
}

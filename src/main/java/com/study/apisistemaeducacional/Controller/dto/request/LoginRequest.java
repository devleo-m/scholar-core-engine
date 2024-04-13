package com.study.apisistemaeducacional.Controller.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record LoginRequest (
        String nomeUsuario,
        String senha,
        @JsonFormat(pattern = "dd/MM/yyyy") //formatar a partir do Json
        LocalDate localDate,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") //formatar a partir do Json
        LocalDateTime localDateTime
){
        // Adiciona getters explicitamente para os campos nomeUsuario e senha
        public String getNomeUsuario() {
                return nomeUsuario;
        }

        public String getSenha() {
                return senha;
        }
}

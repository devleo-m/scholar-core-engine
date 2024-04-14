package com.study.apisistemaeducacional.Controller.dto.request;

public record InserirUsuarioRequest(
        String login,
        String senha,
        String tipoUsuario
) {
}

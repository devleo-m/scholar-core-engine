package com.study.apisistemaeducacional.Controller;

import com.study.apisistemaeducacional.Controller.dto.request.InserirUsuarioRequest;
import com.study.apisistemaeducacional.Controller.dto.request.LoginRequest;
import com.study.apisistemaeducacional.Controller.dto.response.LoginResponse;
import com.study.apisistemaeducacional.Entity.PapelEntity;
import com.study.apisistemaeducacional.Entity.UsuarioEntity;
import com.study.apisistemaeducacional.Repository.PapelRepository;
import com.study.apisistemaeducacional.Repository.UsuarioRepository;
import com.study.apisistemaeducacional.Security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final PapelRepository papelRepository;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest body){
        UsuarioEntity user = this.repository.findByLogin(body.login()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(body.senha(), user.getSenha())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new LoginResponse(user.getLogin(), user.getPapel(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody InserirUsuarioRequest body){
        Optional<UsuarioEntity> user = this.repository.findByLogin(body.login());

        if(user.isEmpty()) {
            UsuarioEntity newUser = new UsuarioEntity();
            newUser.setSenha(passwordEncoder.encode(body.senha()));
            newUser.setLogin(body.login());

            PapelEntity papel = determinarPapel(body.tipoUsuario());
            newUser.setPapel(papel);

            this.repository.save(newUser);

            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new LoginResponse(newUser.getLogin(), newUser.getPapel(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    private PapelEntity determinarPapel(String tipoUsuario) {
        // Se o tipo de usuário não for especificado ou não corresponder a nenhum papel existente, atribuímos o papel padrão "aluno"
        if (tipoUsuario == null || tipoUsuario.isEmpty()) {
            return papelRepository.findByNome("aluno")
                    .orElseThrow(() -> new RuntimeException("Papel 'aluno' não encontrado"));
        }

        // Buscamos o papel pelo nome especificado
        Optional<PapelEntity> papelOptional = papelRepository.findByNome(tipoUsuario.toLowerCase());
        if (papelOptional.isPresent()) {
            return papelOptional.get();
        } else {
            // Se o papel especificado não existir, lançamos uma exceção
            throw new RuntimeException("Papel '" + tipoUsuario + "' não encontrado");
        }
    }
}
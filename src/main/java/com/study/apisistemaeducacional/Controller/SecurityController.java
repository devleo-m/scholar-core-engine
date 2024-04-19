package com.study.apisistemaeducacional.Controller;

import com.study.apisistemaeducacional.Controller.dto.request.InserirUsuarioRequest;
import com.study.apisistemaeducacional.Controller.dto.request.LoginRequest;
import com.study.apisistemaeducacional.Controller.dto.response.LoginResponse;
import com.study.apisistemaeducacional.Controller.dto.response.RegistrarResponse;
import com.study.apisistemaeducacional.Entity.PapelEntity;
import com.study.apisistemaeducacional.Entity.UsuarioEntity;
import com.study.apisistemaeducacional.Repository.PapelRepository;
import com.study.apisistemaeducacional.Repository.UsuarioRepository;
import com.study.apisistemaeducacional.Security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class SecurityController {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final PapelRepository papelRepository;

    /**
     * Endpoint para realizar login de um usuário.
     *
     * @param body O corpo da requisição contendo as informações de login.
     * @throws RuntimeException Se o usuário não for encontrado no banco de dados.
     * @return ResponseEntity contendo os dados de login se o login for bem-sucedido,
     *                        ou uma resposta de erro se o login falhar.
     */
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest body) {
        // Busca o usuário no banco de dados pelo login fornecido no corpo da requisição
        UsuarioEntity usuario = repository.findByLogin(body.login())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Verifica se a senha fornecida no corpo da requisição corresponde à senha armazenada para o usuário
        // Aceita tanto senhas criptografadas quanto não criptografadas
        boolean senhaValida = passwordEncoder.matches(body.senha(), usuario.getSenha()) || body.senha().equals(usuario.getSenha());

        if (senhaValida) {
            // Gera um token de autenticação para o usuário
            String token = tokenService.generateToken(usuario);
            // Retorna uma resposta com os dados de login e o token gerado
            return ResponseEntity.ok(new LoginResponse(usuario.getLogin(),
                    usuario.getPapel().getNome(),
                    token));
        }
        // Retorna uma resposta de erro se a senha fornecida não corresponder à senha armazenada
        return ResponseEntity.badRequest().build();
    }

    /**
     * Endpoint para registrar um novo usuário.
     *
     * @param body O corpo da requisição contendo as informações do novo usuário a ser registrado.
     * @return ResponseEntity contendo os detalhes do novo usuário e um token de autenticação, se o registro for bem-sucedido,
     *         ou uma resposta de erro se o usuário já existir no banco de dados.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/registrar")
    public ResponseEntity registrar(@RequestBody InserirUsuarioRequest body) {
        // Verifica se já existe um usuário com o mesmo login no banco de dados
        Optional<UsuarioEntity> usuarioOptional = repository.findByLogin(body.login());

        // Se não houver um usuário com o mesmo login, registra o novo usuário
        if(usuarioOptional.isEmpty()) {

            // Verifica se a senha fornecida não é nula ou vazia
            if (body.senha() == null || body.senha().isEmpty()) {
                return ResponseEntity.badRequest().body("Senha não pode ser nula ou vazia");
            }

            // Cria uma nova entidade de usuário
            UsuarioEntity novoUsuario = new UsuarioEntity();
            // Define a senha do novo usuário, aplicando o algoritmo de hash
            novoUsuario.setSenha(passwordEncoder.encode(body.senha()));
            // Define o login do novo usuário
            novoUsuario.setLogin(body.login());

            // Determina o papel do novo usuário com base no tipo de usuário fornecido no corpo da requisição
            PapelEntity papel = determinarPapel(body.tipoUsuario());
            novoUsuario.setPapel(papel);

            // Salva o novo usuário no banco de dados
            novoUsuario = repository.save(novoUsuario);
            Long userId = novoUsuario.getId(); // Obter o ID gerado

            // Gera um token de autenticação para o novo usuário
            String token = tokenService.generateToken(novoUsuario);
            // Retorna uma resposta com os detalhes do novo usuário e o token gerado
            return ResponseEntity.ok(new RegistrarResponse(userId, novoUsuario.getLogin(), papel.getNome(), token));
        }
        // Retorna uma resposta de erro se o usuário já existir no banco de dados
        return ResponseEntity.badRequest().build();
    }

    /**
     * Determina o papel do usuário com base no tipo de usuário fornecido.
     *
     * @param tipoUsuario O tipo de usuário fornecido.
     * @return O papel correspondente ao tipo de usuário fornecido.
     * @throws RuntimeException Se o tipo de usuário não for especificado, não corresponder a nenhum papel existente
     *                          ou se ocorrer algum erro durante a busca.
     */
    private PapelEntity determinarPapel(String tipoUsuario) {
        // Se o tipo de usuário não for especificado ou não corresponder a nenhum papel existente, atribuímos o papel padrão "aluno"
        if (tipoUsuario == null || tipoUsuario.isEmpty()) {
            return papelRepository.findByNome("ALUNO")
                    .orElseThrow(() -> new RuntimeException("Papel 'ALUNO' não encontrado"));
        }
        // Buscamos o papel pelo nome especificado
        Optional<PapelEntity> papelOptional = papelRepository.findByNome(tipoUsuario);
        if (papelOptional.isPresent()) {
            return papelOptional.get();
        } else {
            // Se o papel especificado não existir, lançamos uma exceção
            throw new RuntimeException("papel '" + tipoUsuario + "' não encontrado");
        }
    }
}
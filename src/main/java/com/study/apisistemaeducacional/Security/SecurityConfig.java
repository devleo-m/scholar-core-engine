package com.study.apisistemaeducacional.Security;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("Configurando a cadeia de filtros de segurança");
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // Permissão pública
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/notas/aluno/pontuacao/total").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/notas/aluno/lista").permitAll()

                        // Permissões DOCENTE
                        .requestMatchers(HttpMethod.GET, "/api/docentes/**").hasAnyRole("ADMIN", "PEDAGOGICO", "RECRUITER")
                        .requestMatchers(HttpMethod.POST, "/api/docentes/**").hasAnyRole("ADMIN", "PEDAGOGICO", "RECRUITER")
                        .requestMatchers(HttpMethod.PUT, "/api/docentes/**").hasAnyRole("ADMIN", "PEDAGOGICO", "RECRUITER")

                        // Permissões ALUNO
                        .requestMatchers(HttpMethod.GET, "/api/alunos/**").hasAnyRole("ADMIN", "PEDAGOGICO")
                        .requestMatchers(HttpMethod.POST, "/api/alunos/**").hasAnyRole("ADMIN", "PEDAGOGICO")
                        .requestMatchers(HttpMethod.PUT, "/api/alunos/**").hasAnyRole("ADMIN", "PEDAGOGICO")

                        // Permissões CURSO
                        .requestMatchers(HttpMethod.GET, "/api/cursos/**").hasAnyRole("ADMIN", "PEDAGOGICO")
                        .requestMatchers(HttpMethod.POST, "/api/cursos/**").hasAnyRole("ADMIN", "PEDAGOGICO")
                        .requestMatchers(HttpMethod.PUT, "/api/cursos/**").hasAnyRole("ADMIN", "PEDAGOGICO")

                        // Permissões MATERIA
                        .requestMatchers(HttpMethod.GET, "/api/materias/**").hasAnyRole("ADMIN", "PEDAGOGICO")
                        .requestMatchers(HttpMethod.POST, "/api/materias/**").hasAnyRole("ADMIN", "PEDAGOGICO")
                        .requestMatchers(HttpMethod.PUT, "/api/materias/**").hasAnyRole("ADMIN", "PEDAGOGICO")

                        // Permissões TURMA
                        .requestMatchers(HttpMethod.GET, "/api/turmas/**").hasAnyRole("ADMIN", "PEDAGOGICO")
                        .requestMatchers(HttpMethod.POST, "/api/turmas/**").hasAnyRole("ADMIN", "PEDAGOGICO")
                        .requestMatchers(HttpMethod.PUT, "/api/turmas/**").hasAnyRole("ADMIN", "PEDAGOGICO")

                        // Permissões NOTA
                        //.requestMatchers(HttpMethod.GET, "/api/notas/aluno/pontuacao/total").hasRole("ALUNO")
                        .requestMatchers(HttpMethod.GET, "/api/notas/lista/notas/{id}").hasRole("ALUNO")

                        .requestMatchers(HttpMethod.GET, "/api/notas/aluno/{id}").hasAnyRole("ADMIN","PROFESSOR")
                        .requestMatchers(HttpMethod.GET, "/api/notas/aluno/{id}/pontuacao").hasAnyRole("ADMIN","PROFESSOR")
                        .requestMatchers(HttpMethod.GET, "/api/notas/{id}").hasAnyRole("ADMIN","PROFESSOR")

                        //.requestMatchers(HttpMethod.GET, "/api/notas/aluno/{id}/pontuacao").hasAnyRole("ADMIN","PROFESSOR","ALUNO")
                        .requestMatchers(HttpMethod.GET, "/api/notas/**").hasAnyRole("ADMIN", "PROFESSOR")
                        .requestMatchers(HttpMethod.POST, "/api/notas/**").hasAnyRole("ADMIN", "PROFESSOR")
                        .requestMatchers(HttpMethod.PUT, "/api/notas/**").hasAnyRole("ADMIN", "PROFESSOR")

                        // Permissões especificas
                        .requestMatchers(HttpMethod.POST, "/registrar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")

                        // Todas as outras requisições autenticadas
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        log.info("Cadeia de filtros de segurança configurada");
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("Configurando a cadeia de password encoder");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        log.info("Configurando a cadeia de authentication manager");
        return authenticationConfiguration.getAuthenticationManager();
    }
}

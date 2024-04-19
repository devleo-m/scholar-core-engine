package com.study.apisistemaeducacional.Security;

import com.study.apisistemaeducacional.Entity.UsuarioEntity;
import com.study.apisistemaeducacional.Repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UsuarioRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Carregando usuário pelo nome de usuário: {}", username);
        UsuarioEntity user = this.repository.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado! :("));
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getPapel().getNome().toUpperCase()));
        log.info("Usuário carregado: {}", user.getLogin());
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getSenha(), authorities);
    }
}

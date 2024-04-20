package com.study.apisistemaeducacional.Repository;

import com.study.apisistemaeducacional.Entity.AlunoEntity;
import com.study.apisistemaeducacional.Entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<AlunoEntity, Long> {
    Optional<AlunoEntity> findByUsuario(UsuarioEntity usuario);
    Optional<AlunoEntity> findByUsuarioLogin(String usuario);
}

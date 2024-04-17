package com.study.apisistemaeducacional.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "docente")
public class DocenteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_entrada")
    private Date dataEntrada;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private UsuarioEntity usuario;

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

}

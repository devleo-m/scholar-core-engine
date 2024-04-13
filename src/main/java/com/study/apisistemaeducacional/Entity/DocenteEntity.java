package com.study.apisistemaeducacional.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "docente")
public class DocenteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_docente;

    private String nome_docente;

    @Temporal(TemporalType.DATE)
    private Date data_entrada;

    @Column(unique = true)
    private Long id_usuario;

}

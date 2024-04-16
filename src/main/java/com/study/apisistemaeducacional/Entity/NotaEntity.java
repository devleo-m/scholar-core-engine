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
@Table(name = "nota")
public class NotaEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long valor;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_nota")
    private Date data_nota;

    @ManyToOne
    @JoinColumn(name = "id_docente", referencedColumnName = "id")
    private DocenteEntity id_docente;

    @ManyToOne
    @JoinColumn(name = "id_aluno", referencedColumnName = "id")
    private AlunoEntity id_aluno;

    @ManyToOne
    @JoinColumn(name = "id_materia", referencedColumnName = "id")
    private MateriaEntity id_materia;
}


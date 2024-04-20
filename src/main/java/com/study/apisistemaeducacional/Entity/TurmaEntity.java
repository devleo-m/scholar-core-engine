package com.study.apisistemaeducacional.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "turma")
public class TurmaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany(mappedBy = "turma")
    private List<AlunoEntity> alunos;

    @ManyToOne
    @JoinColumn(name = "id_docente", referencedColumnName = "id")
    private DocenteEntity docente;

    @ManyToOne
    @JoinColumn(name = "id_curso", referencedColumnName = "id")
    private CursoEntity curso;
}

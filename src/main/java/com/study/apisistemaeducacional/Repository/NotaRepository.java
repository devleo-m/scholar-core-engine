package com.study.apisistemaeducacional.Repository;

import com.study.apisistemaeducacional.Entity.NotaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotaRepository extends JpaRepository<NotaEntity, Long> {
    @Query("SELECT nota FROM NotaEntity nota WHERE nota.id_aluno.id = :idAluno")
    List<NotaEntity> findAllByIdAluno(Long idAluno);

    @Query("SELECT COUNT(DISTINCT n.id_materia.id) FROM NotaEntity n WHERE n.id_aluno.id = :idAluno")
    Long countMateriasByAlunoId(@Param("idAluno") Long idAluno);

}

package com.study.apisistemaeducacional.Service;

import com.study.apisistemaeducacional.Entity.MateriaEntity;
import java.util.List;

public interface MateriaService {
    MateriaEntity criarMateria(MateriaEntity materia);
    MateriaEntity obterMateriaPorId(Long id);
    MateriaEntity atualizarMateria(Long id, MateriaEntity materia);
    List<MateriaEntity> listarTodasMaterias();
    void deletarMateria(Long id);
}

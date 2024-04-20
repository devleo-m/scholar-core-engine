package com.study.apisistemaeducacional.Service;

import com.study.apisistemaeducacional.Controller.dto.request.MateriaRequest;
import com.study.apisistemaeducacional.Controller.dto.response.MateriaResponse;
import com.study.apisistemaeducacional.Entity.MateriaEntity;
import java.util.List;

public interface MateriaService {
    MateriaResponse criarMateria(MateriaRequest request);
    MateriaResponse obterMateriaPorId(Long id);
    MateriaResponse atualizarMateria(Long id, MateriaRequest request);
    List<MateriaResponse> listarTodasMaterias();
    void deletarMateria(Long id);
}

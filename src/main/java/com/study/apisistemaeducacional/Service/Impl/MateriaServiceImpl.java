package com.study.apisistemaeducacional.Service.Impl;

import com.study.apisistemaeducacional.Entity.MateriaEntity;
import com.study.apisistemaeducacional.Exception.NotFoundException;
import com.study.apisistemaeducacional.Repository.MateriaRepository;
import com.study.apisistemaeducacional.Service.MateriaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MateriaServiceImpl implements MateriaService {
    private final MateriaRepository materiaRepository;

    /**
     * Método para criar materia.
     *
     * @param materia a ser adicionado.
     * @return materia criada.
     */
    @Override
    public MateriaEntity criarMateria(MateriaEntity materia) {
        log.info("Criando nova materia: {}", materia);
        return materiaRepository.save(materia);
    }

    /**
     * Método para obter materia pelo ID.
     *
     * @param id ID da materia a ser obtido.
     * @return A materia encontrada.
     * @throws NotFoundException Se a materia não for encontrado.
     */
    @Override
    public MateriaEntity obterMateriaPorId(Long id) {
        log.info("Obtendo materia por ID: {}", id);
        Optional<MateriaEntity> materiaOptional = materiaRepository.findById(id);
        return materiaOptional.orElseThrow(() -> {
            log.warn("materia não encontrada pelo ID: {}", id);
            return new NotFoundException("materia não encontrada com o ID: " + id);
        });
    }

    /**
     * Método para atualizar materia pelo id.
     *
     * @param id id da materia para ser atualizado
     * @param materia atributos novos da materia
     * @verificarExistenciaDocente metodo para verificar se a materia existe
     * @return a materia atualizada
     */
    @Override
    public MateriaEntity atualizarMateria(Long id, MateriaEntity materia) {
        log.info("Atualizando materia pelo ID: {}", id);
        verificarExistenciaMateria(id);
        materia.setId(id);
        return materiaRepository.save(materia);
    }

    /**
     * Método para listar todas as materias.
     *
     * @return Lista de todas as materias.
     */
    @Override
    public List<MateriaEntity> listarTodasMaterias() {
        log.info("Listando todas as materias!");
        return materiaRepository.findAll();
    }

    /**
     * Deleta uma materia existente.
     *
     * @verificarExistenciaDocente metodo para verificar se a materia existe
     * @param id ID da materia a ser deletado.
     */
    @Override
    public void deletarMateria(Long id) {
        log.info("Deletando materia com o ID: {}", id);
        verificarExistenciaMateria(id);
        materiaRepository.deleteById(id);
    }

    /**
     * Verifica se a materia existe.
     *
     * @param id ID da materia a ser verificado.
     * @throws NotFoundException Se a materia não for encontrado.
     */
    private void verificarExistenciaMateria(Long id) {
        if (!materiaRepository.existsById(id)) {
            log.warn("materia não encontrada com o ID: {}", id);
            throw new NotFoundException("materia não encontrada com o ID: " + id);
        } else {
            log.info("materia encontrada com o ID: {}", id);
        }
    }
}

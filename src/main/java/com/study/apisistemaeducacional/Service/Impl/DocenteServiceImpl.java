package com.study.apisistemaeducacional.Service.Impl;

import com.study.apisistemaeducacional.Controller.dto.request.CriarDocenteRequest;
import com.study.apisistemaeducacional.Controller.dto.response.CriarDocenteResponse;
import com.study.apisistemaeducacional.Entity.DocenteEntity;
import com.study.apisistemaeducacional.Entity.UsuarioEntity;
import com.study.apisistemaeducacional.Exception.NotFoundException;
import com.study.apisistemaeducacional.Repository.DocenteRepository;
import com.study.apisistemaeducacional.Repository.UsuarioRepository;
import com.study.apisistemaeducacional.Security.TokenService;
import com.study.apisistemaeducacional.Service.DocenteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class DocenteServiceImpl implements DocenteService {

    private final DocenteRepository docenteRepository;
    private final UsuarioRepository usuarioRepository;

    /**
     * Método para criar Docente.
     * //@param docente O docente a ser adicionado.
     * @return docente criado.
     */
    @Override
    public CriarDocenteResponse criarDocente(CriarDocenteRequest request) {
        log.info("Criando novo docente: {}", request);

        // Busca o UsuarioEntity pelo id
        UsuarioEntity usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Converte o DTO de entrada para a entidade Docente
        DocenteEntity docente = new DocenteEntity();
        docente.setNome(request.nome());
        docente.setUsuario(usuario); // Define o UsuarioEntity

        // Salva o docente no banco de dados
        DocenteEntity savedDocente = docenteRepository.save(docente);

        // Converte a entidade Docente salva para o DTO de resposta
        CriarDocenteResponse response = new CriarDocenteResponse(savedDocente.getId(), savedDocente.getNome(), savedDocente.getUsuario().getPapel().getNome());

        return response;
    }

    /**
     * Método para obter Docente pelo ID.
     * @param id ID do Docente a ser obtido.
     * @return O docente encontrado.
     * @throws NotFoundException Se o aluno não for encontrado.
     */
    @Override
    public CriarDocenteResponse obterDocentePorId(Long id) {
        log.info("Obtendo docente por ID: {}", id);
        Optional<DocenteEntity> docenteOptional = docenteRepository.findById(id);
        DocenteEntity docente = docenteOptional.orElseThrow(() -> {
            log.warn("Docente não encontrado pelo ID: {}", id);
            return new NotFoundException("Docente não encontrado com o ID: " + id);
        });

        // Converte a entidade Docente para o DTO de resposta
        CriarDocenteResponse response = new CriarDocenteResponse(docente.getId(), docente.getNome(), docente.getUsuario().getPapel().getNome());

        return response;
    }


    /**
     * Método para listar todos os Docentes.
     * @return Lista de todos os docentes.
     */
    @Override
    public List<DocenteEntity> listarTodosDocentes() {
        log.info("Listando todos os Docentes!");
        return docenteRepository.findAll();
    }

    /**
     * Método para atualizar Docentes pelo id.
     * @param id id do docente para ser atualizado
     * @param docente novos dados do docente
     *.@verificarExistenciaDocente metodo para verificar se o docente existe
     * @return o docente atualizado.
     */
    public DocenteEntity atualizarDocente(Long id, DocenteEntity docente){
        log.info("Atualizando docente pelo ID: {}", id);
        verificarExistenciaDocente(id);
        docente.setId(id);
        return docenteRepository.save(docente);
    }

    /**
     * Deleta um aluno existente.
     * .@verificarExistenciaDocente metodo para verificar se o docente existe
     *
     * @param id ID do aluno a ser deletado.
     * @return
     */
    @Override
    public void deletarDocente(Long id) {
        log.info("Deletando aluno com o ID: {}", id);
        verificarExistenciaDocente(id);
        docenteRepository.deleteById(id);
    }

    /**
     * Verifica se o docente existe.
     * @param id ID do docente a ser verificado.
     * @throws NotFoundException Se o docente não for encontrado.
     */
    private void verificarExistenciaDocente(Long id) {
        if (!docenteRepository.existsById(id)) {
            log.warn("Aluno não encontrado com o ID: {}", id);
            throw new NotFoundException("Aluno não encontrado com o ID: " + id);
        } else {
            log.info("Docente encontrado com o ID: {}", id);
        }
    }
}
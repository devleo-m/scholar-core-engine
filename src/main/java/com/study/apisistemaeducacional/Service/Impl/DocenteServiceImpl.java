package com.study.apisistemaeducacional.Service.Impl;

import com.study.apisistemaeducacional.Controller.dto.request.DocenteRequest;
import com.study.apisistemaeducacional.Controller.dto.response.DocenteResponse;
import com.study.apisistemaeducacional.Entity.DocenteEntity;
import com.study.apisistemaeducacional.Entity.UsuarioEntity;
import com.study.apisistemaeducacional.Exception.NotFoundException;
import com.study.apisistemaeducacional.Repository.DocenteRepository;
import com.study.apisistemaeducacional.Repository.UsuarioRepository;
import com.study.apisistemaeducacional.Service.DocenteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class DocenteServiceImpl implements DocenteService {
    private final DocenteRepository docenteRepository;
    private final UsuarioRepository usuarioRepository;

    /**
     * Método para criar Docente.
     *
     * //@param docente O docente a ser adicionado.
     * @return docente criado.
     */
    @Override
    public DocenteResponse criarDocente(DocenteRequest request) {
        log.info("Criando novo docente: {}", request);

        // Busca o UsuarioEntity pelo id
        UsuarioEntity usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Verifica se o papel do usuário é permitido para um docente
        String papelUsuario = usuario.getPapel().getNome();
        if (!(papelUsuario.equals("PROFESSOR") || papelUsuario.equals("ADMIN") ||
                papelUsuario.equals("RECRUITER") || papelUsuario.equals("PEDAGOGICO"))) {
            throw new RuntimeException("Somente usuários com os papéis de 'ADMIN','PROFESSOR' 'RECRUITER' ou 'PEDAGOGICO' podem ser criados como docentes");
        }

        // Converte o DTO de entrada para a entidade Docente
        DocenteEntity docenteEntity = new DocenteEntity();
        docenteEntity.setNome(request.nome());

        // Verifica se uma data de entrada foi fornecida, caso contrário, usa a data atual
        Date dataEntrada = request.dataEntrada() != null ? request.dataEntrada() : new Date();
        docenteEntity.setDataEntrada(dataEntrada);

        docenteEntity.setUsuario(usuario); // Define o UsuarioEntity

        // Salva o docente no banco de dados
        DocenteEntity docente = docenteRepository.save(docenteEntity);

        // Converte a entidade Docente salva para o DTO de resposta
        DocenteResponse response = new DocenteResponse(
                docente.getId(),
                docente.getNome(),
                docente.getDataEntrada(),
                docente.getUsuario().getLogin(),
                docente.getUsuario().getPapel().getNome());
        return response;
    }

    /**
     * Método para obter Docente pelo ID.
     *
     * @param id ID do Docente a ser obtido.
     * @return O docente encontrado.
     * @throws NotFoundException Se o aluno não for encontrado.
     */
    @Override
    public DocenteResponse obterDocentePorId(Long id) {
        log.info("Obtendo docente por ID: {}", id);
        Optional<DocenteEntity> docenteOptional = docenteRepository.findById(id);
        DocenteEntity docente = docenteOptional.orElseThrow(() -> {
            log.warn("Docente não encontrado pelo ID: {}", id);
            return new NotFoundException("Docente não encontrado com o ID: " + id);
        });
        // Converte a entidade Docente para o DTO de resposta
        DocenteResponse response = new DocenteResponse(
                docente.getId(),
                docente.getNome(),
                docente.getDataEntrada() ,
                docente.getUsuario().getLogin(),
                docente.getUsuario().getPapel().getNome());
        return response;
    }

    /**
     * Método para listar todos os Docentes.
     *
     * @return Lista de todos os docentes.
     */
    @Override
    public List<DocenteResponse> listarTodosDocentes() {
        log.info("Listando todos os Docentes!");
        List<DocenteEntity> docentes = docenteRepository.findAll();
        // Mapeie as entidades Docente para os DTOs de resposta
        List<DocenteResponse> usuariosDTO = docentes.stream()
                .map(docente -> new DocenteResponse(
                        docente.getId(),
                        docente.getNome(),
                        docente.getDataEntrada(),
                        docente.getUsuario().getLogin(),
                        docente.getUsuario().getPapel().getNome()))
                .collect(Collectors.toList());

        return usuariosDTO;
    }

    /**
     * Método para atualizar Docentes pelo id.
     *
     * @param id id do docente para ser atualizado
     * @param request novos dados do docente
     * @verificarExistenciaDocente metodo para verificar se o docente existe
     * @return o docente atualizado.
     */
    @Override
    public DocenteResponse atualizarDocente(Long id, DocenteRequest request) {
        log.info("Atualizando docente pelo ID: {}", id);
        verificarExistenciaDocente(id);

        // Busca o UsuarioEntity pelo id e verifica o papel do usuário
        UsuarioEntity usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        String papelUsuario = usuario.getPapel().getNome();
        if (!(papelUsuario.equals("PROFESSOR") || papelUsuario.equals("ADMIN") ||
                papelUsuario.equals("RECRUITER") || papelUsuario.equals("PEDAGOGICO"))) {
            throw new RuntimeException("Somente usuários com os papéis de 'ADMIN', 'PROFESSOR', 'RECRUITER' ou 'PEDAGOGICO' podem ser atualizados como docentes");
        }

        // Busca o DocenteEntity pelo id
        DocenteEntity docente = docenteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Docente não encontrado com o ID: " + id));

        // Atualiza o docente com os novos valores
        docente.setNome(request.nome());
        docente.setUsuario(usuario);
        docente.setDataEntrada(request.dataEntrada());

        // Salva o docente atualizado no repositório
        DocenteEntity docenteAtualizado = docenteRepository.save(docente);

        // Cria a resposta com os dados do docente atualizado
        DocenteResponse response = new DocenteResponse(
                docenteAtualizado.getId(),
                docenteAtualizado.getNome(),
                docenteAtualizado.getDataEntrada(),
                docenteAtualizado.getUsuario().getLogin(),
                docenteAtualizado.getUsuario().getPapel().getNome());

        log.debug("Docente atualizado: {}", docenteAtualizado);
        return response;
    }

    /**
     * Deleta um Docente existente.
     *
     * @param id ID do Docente a ser deletado.
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
     *
     * @param id ID do docente a ser verificado.
     * @throws NotFoundException Se o docente não for encontrado.
     */
    private void verificarExistenciaDocente(Long id) {
        if (!docenteRepository.existsById(id)) {
            log.warn("Docente não encontrado com o ID: {}", id);
            throw new NotFoundException("Docente não encontrado com o ID: " + id);
        } else {
            log.info("Docente encontrado com o ID: {}", id);
        }
    }
}
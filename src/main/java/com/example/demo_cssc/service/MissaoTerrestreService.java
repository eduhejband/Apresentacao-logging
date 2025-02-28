package com.example.demo_cssc.service;

import com.example.demo_cssc.dto.MissaoTerrestreDTO;
import com.example.demo_cssc.exception.RecursoNaoEncontradoException;
import com.example.demo_cssc.mapper.MissaoTerrestreMapper;
import com.example.demo_cssc.model.MissaoTerrestre;
import com.example.demo_cssc.model.SoldadoAtivo;
import com.example.demo_cssc.repository.MissaoTerrestreRepository;
import com.example.demo_cssc.repository.SoldadoAtivoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MissaoTerrestreService {

    private final MissaoTerrestreRepository missaoRepository;
    private final SoldadoAtivoRepository soldadoRepository;
    private final MissaoTerrestreMapper missaoTerrestreMapper;

    public MissaoTerrestreService(MissaoTerrestreRepository missaoRepository,
                                  SoldadoAtivoRepository soldadoRepository,
                                  MissaoTerrestreMapper missaoTerrestreMapper) {
        this.missaoRepository = missaoRepository;
        this.soldadoRepository = soldadoRepository;
        this.missaoTerrestreMapper = missaoTerrestreMapper;
    }

    public MissaoTerrestreDTO atualizarMissao(Long id, MissaoTerrestreDTO missaoDTO) {
        MissaoTerrestre missao = missaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Missão não encontrada"));

        missao.setNomeMissao(missaoDTO.getNomeMissao());

        if (missaoDTO.getSoldadosIds() != null) {
            Set<SoldadoAtivo> novosSoldados = missaoDTO.getSoldadosIds().stream()
                    .map(soldadoRepository::findById)
                    .map(optional -> optional.orElseThrow(() -> new RecursoNaoEncontradoException("Soldado não encontrado")))
                    .collect(Collectors.toSet());

            // 🔥 Em vez de sobrescrever, adicionamos ao conjunto existente
            missao.getSoldados().addAll(novosSoldados);
        }

        MissaoTerrestre missaoAtualizada = missaoRepository.save(missao);
        return missaoTerrestreMapper.toDTO(missaoAtualizada);
    }



    public List<MissaoTerrestreDTO> listarMissoes() {
        return missaoRepository.findAll().stream()
                .map(missaoTerrestreMapper::toDTO)
                .toList();
    }

    public MissaoTerrestreDTO buscarPorId(Long id) {
        MissaoTerrestre missao = missaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Missão não encontrada"));
        return missaoTerrestreMapper.toDTO(missao);
    }

    public MissaoTerrestreDTO criarMissao(MissaoTerrestreDTO missaoDTO) {
        Set<SoldadoAtivo> soldados = missaoDTO.getSoldadosIds().stream()
                .map(id -> soldadoRepository.findById(id)
                        .orElseThrow(() -> new RecursoNaoEncontradoException("Soldado não encontrado")))
                .collect(Collectors.toSet());

        MissaoTerrestre missao = new MissaoTerrestre();
        missao.setNomeMissao(missaoDTO.getNomeMissao());
        missao.setSoldados(soldados);

        return missaoTerrestreMapper.toDTO(missaoRepository.save(missao));
    }

    public void deletarMissao(Long id) {
        MissaoTerrestre missao = missaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Missão não encontrada"));

        // Remove as associações e deleta
        missao.getSoldados().forEach(soldado -> soldado.getMissoes().remove(missao));
        missao.getSoldados().clear();
        missaoRepository.delete(missao);
    }
}

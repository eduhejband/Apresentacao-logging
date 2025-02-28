package com.example.demo_cssc.service;

import com.example.demo_cssc.dto.SoldadoAtivoDTO;
import com.example.demo_cssc.exception.RecursoNaoEncontradoException;
import com.example.demo_cssc.mapper.SoldadoAtivoMapper;
import com.example.demo_cssc.model.MissaoTerrestre;
import com.example.demo_cssc.model.SoldadoAtivo;
import com.example.demo_cssc.repository.SoldadoAtivoRepository;
import com.example.demo_cssc.repository.MissaoTerrestreRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SoldadoAtivoService {

    private final SoldadoAtivoRepository soldadoRepository;
    private final MissaoTerrestreRepository missaoTerrestreRepository;
    private final SoldadoAtivoMapper soldadoAtivoMapper;

    public SoldadoAtivoService(
            SoldadoAtivoRepository soldadoRepository,
            MissaoTerrestreRepository missaoTerrestreRepository,
            SoldadoAtivoMapper soldadoAtivoMapper) {
        this.soldadoRepository = soldadoRepository;
        this.missaoTerrestreRepository = missaoTerrestreRepository;
        this.soldadoAtivoMapper = soldadoAtivoMapper;
    }

    public List<SoldadoAtivoDTO> listarSoldados() {
        return soldadoRepository.findAll()
                .stream()
                .map(soldadoAtivoMapper::toDTO) // ✅ Agora chamamos corretamente o método do mapper
                .toList();
    }

    public SoldadoAtivoDTO buscarPorId(Long id) {
        SoldadoAtivo soldado = soldadoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Soldado não encontrado"));
        return soldadoAtivoMapper.toDTO(soldado);
    }

    public List<SoldadoAtivoDTO> buscarPorMissao(Long missaoId) {
        return soldadoRepository.findByMissaoId(missaoId)
                .stream()
                .map(soldadoAtivoMapper::toDTO)
                .toList();
    }

    public SoldadoAtivoDTO criarSoldado(SoldadoAtivoDTO soldadoDTO) {
        SoldadoAtivo soldado = soldadoAtivoMapper.toEntity(soldadoDTO, new HashSet<>()); // ✅ Mapper usado corretamente
        SoldadoAtivo soldadoSalvo = soldadoRepository.save(soldado);
        return soldadoAtivoMapper.toDTO(soldadoSalvo);
    }

    public SoldadoAtivoDTO atualizarSoldado(Long id, SoldadoAtivoDTO soldadoDTO) {
        SoldadoAtivo soldado = soldadoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Soldado não encontrado"));

        soldado.setNome(soldadoDTO.getNome());
        soldado.setIdade(soldadoDTO.getIdade());

        if (soldadoDTO.getMissoesIds() != null) {
            Set<MissaoTerrestre> novasMissoes = soldadoDTO.getMissoesIds().stream()
                    .map(missaoTerrestreRepository::findById)
                    .map(optional -> optional.orElseThrow(() -> new RecursoNaoEncontradoException("Missão não encontrada")))
                    .collect(Collectors.toSet());

            // 🔥 Em vez de sobrescrever, adicionamos ao conjunto existente
            soldado.getMissoes().addAll(novasMissoes);
        }

        SoldadoAtivo soldadoAtualizado = soldadoRepository.save(soldado);
        return soldadoAtivoMapper.toDTO(soldadoAtualizado);
    }


    public void deletarSoldado(Long id) {
        SoldadoAtivo soldado = soldadoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Soldado não encontrado"));

        // Remove as associações e deleta
        soldado.getMissoes().forEach(missao -> missao.getSoldados().remove(soldado));
        soldado.getMissoes().clear();
        soldadoRepository.delete(soldado);
    }
}

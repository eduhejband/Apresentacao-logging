package com.example.demo_cssc.mapper;

import com.example.demo_cssc.dto.MissaoTerrestreDTO;
import com.example.demo_cssc.model.MissaoTerrestre;
import com.example.demo_cssc.model.SoldadoAtivo;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component // Permite injetar no Service sem chamar métodos estáticos
public class MissaoTerrestreMapper {

    public MissaoTerrestreDTO toDTO(MissaoTerrestre missao) {
        return new MissaoTerrestreDTO(
                missao.getId(),
                missao.getNomeMissao(),
                missao.getSoldados().stream().map(SoldadoAtivo::getId).collect(Collectors.toSet())
        );
    }

    public MissaoTerrestre toEntity(MissaoTerrestreDTO dto, Set<SoldadoAtivo> soldados) {
        MissaoTerrestre missao = new MissaoTerrestre();
        missao.setId(dto.getId());
        missao.setNomeMissao(dto.getNomeMissao());
        missao.setSoldados(soldados);
        return missao;
    }
}
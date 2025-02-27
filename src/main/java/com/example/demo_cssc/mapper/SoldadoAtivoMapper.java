package com.example.demo_cssc.mapper;

import com.example.demo_cssc.dto.SoldadoAtivoDTO;
import com.example.demo_cssc.model.MissaoTerrestre;
import com.example.demo_cssc.model.SoldadoAtivo;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component // Permite injetar no Service sem chamar métodos estáticos
public class SoldadoAtivoMapper {

    public SoldadoAtivoDTO toDTO(SoldadoAtivo soldado) {
        return new SoldadoAtivoDTO(
                soldado.getId(),
                soldado.getNome(),
                soldado.getIdade(),
                soldado.getMissoes().stream().map(MissaoTerrestre::getId).collect(Collectors.toSet())
        );
    }

    public SoldadoAtivo toEntity(SoldadoAtivoDTO dto, Set<MissaoTerrestre> missoes) {
        SoldadoAtivo soldado = new SoldadoAtivo();
        soldado.setId(dto.getId());
        soldado.setNome(dto.getNome());
        soldado.setIdade(dto.getIdade());
        soldado.setMissoes(missoes);
        return soldado;
    }
}
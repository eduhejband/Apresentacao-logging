package com.example.demo_cssc.dto;

import jakarta.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MissaoTerrestreDTO {
    private Long id;

    @NotNull
    private String nomeMissao;

    @NotNull
    private Set<Long> soldadosIds = Collections.emptySet(); // Evita NullPointerException

    public MissaoTerrestreDTO() {}

    public MissaoTerrestreDTO(Long id, String nomeMissao, Set<Long> soldadosIds) {
        this.id = id;
        this.nomeMissao = nomeMissao;
        this.soldadosIds = soldadosIds != null ? soldadosIds : Collections.emptySet();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeMissao() { return nomeMissao; }
    public void setNomeMissao(String nomeMissao) { this.nomeMissao = nomeMissao; }

    public Set<Long> getSoldadosIds() { return soldadosIds; }
    public void setSoldadosIds(Set<Long> soldadosIds) {
        this.soldadosIds = (soldadosIds == null || soldadosIds.isEmpty()) ? new HashSet<>() : soldadosIds;
    }

}

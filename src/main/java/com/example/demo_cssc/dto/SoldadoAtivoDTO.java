package com.example.demo_cssc.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Set;

public class SoldadoAtivoDTO {
    private Long id;

    @NotNull
    private String nome;

    @Min(18) // Supondo que todos os soldados devem ter pelo menos 18 anos
    private int idade;

    @NotNull
    private Set<Long> missoesIds = Collections.emptySet(); // Evita NullPointerException

    public SoldadoAtivoDTO() {}

    public SoldadoAtivoDTO(Long id, String nome, int idade, Set<Long> missoesIds) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.missoesIds = missoesIds != null ? missoesIds : Collections.emptySet();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public Set<Long> getMissoesIds() { return missoesIds; }
    public void setMissoesIds(Set<Long> missoesIds) { this.missoesIds = missoesIds != null ? missoesIds : Collections.emptySet(); }
}

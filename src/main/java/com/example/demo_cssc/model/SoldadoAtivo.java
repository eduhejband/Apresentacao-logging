package com.example.demo_cssc.model;


import com.example.demo_cssc.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Id;

import java.util.Set;

@Entity
@Table(name = "soldado_ativo")
public class SoldadoAtivo extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private int idade;

    @ManyToMany(mappedBy = "soldados", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private Set<MissaoTerrestre> missoes;

    public SoldadoAtivo() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public Set<MissaoTerrestre> getMissoes() {
        return missoes;
    }

    public void setMissoes(Set<MissaoTerrestre> missoes) {
        this.missoes = missoes;
    }
}
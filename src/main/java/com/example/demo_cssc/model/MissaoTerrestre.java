package com.example.demo_cssc.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "missao_terrestre")
public class MissaoTerrestre extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeMissao;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "missao_soldado",
            joinColumns = @JoinColumn(name = "missao_id"),
            inverseJoinColumns = @JoinColumn(name = "soldado_id")
    )
    private Set<SoldadoAtivo> soldados = new HashSet<>();


    public MissaoTerrestre() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeMissao() {
        return nomeMissao;
    }

    public void setNomeMissao(String nomeMissao) {
        this.nomeMissao = nomeMissao;
    }

    public Set<SoldadoAtivo> getSoldados() {
        return soldados;
    }

    public void setSoldados(Set<SoldadoAtivo> soldados) {
        this.soldados = soldados;
    }
}
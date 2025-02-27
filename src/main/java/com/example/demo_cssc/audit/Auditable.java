package com.example.demo_cssc.audit;

import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class Auditable {

    @CreatedBy
    private String criadoPor;

    @LastModifiedBy
    private String modificadoPor;

    @CreatedDate
    private LocalDateTime dataCriacao;

    @LastModifiedDate
    private LocalDateTime dataModificacao;

    public String getCriadoPor() {
        return criadoPor;
    }

    public String getModificadoPor() {
        return modificadoPor;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataModificacao() {
        return dataModificacao;
    }
}

package com.jesus.genidocs.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "introduccion_dfr")
public class IntroduccionDfr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "version_id", nullable = false)
    private VersionDocumento version;

    @Column(columnDefinition = "TEXT")
    private String proposito;

    @Column(name = "alcance_incluye", columnDefinition = "TEXT")
    private String alcanceIncluye;

    @Column(name = "alcance_excluye", columnDefinition = "TEXT")
    private String alcanceExcluye;

    @Column(columnDefinition = "TEXT")
    private String definiciones;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VersionDocumento getVersion() {
        return version;
    }

    public void setVersion(VersionDocumento version) {
        this.version = version;
    }

    public String getProposito() {
        return proposito;
    }

    public void setProposito(String proposito) {
        this.proposito = proposito;
    }

    public String getAlcanceIncluye() {
        return alcanceIncluye;
    }

    public void setAlcanceIncluye(String alcanceIncluye) {
        this.alcanceIncluye = alcanceIncluye;
    }

    public String getAlcanceExcluye() {
        return alcanceExcluye;
    }

    public void setAlcanceExcluye(String alcanceExcluye) {
        this.alcanceExcluye = alcanceExcluye;
    }

    public String getDefiniciones() {
        return definiciones;
    }

    public void setDefiniciones(String definiciones) {
        this.definiciones = definiciones;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

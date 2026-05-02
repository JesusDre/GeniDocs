package com.jesus.genidocs.entity;

import com.jesus.genidocs.entity.enums.EstadoCriterioAceptacion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "criterios_aceptacion_dwt")
public class CriterioAceptacionDwt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requerimiento_funcional_id", nullable = false)
    private RequerimientoFuncional requerimientoFuncional;

    @Column(columnDefinition = "TEXT")
    private String dado;

    @Column(columnDefinition = "TEXT")
    private String cuando;

    @Column(columnDefinition = "TEXT")
    private String entonces;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EstadoCriterioAceptacion estado;

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

    public RequerimientoFuncional getRequerimientoFuncional() {
        return requerimientoFuncional;
    }

    public void setRequerimientoFuncional(RequerimientoFuncional requerimientoFuncional) {
        this.requerimientoFuncional = requerimientoFuncional;
    }

    public String getDado() {
        return dado;
    }

    public void setDado(String dado) {
        this.dado = dado;
    }

    public String getCuando() {
        return cuando;
    }

    public void setCuando(String cuando) {
        this.cuando = cuando;
    }

    public String getEntonces() {
        return entonces;
    }

    public void setEntonces(String entonces) {
        this.entonces = entonces;
    }

    public EstadoCriterioAceptacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoCriterioAceptacion estado) {
        this.estado = estado;
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

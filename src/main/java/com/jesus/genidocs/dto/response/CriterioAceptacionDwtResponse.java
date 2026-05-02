package com.jesus.genidocs.dto.response;

import com.jesus.genidocs.entity.enums.EstadoCriterioAceptacion;

import java.time.LocalDateTime;

public class CriterioAceptacionDwtResponse {

    private Long id;
    private Long requerimientoFuncionalId;
    private String dado;
    private String cuando;
    private String entonces;
    private EstadoCriterioAceptacion estado;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRequerimientoFuncionalId() {
        return requerimientoFuncionalId;
    }

    public void setRequerimientoFuncionalId(Long requerimientoFuncionalId) {
        this.requerimientoFuncionalId = requerimientoFuncionalId;
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

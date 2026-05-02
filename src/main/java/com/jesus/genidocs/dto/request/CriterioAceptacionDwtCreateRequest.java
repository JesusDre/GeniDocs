package com.jesus.genidocs.dto.request;

import com.jesus.genidocs.entity.enums.EstadoCriterioAceptacion;
import jakarta.validation.constraints.NotNull;

public class CriterioAceptacionDwtCreateRequest {

    @NotNull
    private Long requerimientoFuncionalId;

    private String dado;
    private String cuando;
    private String entonces;

    @NotNull
    private EstadoCriterioAceptacion estado;

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
}

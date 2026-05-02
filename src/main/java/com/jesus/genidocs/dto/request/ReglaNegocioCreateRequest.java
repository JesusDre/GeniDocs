package com.jesus.genidocs.dto.request;

import com.jesus.genidocs.entity.enums.EstadoReglaNegocio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReglaNegocioCreateRequest {

    @NotNull
    private Long proyectoId;

    private String codigo;

    @NotBlank
    private String descripcion;

    @NotNull
    private EstadoReglaNegocio estado;

    public Long getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(Long proyectoId) {
        this.proyectoId = proyectoId;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoReglaNegocio getEstado() {
        return estado;
    }

    public void setEstado(EstadoReglaNegocio estado) {
        this.estado = estado;
    }
}

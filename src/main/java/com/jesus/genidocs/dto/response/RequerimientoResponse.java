package com.jesus.genidocs.dto.response;

import com.jesus.genidocs.entity.enums.EstadoRequerimiento;
import com.jesus.genidocs.entity.enums.Prioridad;

public class RequerimientoResponse {

    private Long id;
    private Long proyectoId;
    private String tipo;
    private String codigo;
    private String titulo;
    private String descripcion;
    private String tipoNoFuncional;
    private Prioridad prioridad;
    private EstadoRequerimiento estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(Long proyectoId) {
        this.proyectoId = proyectoId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoNoFuncional() {
        return tipoNoFuncional;
    }

    public void setTipoNoFuncional(String tipoNoFuncional) {
        this.tipoNoFuncional = tipoNoFuncional;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public EstadoRequerimiento getEstado() {
        return estado;
    }

    public void setEstado(EstadoRequerimiento estado) {
        this.estado = estado;
    }
}

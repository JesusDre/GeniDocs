package com.jesus.genidocs.dto.response;

import com.jesus.genidocs.entity.enums.EstadoVersion;

import java.time.LocalDateTime;

public class VersionDocumentoResponse {

    private Long id;
    private Long documentoId;
    private String numeroVersion;
    private String descripcionCambios;
    private Long generadoPorId;
    private LocalDateTime fechaGeneracion;
    private EstadoVersion estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDocumentoId() {
        return documentoId;
    }

    public void setDocumentoId(Long documentoId) {
        this.documentoId = documentoId;
    }

    public String getNumeroVersion() {
        return numeroVersion;
    }

    public void setNumeroVersion(String numeroVersion) {
        this.numeroVersion = numeroVersion;
    }

    public String getDescripcionCambios() {
        return descripcionCambios;
    }

    public void setDescripcionCambios(String descripcionCambios) {
        this.descripcionCambios = descripcionCambios;
    }

    public Long getGeneradoPorId() {
        return generadoPorId;
    }

    public void setGeneradoPorId(Long generadoPorId) {
        this.generadoPorId = generadoPorId;
    }

    public LocalDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDateTime fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public EstadoVersion getEstado() {
        return estado;
    }

    public void setEstado(EstadoVersion estado) {
        this.estado = estado;
    }
}

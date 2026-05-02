package com.jesus.genidocs.dto.response;

import com.jesus.genidocs.entity.enums.TipoArchivo;

import java.time.LocalDateTime;

public class ArchivoGeneradoResponse {

    private Long id;
    private Long versionId;
    private String nombreArchivo;
    private String rutaArchivo;
    private TipoArchivo tipo;
    private LocalDateTime generadoEn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public TipoArchivo getTipo() {
        return tipo;
    }

    public void setTipo(TipoArchivo tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getGeneradoEn() {
        return generadoEn;
    }

    public void setGeneradoEn(LocalDateTime generadoEn) {
        this.generadoEn = generadoEn;
    }
}

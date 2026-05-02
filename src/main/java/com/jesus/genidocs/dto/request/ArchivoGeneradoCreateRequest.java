package com.jesus.genidocs.dto.request;

import com.jesus.genidocs.entity.enums.TipoArchivo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ArchivoGeneradoCreateRequest {

    @NotNull
    private Long versionId;

    @NotBlank
    private String nombreArchivo;

    @NotBlank
    private String rutaArchivo;

    @NotNull
    private TipoArchivo tipo;

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
}

package com.jesus.genidocs.dto.request;

import com.jesus.genidocs.entity.enums.TipoAnexo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AnexoCreateRequest {

    @NotNull
    private Long versionId;

    @NotNull
    private TipoAnexo tipo;

    @NotBlank
    private String nombre;

    private String descripcion;

    private String rutaArchivo;

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    public TipoAnexo getTipo() {
        return tipo;
    }

    public void setTipo(TipoAnexo tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }
}

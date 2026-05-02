package com.jesus.genidocs.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class VersionDocumentoCreateRequest {

    @NotNull
    private Long documentoId;

    @NotBlank
    private String numeroVersion;

    private String descripcionCambios;

    private Long generadoPorId;

    private List<Long> rfIds;
    private List<Long> rnfIds;
    private List<Long> reglaIds;

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

    public List<Long> getRfIds() {
        return rfIds;
    }

    public void setRfIds(List<Long> rfIds) {
        this.rfIds = rfIds;
    }

    public List<Long> getRnfIds() {
        return rnfIds;
    }

    public void setRnfIds(List<Long> rnfIds) {
        this.rnfIds = rnfIds;
    }

    public List<Long> getReglaIds() {
        return reglaIds;
    }

    public void setReglaIds(List<Long> reglaIds) {
        this.reglaIds = reglaIds;
    }
}

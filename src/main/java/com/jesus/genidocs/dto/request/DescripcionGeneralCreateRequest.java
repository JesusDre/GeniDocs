package com.jesus.genidocs.dto.request;

import jakarta.validation.constraints.NotNull;

public class DescripcionGeneralCreateRequest {

    @NotNull
    private Long documentoId;

    private String objetivo;
    private String problema;
    private String usuariosRol;

    public Long getDocumentoId() {
        return documentoId;
    }

    public void setDocumentoId(Long documentoId) {
        this.documentoId = documentoId;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getProblema() {
        return problema;
    }

    public void setProblema(String problema) {
        this.problema = problema;
    }

    public String getUsuariosRol() {
        return usuariosRol;
    }

    public void setUsuariosRol(String usuariosRol) {
        this.usuariosRol = usuariosRol;
    }
}

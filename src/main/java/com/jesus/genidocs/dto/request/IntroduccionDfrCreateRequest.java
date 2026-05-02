package com.jesus.genidocs.dto.request;

import jakarta.validation.constraints.NotNull;

public class IntroduccionDfrCreateRequest {

    @NotNull
    private Long documentoId;

    private String proposito;
    private String alcanceIncluye;
    private String alcanceExcluye;
    private String definiciones;

    public Long getDocumentoId() {
        return documentoId;
    }

    public void setDocumentoId(Long documentoId) {
        this.documentoId = documentoId;
    }

    public String getProposito() {
        return proposito;
    }

    public void setProposito(String proposito) {
        this.proposito = proposito;
    }

    public String getAlcanceIncluye() {
        return alcanceIncluye;
    }

    public void setAlcanceIncluye(String alcanceIncluye) {
        this.alcanceIncluye = alcanceIncluye;
    }

    public String getAlcanceExcluye() {
        return alcanceExcluye;
    }

    public void setAlcanceExcluye(String alcanceExcluye) {
        this.alcanceExcluye = alcanceExcluye;
    }

    public String getDefiniciones() {
        return definiciones;
    }

    public void setDefiniciones(String definiciones) {
        this.definiciones = definiciones;
    }
}

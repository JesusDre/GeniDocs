package com.jesus.genidocs.dto.response;

import java.time.LocalDateTime;

public class IntroduccionDfrResponse {

    private Long id;
    private Long versionId;
    private String proposito;
    private String alcanceIncluye;
    private String alcanceExcluye;
    private String definiciones;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

package com.jesus.genidocs.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "version_rf")
public class VersionRequerimientoFuncional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "version_id")
    private VersionDocumento version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requerimiento_id")
    private RequerimientoFuncional requerimiento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VersionDocumento getVersion() {
        return version;
    }

    public void setVersion(VersionDocumento version) {
        this.version = version;
    }

    public RequerimientoFuncional getRequerimiento() {
        return requerimiento;
    }

    public void setRequerimiento(RequerimientoFuncional requerimiento) {
        this.requerimiento = requerimiento;
    }
}

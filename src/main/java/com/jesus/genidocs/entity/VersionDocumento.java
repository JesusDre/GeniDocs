package com.jesus.genidocs.entity;

import com.jesus.genidocs.entity.enums.EstadoVersion;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "versiones_documento")
public class VersionDocumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "documento_id", nullable = false)
    private DocumentoDfr documento;

    @Column(name = "numero_version", nullable = false, length = 20)
    private String numeroVersion;

    @Column(name = "descripcion_cambios", columnDefinition = "TEXT")
    private String descripcionCambios;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generado_por")
    private Usuario generadoPor;

    @CreationTimestamp
    @Column(name = "fecha_generacion", nullable = false, updatable = false)
    private LocalDateTime fechaGeneracion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoVersion estado = EstadoVersion.ACTIVA;

    @OneToMany(mappedBy = "version", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<VersionRequerimientoFuncional> versionesRf;

    @OneToMany(mappedBy = "version", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<VersionRequerimientoNoFuncional> versionesRnf;

    @OneToMany(mappedBy = "version", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<VersionReglaNegocio> versionesReglas;

    @OneToMany(mappedBy = "version", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ArchivoGenerado> archivosGenerados;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DocumentoDfr getDocumento() {
        return documento;
    }

    public void setDocumento(DocumentoDfr documento) {
        this.documento = documento;
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

    public Usuario getGeneradoPor() {
        return generadoPor;
    }

    public void setGeneradoPor(Usuario generadoPor) {
        this.generadoPor = generadoPor;
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

    public List<VersionRequerimientoFuncional> getVersionesRf() {
        return versionesRf;
    }

    public void setVersionesRf(List<VersionRequerimientoFuncional> versionesRf) {
        this.versionesRf = versionesRf;
    }

    public List<VersionRequerimientoNoFuncional> getVersionesRnf() {
        return versionesRnf;
    }

    public void setVersionesRnf(List<VersionRequerimientoNoFuncional> versionesRnf) {
        this.versionesRnf = versionesRnf;
    }

    public List<VersionReglaNegocio> getVersionesReglas() {
        return versionesReglas;
    }

    public void setVersionesReglas(List<VersionReglaNegocio> versionesReglas) {
        this.versionesReglas = versionesReglas;
    }

    public List<ArchivoGenerado> getArchivosGenerados() {
        return archivosGenerados;
    }

    public void setArchivosGenerados(List<ArchivoGenerado> archivosGenerados) {
        this.archivosGenerados = archivosGenerados;
    }
}

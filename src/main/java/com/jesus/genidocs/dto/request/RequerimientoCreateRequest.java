package com.jesus.genidocs.dto.request;

import com.jesus.genidocs.entity.enums.EstadoRequerimiento;
import com.jesus.genidocs.entity.enums.Prioridad;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request para crear requerimientos funcionales y no funcionales. El código se genera automáticamente por proyecto.")
public class RequerimientoCreateRequest {

    @NotNull(message = "El proyecto ID es requerido")
    @Schema(description = "ID del proyecto", example = "1")
    private Long proyectoId;

    @NotBlank(message = "El tipo es requerido")
    @Schema(description = "Tipo de requerimiento: FUNCIONAL o NO_FUNCIONAL", example = "FUNCIONAL")
    private String tipo;

    @Schema(description = "Título del requerimiento (requerido para FUNCIONAL)", example = "Autenticación de usuarios")
    private String titulo;

    @Schema(description = "Descripción detallada del requerimiento")
    private String descripcion;

    @Schema(description = "Tipo de requerimiento no funcional (SEGURIDAD, RENDIMIENTO, DISPONIBILIDAD, ESCALABILIDAD, USABILIDAD, COMPATIBILIDAD)", example = "SEGURIDAD")
    private String tipoNoFuncional;

    @NotNull(message = "La prioridad es requerida")
    @Schema(description = "Prioridad del requerimiento (ALTA, MEDIA, BAJA)", example = "ALTA")
    private Prioridad prioridad;

    @NotNull(message = "El estado es requerido")
    @Schema(description = "Estado del requerimiento (PENDIENTE, EN_DESARROLLO, COMPLETADO, RECHAZADO)", example = "PENDIENTE")
    private EstadoRequerimiento estado;

    public Long getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(Long proyectoId) {
        this.proyectoId = proyectoId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoNoFuncional() {
        return tipoNoFuncional;
    }

    public void setTipoNoFuncional(String tipoNoFuncional) {
        this.tipoNoFuncional = tipoNoFuncional;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public EstadoRequerimiento getEstado() {
        return estado;
    }

    public void setEstado(EstadoRequerimiento estado) {
        this.estado = estado;
    }
}

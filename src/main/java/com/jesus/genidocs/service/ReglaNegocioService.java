package com.jesus.genidocs.service;

import com.jesus.genidocs.entity.Proyecto;
import com.jesus.genidocs.entity.ReglaNegocio;
import com.jesus.genidocs.entity.enums.EstadoReglaNegocio;
import com.jesus.genidocs.exception.BusinessValidationException;
import com.jesus.genidocs.exception.ResourceNotFoundException;
import com.jesus.genidocs.repository.ProyectoRepository;
import com.jesus.genidocs.repository.ReglaNegocioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReglaNegocioService {

    private final ReglaNegocioRepository reglaNegocioRepository;
    private final ProyectoRepository proyectoRepository;

    public ReglaNegocioService(ReglaNegocioRepository reglaNegocioRepository,
                               ProyectoRepository proyectoRepository) {
        this.reglaNegocioRepository = reglaNegocioRepository;
        this.proyectoRepository = proyectoRepository;
    }

    @Transactional
    public ReglaNegocio crearRegla(Long proyectoId, String codigo, String descripcion, EstadoReglaNegocio estado) {
        validarId(proyectoId, "proyectoId");
        validarTexto(descripcion, "descripcion");
        if (estado == null) {
            throw new BusinessValidationException("El estado es obligatorio");
        }

        Proyecto proyecto = proyectoRepository.findById(proyectoId)
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado"));

        ReglaNegocio regla = new ReglaNegocio();
        regla.setProyecto(proyecto);
        regla.setCodigo(limpiar(codigo));
        regla.setDescripcion(descripcion.trim());
        regla.setEstado(estado);

        return reglaNegocioRepository.save(regla);
    }

    private void validarTexto(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new BusinessValidationException("El campo " + campo + " es obligatorio");
        }
    }

    private void validarId(Long id, String campo) {
        if (id == null || id <= 0) {
            throw new BusinessValidationException("El campo " + campo + " es obligatorio");
        }
    }

    private String limpiar(String valor) {
        return valor == null ? null : valor.trim();
    }
}

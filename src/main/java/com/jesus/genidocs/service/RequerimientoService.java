package com.jesus.genidocs.service;

import com.jesus.genidocs.entity.Proyecto;
import com.jesus.genidocs.entity.RequerimientoFuncional;
import com.jesus.genidocs.entity.RequerimientoNoFuncional;
import com.jesus.genidocs.entity.enums.EstadoRequerimiento;
import com.jesus.genidocs.entity.enums.Prioridad;
import com.jesus.genidocs.exception.BusinessValidationException;
import com.jesus.genidocs.exception.ResourceNotFoundException;
import com.jesus.genidocs.repository.ProyectoRepository;
import com.jesus.genidocs.repository.RequerimientoFuncionalRepository;
import com.jesus.genidocs.repository.RequerimientoNoFuncionalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
public class RequerimientoService {

    private final ProyectoRepository proyectoRepository;
    private final RequerimientoFuncionalRepository requerimientoFuncionalRepository;
    private final RequerimientoNoFuncionalRepository requerimientoNoFuncionalRepository;

    public RequerimientoService(ProyectoRepository proyectoRepository,
                                RequerimientoFuncionalRepository requerimientoFuncionalRepository,
                                RequerimientoNoFuncionalRepository requerimientoNoFuncionalRepository) {
        this.proyectoRepository = proyectoRepository;
        this.requerimientoFuncionalRepository = requerimientoFuncionalRepository;
        this.requerimientoNoFuncionalRepository = requerimientoNoFuncionalRepository;
    }

    @Transactional
    public RequerimientoFuncional agregarRequerimientoFuncional(Long proyectoId, String codigo, String titulo,
                                                                String descripcion, Prioridad prioridad,
                                                                EstadoRequerimiento estado) {
        validarId(proyectoId, "proyectoId");
        validarTexto(titulo, "titulo");
        if (prioridad == null) {
            throw new BusinessValidationException("La prioridad es obligatoria");
        }
        if (estado == null) {
            throw new BusinessValidationException("El estado es obligatorio");
        }

        Proyecto proyecto = proyectoRepository.findById(proyectoId)
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado"));

        RequerimientoFuncional requerimiento = new RequerimientoFuncional();
        requerimiento.setProyecto(proyecto);
        requerimiento.setCodigo(generarCodigoFuncional(proyectoId));
        requerimiento.setTitulo(titulo.trim());
        requerimiento.setDescripcion(limpiar(descripcion));
        requerimiento.setPrioridad(prioridad);
        requerimiento.setEstado(estado);

        return requerimientoFuncionalRepository.save(requerimiento);
    }

    @Transactional
    public RequerimientoNoFuncional agregarRequerimientoNoFuncional(Long proyectoId, String tipo,
                                                                    String descripcion, Prioridad prioridad,
                                                                    EstadoRequerimiento estado) {
        validarId(proyectoId, "proyectoId");
        validarTexto(tipo, "tipo");
        if (prioridad == null) {
            throw new BusinessValidationException("La prioridad es obligatoria");
        }
        if (estado == null) {
            throw new BusinessValidationException("El estado es obligatorio");
        }

        Proyecto proyecto = proyectoRepository.findById(proyectoId)
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado"));

        RequerimientoNoFuncional requerimiento = new RequerimientoNoFuncional();
        requerimiento.setProyecto(proyecto);
        requerimiento.setCodigo(generarCodigoNoFuncional(proyectoId));
        requerimiento.setTipo(tipo.trim());
        requerimiento.setDescripcion(limpiar(descripcion));
        requerimiento.setPrioridad(prioridad);
        requerimiento.setEstado(estado);

        return requerimientoNoFuncionalRepository.save(requerimiento);
    }

    private String generarCodigoFuncional(Long proyectoId) {
        var ultimoRequerimiento = requerimientoFuncionalRepository.findTopByProyectoIdOrderByCodigoDesc(proyectoId);
        long siguiente = 1;
        if (ultimoRequerimiento.isPresent()) {
            String codigoUltimo = ultimoRequerimiento.get().getCodigo();
            String numero = codigoUltimo.substring(3); // Extrae "001" de "RF-001"
            siguiente = Long.parseLong(numero) + 1;
        }
        return String.format(Locale.ROOT, "RF-%03d", siguiente);
    }

    private String generarCodigoNoFuncional(Long proyectoId) {
        var ultimoRequerimiento = requerimientoNoFuncionalRepository.findTopByProyectoIdOrderByCodigoDesc(proyectoId);
        long siguiente = 1;
        if (ultimoRequerimiento.isPresent()) {
            String codigoUltimo = ultimoRequerimiento.get().getCodigo();
            String numero = codigoUltimo.substring(4); // Extrae "001" de "RNF-001"
            siguiente = Long.parseLong(numero) + 1;
        }
        return String.format(Locale.ROOT, "RNF-%03d", siguiente);
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

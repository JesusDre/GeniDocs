package com.jesus.genidocs.service;

import com.jesus.genidocs.entity.DocumentoDfr;
import com.jesus.genidocs.entity.Proyecto;
import com.jesus.genidocs.exception.BusinessValidationException;
import com.jesus.genidocs.exception.ResourceNotFoundException;
import com.jesus.genidocs.repository.DocumentoDfrRepository;
import com.jesus.genidocs.repository.ProyectoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DocumentoDfrService {

    private final DocumentoDfrRepository documentoDfrRepository;
    private final ProyectoRepository proyectoRepository;

    public DocumentoDfrService(DocumentoDfrRepository documentoDfrRepository,
                               ProyectoRepository proyectoRepository) {
        this.documentoDfrRepository = documentoDfrRepository;
        this.proyectoRepository = proyectoRepository;
    }

    @Transactional
    public DocumentoDfr crearDocumento(Long proyectoId, String titulo, String descripcion) {
        validarId(proyectoId, "proyectoId");
        validarTexto(titulo, "titulo");

        Proyecto proyecto = proyectoRepository.findById(proyectoId)
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado"));

        DocumentoDfr documento = new DocumentoDfr();
        documento.setProyecto(proyecto);
        documento.setTitulo(titulo.trim());
        documento.setDescripcion(limpiar(descripcion));

        return documentoDfrRepository.save(documento);
    }

    @Transactional(readOnly = true)
    public DocumentoDfr obtenerDocumento(Long documentoId) {
        validarId(documentoId, "documentoId");
        return documentoDfrRepository.findByIdWithProyecto(documentoId)
                .orElseThrow(() -> new ResourceNotFoundException("Documento DFR no encontrado"));
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

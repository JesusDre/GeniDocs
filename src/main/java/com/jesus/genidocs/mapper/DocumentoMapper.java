package com.jesus.genidocs.mapper;

import com.jesus.genidocs.dto.response.DocumentoResponse;
import com.jesus.genidocs.entity.DocumentoDfr;
import org.springframework.stereotype.Component;

@Component
public class DocumentoMapper {

    public DocumentoResponse toResponse(DocumentoDfr documento) {
        DocumentoResponse response = new DocumentoResponse();
        response.setId(documento.getId());
        response.setProyectoId(documento.getProyecto().getId());
        response.setTitulo(documento.getTitulo());
        response.setDescripcion(documento.getDescripcion());
        response.setEstado(documento.getEstado());
        response.setCreatedAt(documento.getCreatedAt());
        response.setUpdatedAt(documento.getUpdatedAt());
        return response;
    }
}

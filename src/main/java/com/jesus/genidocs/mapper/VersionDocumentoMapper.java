package com.jesus.genidocs.mapper;

import com.jesus.genidocs.dto.response.VersionDocumentoResponse;
import com.jesus.genidocs.entity.VersionDocumento;
import org.springframework.stereotype.Component;

@Component
public class VersionDocumentoMapper {

    public VersionDocumentoResponse toResponse(VersionDocumento version) {
        VersionDocumentoResponse response = new VersionDocumentoResponse();
        response.setId(version.getId());
        response.setDocumentoId(version.getDocumento().getId());
        response.setNumeroVersion(version.getNumeroVersion());
        response.setDescripcionCambios(version.getDescripcionCambios());
        response.setGeneradoPorId(version.getGeneradoPor() == null ? null : version.getGeneradoPor().getId());
        response.setFechaGeneracion(version.getFechaGeneracion());
        response.setEstado(version.getEstado());
        return response;
    }
}

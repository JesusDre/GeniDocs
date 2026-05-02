package com.jesus.genidocs.mapper;

import com.jesus.genidocs.dto.response.AnexoResponse;
import com.jesus.genidocs.entity.Anexo;
import org.springframework.stereotype.Component;

@Component
public class AnexoMapper {

    public AnexoResponse toResponse(Anexo anexo) {
        AnexoResponse response = new AnexoResponse();
        response.setId(anexo.getId());
        response.setVersionId(anexo.getVersion().getId());
        response.setTipo(anexo.getTipo());
        response.setNombre(anexo.getNombre());
        response.setDescripcion(anexo.getDescripcion());
        response.setRutaArchivo(anexo.getRutaArchivo());
        response.setCreatedAt(anexo.getCreatedAt());
        response.setUpdatedAt(anexo.getUpdatedAt());
        return response;
    }
}

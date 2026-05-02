package com.jesus.genidocs.mapper;

import com.jesus.genidocs.dto.response.DescripcionGeneralResponse;
import com.jesus.genidocs.entity.DescripcionGeneral;
import org.springframework.stereotype.Component;

@Component
public class DescripcionGeneralMapper {

    public DescripcionGeneralResponse toResponse(DescripcionGeneral descripcion) {
        DescripcionGeneralResponse response = new DescripcionGeneralResponse();
        response.setId(descripcion.getId());
        response.setVersionId(descripcion.getVersion().getId());
        response.setObjetivo(descripcion.getObjetivo());
        response.setProblema(descripcion.getProblema());
        response.setUsuariosRol(descripcion.getUsuariosRol());
        response.setCreatedAt(descripcion.getCreatedAt());
        response.setUpdatedAt(descripcion.getUpdatedAt());
        return response;
    }
}

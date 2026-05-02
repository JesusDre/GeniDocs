package com.jesus.genidocs.mapper;

import com.jesus.genidocs.dto.response.ModeloDatosResponse;
import com.jesus.genidocs.entity.ModeloDatos;
import org.springframework.stereotype.Component;

@Component
public class ModeloDatosMapper {

    public ModeloDatosResponse toResponse(ModeloDatos modelo) {
        ModeloDatosResponse response = new ModeloDatosResponse();
        response.setId(modelo.getId());
        response.setVersionId(modelo.getVersion().getId());
        response.setEntidades(modelo.getEntidades());
        response.setRelaciones(modelo.getRelaciones());
        response.setDiagrama(modelo.getDiagrama());
        response.setCreatedAt(modelo.getCreatedAt());
        response.setUpdatedAt(modelo.getUpdatedAt());
        return response;
    }
}

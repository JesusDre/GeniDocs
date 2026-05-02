package com.jesus.genidocs.mapper;

import com.jesus.genidocs.dto.response.ReglaNegocioResponse;
import com.jesus.genidocs.entity.ReglaNegocio;
import org.springframework.stereotype.Component;

@Component
public class ReglaNegocioMapper {

    public ReglaNegocioResponse toResponse(ReglaNegocio regla) {
        ReglaNegocioResponse response = new ReglaNegocioResponse();
        response.setId(regla.getId());
        response.setProyectoId(regla.getProyecto().getId());
        response.setCodigo(regla.getCodigo());
        response.setDescripcion(regla.getDescripcion());
        response.setEstado(regla.getEstado());
        response.setCreatedAt(regla.getCreatedAt());
        response.setUpdatedAt(regla.getUpdatedAt());
        return response;
    }
}

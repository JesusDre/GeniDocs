package com.jesus.genidocs.mapper;

import com.jesus.genidocs.dto.response.ProyectoResponse;
import com.jesus.genidocs.entity.Proyecto;
import org.springframework.stereotype.Component;

@Component
public class ProyectoMapper {

    public ProyectoResponse toResponse(Proyecto proyecto) {
        ProyectoResponse response = new ProyectoResponse();
        response.setId(proyecto.getId());
        response.setNombre(proyecto.getNombre());
        response.setDescripcion(proyecto.getDescripcion());
        response.setClienteId(proyecto.getCliente().getId());
        response.setResponsableId(proyecto.getResponsable().getId());
        response.setEstado(proyecto.getEstado());
        response.setCreatedAt(proyecto.getCreatedAt());
        response.setUpdatedAt(proyecto.getUpdatedAt());
        return response;
    }
}

package com.jesus.genidocs.mapper;

import com.jesus.genidocs.dto.response.RequerimientoResponse;
import com.jesus.genidocs.entity.RequerimientoFuncional;
import com.jesus.genidocs.entity.RequerimientoNoFuncional;
import org.springframework.stereotype.Component;

@Component
public class RequerimientoMapper {

    public RequerimientoResponse toResponse(RequerimientoFuncional rf) {
        RequerimientoResponse response = new RequerimientoResponse();
        response.setId(rf.getId());
        response.setProyectoId(rf.getProyecto().getId());
        response.setTipo("FUNCIONAL");
        response.setCodigo(rf.getCodigo());
        response.setTitulo(rf.getTitulo());
        response.setDescripcion(rf.getDescripcion());
        response.setPrioridad(rf.getPrioridad());
        response.setEstado(rf.getEstado());
        return response;
    }

    public RequerimientoResponse toResponse(RequerimientoNoFuncional rnf) {
        RequerimientoResponse response = new RequerimientoResponse();
        response.setId(rnf.getId());
        response.setProyectoId(rnf.getProyecto().getId());
        response.setTipo("NO_FUNCIONAL");
        response.setTipoNoFuncional(rnf.getTipo());
        response.setDescripcion(rnf.getDescripcion());
        response.setPrioridad(rnf.getPrioridad());
        response.setEstado(rnf.getEstado());
        return response;
    }
}

package com.jesus.genidocs.mapper;

import com.jesus.genidocs.dto.response.CriterioAceptacionDwtResponse;
import com.jesus.genidocs.entity.CriterioAceptacionDwt;
import org.springframework.stereotype.Component;

@Component
public class CriterioAceptacionDwtMapper {

    public CriterioAceptacionDwtResponse toResponse(CriterioAceptacionDwt criterio) {
        CriterioAceptacionDwtResponse response = new CriterioAceptacionDwtResponse();
        response.setId(criterio.getId());
        response.setRequerimientoFuncionalId(criterio.getRequerimientoFuncional().getId());
        response.setDado(criterio.getDado());
        response.setCuando(criterio.getCuando());
        response.setEntonces(criterio.getEntonces());
        response.setEstado(criterio.getEstado());
        response.setCreatedAt(criterio.getCreatedAt());
        response.setUpdatedAt(criterio.getUpdatedAt());
        return response;
    }
}

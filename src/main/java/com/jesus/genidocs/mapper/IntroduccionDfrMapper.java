package com.jesus.genidocs.mapper;

import com.jesus.genidocs.dto.response.IntroduccionDfrResponse;
import com.jesus.genidocs.entity.IntroduccionDfr;
import org.springframework.stereotype.Component;

@Component
public class IntroduccionDfrMapper {

    public IntroduccionDfrResponse toResponse(IntroduccionDfr introduccion) {
        IntroduccionDfrResponse response = new IntroduccionDfrResponse();
        response.setId(introduccion.getId());
        response.setVersionId(introduccion.getVersion().getId());
        response.setProposito(introduccion.getProposito());
        response.setAlcanceIncluye(introduccion.getAlcanceIncluye());
        response.setAlcanceExcluye(introduccion.getAlcanceExcluye());
        response.setDefiniciones(introduccion.getDefiniciones());
        response.setCreatedAt(introduccion.getCreatedAt());
        response.setUpdatedAt(introduccion.getUpdatedAt());
        return response;
    }
}

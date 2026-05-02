package com.jesus.genidocs.mapper;

import com.jesus.genidocs.dto.response.ArchivoGeneradoResponse;
import com.jesus.genidocs.entity.ArchivoGenerado;
import org.springframework.stereotype.Component;

@Component
public class ArchivoGeneradoMapper {

    public ArchivoGeneradoResponse toResponse(ArchivoGenerado archivo) {
        ArchivoGeneradoResponse response = new ArchivoGeneradoResponse();
        response.setId(archivo.getId());
        response.setVersionId(archivo.getVersion().getId());
        response.setNombreArchivo(archivo.getNombreArchivo());
        response.setRutaArchivo(archivo.getRutaArchivo());
        response.setTipo(archivo.getTipo());
        response.setGeneradoEn(archivo.getGeneradoEn());
        return response;
    }
}

package com.jesus.genidocs.mapper;

import com.jesus.genidocs.dto.response.ClienteResponse;
import com.jesus.genidocs.entity.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public ClienteResponse toResponse(Cliente cliente) {
        ClienteResponse response = new ClienteResponse();
        response.setId(cliente.getId());
        response.setNombre(cliente.getNombre());
        response.setEmpresa(cliente.getEmpresa());
        response.setEmail(cliente.getEmail());
        response.setTelefono(cliente.getTelefono());
        response.setDireccion(cliente.getDireccion());
        response.setEstado(cliente.getEstado());
        response.setCreatedAt(cliente.getCreatedAt());
        response.setUpdatedAt(cliente.getUpdatedAt());
        return response;
    }
}

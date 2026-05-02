package com.jesus.genidocs.service;

import com.jesus.genidocs.entity.Cliente;
import com.jesus.genidocs.exception.BusinessValidationException;
import com.jesus.genidocs.exception.ResourceNotFoundException;
import com.jesus.genidocs.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public Cliente crearCliente(String nombre, String empresa, String email, String telefono, String direccion) {
        validarTexto(nombre, "nombre");

        Cliente cliente = new Cliente();
        cliente.setNombre(nombre.trim());
        cliente.setEmpresa(limpiar(empresa));
        cliente.setEmail(limpiar(email));
        cliente.setTelefono(limpiar(telefono));
        cliente.setDireccion(limpiar(direccion));

        return clienteRepository.save(cliente);
    }

    @Transactional(readOnly = true)
    public Cliente obtenerCliente(Long clienteId) {
        validarId(clienteId, "clienteId");
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
    }

    @Transactional(readOnly = true)
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    private void validarTexto(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new BusinessValidationException("El campo " + campo + " es obligatorio");
        }
    }

    private void validarId(Long id, String campo) {
        if (id == null || id <= 0) {
            throw new BusinessValidationException("El campo " + campo + " es obligatorio");
        }
    }

    private String limpiar(String valor) {
        return valor == null ? null : valor.trim();
    }
}

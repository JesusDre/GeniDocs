package com.jesus.genidocs.service;

import com.jesus.genidocs.entity.Cliente;
import com.jesus.genidocs.entity.Proyecto;
import com.jesus.genidocs.entity.Usuario;
import com.jesus.genidocs.exception.BusinessValidationException;
import com.jesus.genidocs.exception.ResourceNotFoundException;
import com.jesus.genidocs.repository.ClienteRepository;
import com.jesus.genidocs.repository.ProyectoRepository;
import com.jesus.genidocs.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProyectoService {

    private final ProyectoRepository proyectoRepository;
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;

    public ProyectoService(ProyectoRepository proyectoRepository, ClienteRepository clienteRepository,
                           UsuarioRepository usuarioRepository) {
        this.proyectoRepository = proyectoRepository;
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Proyecto crearProyecto(String nombre, String descripcion, Long clienteId, Long responsableId) {
        validarTexto(nombre, "nombre");
        validarId(clienteId, "clienteId");
        validarId(responsableId, "responsableId");

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        Usuario responsable = usuarioRepository.findById(responsableId)
                .orElseThrow(() -> new ResourceNotFoundException("Responsable no encontrado"));

        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(nombre.trim());
        proyecto.setDescripcion(limpiar(descripcion));
        proyecto.setCliente(cliente);
        proyecto.setResponsable(responsable);

        return proyectoRepository.save(proyecto);
    }

    @Transactional(readOnly = true)
    public Proyecto obtenerProyecto(Long proyectoId) {
        validarId(proyectoId, "proyectoId");
        return proyectoRepository.findById(proyectoId)
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado"));
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

package com.jesus.genidocs.service;

import com.jesus.genidocs.entity.Usuario;
import com.jesus.genidocs.entity.enums.RolUsuario;
import com.jesus.genidocs.exception.BusinessValidationException;
import com.jesus.genidocs.exception.ResourceNotFoundException;
import com.jesus.genidocs.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Usuario crearUsuario(String nombre, String email, String password, RolUsuario rol) {
        validarTexto(nombre, "nombre");
        validarTexto(email, "email");
        validarTexto(password, "password");
        if (rol == null) {
            throw new BusinessValidationException("El rol es obligatorio");
        }
        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new BusinessValidationException("El email ya esta registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre.trim());
        usuario.setEmail(email.trim());
        usuario.setPassword(password);
        usuario.setRol(rol);

        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public Usuario obtenerUsuario(Long usuarioId) {
        validarId(usuarioId, "usuarioId");
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
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
}

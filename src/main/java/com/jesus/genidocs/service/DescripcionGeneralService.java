package com.jesus.genidocs.service;

import com.jesus.genidocs.entity.DescripcionGeneral;
import com.jesus.genidocs.entity.VersionDocumento;
import com.jesus.genidocs.exception.BusinessValidationException;
import com.jesus.genidocs.exception.ResourceNotFoundException;
import com.jesus.genidocs.repository.DescripcionGeneralRepository;
import com.jesus.genidocs.repository.VersionDocumentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DescripcionGeneralService {

    private final DescripcionGeneralRepository descripcionGeneralRepository;
    private final VersionDocumentoRepository versionDocumentoRepository;
    private final VersionDocumentoService versionDocumentoService;

    public DescripcionGeneralService(DescripcionGeneralRepository descripcionGeneralRepository,
                                     VersionDocumentoRepository versionDocumentoRepository,
                                     VersionDocumentoService versionDocumentoService) {
        this.descripcionGeneralRepository = descripcionGeneralRepository;
        this.versionDocumentoRepository = versionDocumentoRepository;
        this.versionDocumentoService = versionDocumentoService;
    }

    @Transactional
    public DescripcionGeneral crear(Long versionId, String objetivo, String problema, String usuariosRol) {
        validarId(versionId, "versionId");
        VersionDocumento version = versionDocumentoRepository.findById(versionId)
                .orElseThrow(() -> new ResourceNotFoundException("Version no encontrada"));

        DescripcionGeneral descripcion = new DescripcionGeneral();
        descripcion.setVersion(version);
        descripcion.setObjetivo(limpiar(objetivo));
        descripcion.setProblema(limpiar(problema));
        descripcion.setUsuariosRol(limpiar(usuariosRol));

        return descripcionGeneralRepository.save(descripcion);
    }

    @Transactional(readOnly = true)
    public List<DescripcionGeneral> listarPorVersion(Long versionId) {
        validarId(versionId, "versionId");
        return descripcionGeneralRepository.findByVersionId(versionId);
    }

    @Transactional
    public DescripcionGeneral guardarPorDocumento(Long documentoId, String objetivo, String problema, String usuariosRol,
                                                  Long usuarioId) {
        VersionDocumento version = versionDocumentoService.crearVersionAutomatica(documentoId, usuarioId,
                "Actualizacion de descripcion general");
        DescripcionGeneral descripcion = new DescripcionGeneral();
        descripcion.setVersion(version);
        descripcion.setObjetivo(limpiar(objetivo));
        descripcion.setProblema(limpiar(problema));
        descripcion.setUsuariosRol(limpiar(usuariosRol));
        return descripcionGeneralRepository.save(descripcion);
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

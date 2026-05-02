package com.jesus.genidocs.service;

import com.jesus.genidocs.entity.IntroduccionDfr;
import com.jesus.genidocs.entity.VersionDocumento;
import com.jesus.genidocs.exception.BusinessValidationException;
import com.jesus.genidocs.exception.ResourceNotFoundException;
import com.jesus.genidocs.repository.IntroduccionDfrRepository;
import com.jesus.genidocs.repository.VersionDocumentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IntroduccionDfrService {

    private final IntroduccionDfrRepository introduccionDfrRepository;
    private final VersionDocumentoRepository versionDocumentoRepository;
    private final VersionDocumentoService versionDocumentoService;

    public IntroduccionDfrService(IntroduccionDfrRepository introduccionDfrRepository,
                                  VersionDocumentoRepository versionDocumentoRepository,
                                  VersionDocumentoService versionDocumentoService) {
        this.introduccionDfrRepository = introduccionDfrRepository;
        this.versionDocumentoRepository = versionDocumentoRepository;
        this.versionDocumentoService = versionDocumentoService;
    }

    @Transactional
    public IntroduccionDfr crear(Long versionId, String proposito, String alcanceIncluye,
                                 String alcanceExcluye, String definiciones) {
        validarId(versionId, "versionId");
        VersionDocumento version = versionDocumentoRepository.findById(versionId)
                .orElseThrow(() -> new ResourceNotFoundException("Version no encontrada"));

        IntroduccionDfr introduccion = new IntroduccionDfr();
        introduccion.setVersion(version);
        introduccion.setProposito(limpiar(proposito));
        introduccion.setAlcanceIncluye(limpiar(alcanceIncluye));
        introduccion.setAlcanceExcluye(limpiar(alcanceExcluye));
        introduccion.setDefiniciones(limpiar(definiciones));

        return introduccionDfrRepository.save(introduccion);
    }

    @Transactional(readOnly = true)
    public List<IntroduccionDfr> listarPorVersion(Long versionId) {
        validarId(versionId, "versionId");
        return introduccionDfrRepository.findByVersionId(versionId);
    }

    @Transactional
    public IntroduccionDfr guardarPorDocumento(Long documentoId, String proposito, String alcanceIncluye,
                                               String alcanceExcluye, String definiciones,
                                               Long usuarioId) {
        validarId(documentoId, "documentoId");
        VersionDocumento version = versionDocumentoService.crearVersionAutomatica(documentoId, usuarioId,
                "Actualizacion de introduccion");

        IntroduccionDfr introduccion = new IntroduccionDfr();
        introduccion.setVersion(version);
        introduccion.setProposito(limpiar(proposito));
        introduccion.setAlcanceIncluye(limpiar(alcanceIncluye));
        introduccion.setAlcanceExcluye(limpiar(alcanceExcluye));
        introduccion.setDefiniciones(limpiar(definiciones));
        return introduccionDfrRepository.save(introduccion);
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

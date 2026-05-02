package com.jesus.genidocs.service;

import com.jesus.genidocs.entity.ModeloDatos;
import com.jesus.genidocs.entity.VersionDocumento;
import com.jesus.genidocs.exception.BusinessValidationException;
import com.jesus.genidocs.exception.ResourceNotFoundException;
import com.jesus.genidocs.repository.ModeloDatosRepository;
import com.jesus.genidocs.repository.VersionDocumentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ModeloDatosService {

    private final ModeloDatosRepository modeloDatosRepository;
    private final VersionDocumentoRepository versionDocumentoRepository;
    private final VersionDocumentoService versionDocumentoService;

    public ModeloDatosService(ModeloDatosRepository modeloDatosRepository,
                              VersionDocumentoRepository versionDocumentoRepository,
                              VersionDocumentoService versionDocumentoService) {
        this.modeloDatosRepository = modeloDatosRepository;
        this.versionDocumentoRepository = versionDocumentoRepository;
        this.versionDocumentoService = versionDocumentoService;
    }

    @Transactional
    public ModeloDatos crear(Long versionId, String entidades, String relaciones, String diagrama) {
        validarId(versionId, "versionId");
        VersionDocumento version = versionDocumentoRepository.findById(versionId)
                .orElseThrow(() -> new ResourceNotFoundException("Version no encontrada"));

        ModeloDatos modelo = new ModeloDatos();
        modelo.setVersion(version);
        modelo.setEntidades(limpiar(entidades));
        modelo.setRelaciones(limpiar(relaciones));
        modelo.setDiagrama(limpiar(diagrama));

        return modeloDatosRepository.save(modelo);
    }

    @Transactional(readOnly = true)
    public List<ModeloDatos> listarPorVersion(Long versionId) {
        validarId(versionId, "versionId");
        return modeloDatosRepository.findByVersionId(versionId);
    }

    @Transactional
    public ModeloDatos guardarPorDocumento(Long documentoId, String entidades, String relaciones, String diagrama,
                                           Long usuarioId) {
        VersionDocumento version = versionDocumentoService.crearVersionAutomatica(documentoId, usuarioId,
                "Actualizacion de modelo de datos");
        ModeloDatos modelo = new ModeloDatos();
        modelo.setVersion(version);
        modelo.setEntidades(limpiar(entidades));
        modelo.setRelaciones(limpiar(relaciones));
        modelo.setDiagrama(limpiar(diagrama));
        return modeloDatosRepository.save(modelo);
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

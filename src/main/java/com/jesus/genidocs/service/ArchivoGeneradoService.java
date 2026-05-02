package com.jesus.genidocs.service;

import com.jesus.genidocs.entity.ArchivoGenerado;
import com.jesus.genidocs.entity.VersionDocumento;
import com.jesus.genidocs.entity.enums.TipoArchivo;
import com.jesus.genidocs.exception.BusinessValidationException;
import com.jesus.genidocs.exception.ResourceNotFoundException;
import com.jesus.genidocs.repository.ArchivoGeneradoRepository;
import com.jesus.genidocs.repository.VersionDocumentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArchivoGeneradoService {

    private final ArchivoGeneradoRepository archivoGeneradoRepository;
    private final VersionDocumentoRepository versionDocumentoRepository;

    public ArchivoGeneradoService(ArchivoGeneradoRepository archivoGeneradoRepository,
                                  VersionDocumentoRepository versionDocumentoRepository) {
        this.archivoGeneradoRepository = archivoGeneradoRepository;
        this.versionDocumentoRepository = versionDocumentoRepository;
    }

    @Transactional
    public ArchivoGenerado registrar(Long versionId, String nombreArchivo, String rutaArchivo, TipoArchivo tipo) {
        validarId(versionId, "versionId");
        if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) {
            throw new BusinessValidationException("El nombreArchivo es obligatorio");
        }
        if (rutaArchivo == null || rutaArchivo.trim().isEmpty()) {
            throw new BusinessValidationException("La rutaArchivo es obligatoria");
        }
        if (tipo == null) {
            throw new BusinessValidationException("El tipo es obligatorio");
        }

        VersionDocumento version = versionDocumentoRepository.findById(versionId)
                .orElseThrow(() -> new ResourceNotFoundException("Version no encontrada"));

        ArchivoGenerado archivo = new ArchivoGenerado();
        archivo.setVersion(version);
        archivo.setNombreArchivo(nombreArchivo.trim());
        archivo.setRutaArchivo(rutaArchivo.trim());
        archivo.setTipo(tipo);

        return archivoGeneradoRepository.save(archivo);
    }

    @Transactional(readOnly = true)
    public List<ArchivoGenerado> listarPorVersion(Long versionId) {
        validarId(versionId, "versionId");
        return archivoGeneradoRepository.findByVersionId(versionId);
    }

    private void validarId(Long id, String campo) {
        if (id == null || id <= 0) {
            throw new BusinessValidationException("El campo " + campo + " es obligatorio");
        }
    }
}

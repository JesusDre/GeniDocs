package com.jesus.genidocs.service;

import com.jesus.genidocs.entity.Anexo;
import com.jesus.genidocs.entity.VersionDocumento;
import com.jesus.genidocs.entity.enums.TipoAnexo;
import com.jesus.genidocs.exception.BusinessValidationException;
import com.jesus.genidocs.exception.ResourceNotFoundException;
import com.jesus.genidocs.repository.AnexoRepository;
import com.jesus.genidocs.repository.VersionDocumentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnexoService {

    private final AnexoRepository anexoRepository;
    private final VersionDocumentoRepository versionDocumentoRepository;

    public AnexoService(AnexoRepository anexoRepository,
                        VersionDocumentoRepository versionDocumentoRepository) {
        this.anexoRepository = anexoRepository;
        this.versionDocumentoRepository = versionDocumentoRepository;
    }

    @Transactional
    public Anexo crear(Long versionId, TipoAnexo tipo, String nombre, String descripcion, String rutaArchivo) {
        validarId(versionId, "versionId");
        if (tipo == null) {
            throw new BusinessValidationException("El tipo es obligatorio");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new BusinessValidationException("El nombre es obligatorio");
        }

        VersionDocumento version = versionDocumentoRepository.findById(versionId)
                .orElseThrow(() -> new ResourceNotFoundException("Version no encontrada"));

        Anexo anexo = new Anexo();
        anexo.setVersion(version);
        anexo.setTipo(tipo);
        anexo.setNombre(nombre.trim());
        anexo.setDescripcion(limpiar(descripcion));
        anexo.setRutaArchivo(limpiar(rutaArchivo));

        return anexoRepository.save(anexo);
    }

    @Transactional(readOnly = true)
    public List<Anexo> listarPorVersion(Long versionId) {
        validarId(versionId, "versionId");
        return anexoRepository.findByVersionId(versionId);
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

package com.jesus.genidocs.service;

import com.jesus.genidocs.entity.DocumentoDfr;
import com.jesus.genidocs.entity.ReglaNegocio;
import com.jesus.genidocs.entity.RequerimientoFuncional;
import com.jesus.genidocs.entity.RequerimientoNoFuncional;
import com.jesus.genidocs.entity.Usuario;
import com.jesus.genidocs.entity.VersionDocumento;
import com.jesus.genidocs.entity.VersionReglaNegocio;
import com.jesus.genidocs.entity.VersionRequerimientoFuncional;
import com.jesus.genidocs.entity.VersionRequerimientoNoFuncional;
import com.jesus.genidocs.exception.BusinessValidationException;
import com.jesus.genidocs.exception.ResourceNotFoundException;
import com.jesus.genidocs.repository.DocumentoDfrRepository;
import com.jesus.genidocs.repository.ReglaNegocioRepository;
import com.jesus.genidocs.repository.RequerimientoFuncionalRepository;
import com.jesus.genidocs.repository.RequerimientoNoFuncionalRepository;
import com.jesus.genidocs.repository.UsuarioRepository;
import com.jesus.genidocs.repository.VersionDocumentoRepository;
import com.jesus.genidocs.repository.VersionReglaNegocioRepository;
import com.jesus.genidocs.repository.VersionRequerimientoFuncionalRepository;
import com.jesus.genidocs.repository.VersionRequerimientoNoFuncionalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class VersionDocumentoService {

    private final VersionDocumentoRepository versionDocumentoRepository;
    private final DocumentoDfrRepository documentoDfrRepository;
    private final UsuarioRepository usuarioRepository;
    private final RequerimientoFuncionalRepository requerimientoFuncionalRepository;
    private final RequerimientoNoFuncionalRepository requerimientoNoFuncionalRepository;
    private final ReglaNegocioRepository reglaNegocioRepository;
    private final VersionRequerimientoFuncionalRepository versionRequerimientoFuncionalRepository;
    private final VersionRequerimientoNoFuncionalRepository versionRequerimientoNoFuncionalRepository;
    private final VersionReglaNegocioRepository versionReglaNegocioRepository;

    public VersionDocumentoService(VersionDocumentoRepository versionDocumentoRepository,
                                   DocumentoDfrRepository documentoDfrRepository,
                                   UsuarioRepository usuarioRepository,
                                   RequerimientoFuncionalRepository requerimientoFuncionalRepository,
                                   RequerimientoNoFuncionalRepository requerimientoNoFuncionalRepository,
                                   ReglaNegocioRepository reglaNegocioRepository,
                                   VersionRequerimientoFuncionalRepository versionRequerimientoFuncionalRepository,
                                   VersionRequerimientoNoFuncionalRepository versionRequerimientoNoFuncionalRepository,
                                   VersionReglaNegocioRepository versionReglaNegocioRepository) {
        this.versionDocumentoRepository = versionDocumentoRepository;
        this.documentoDfrRepository = documentoDfrRepository;
        this.usuarioRepository = usuarioRepository;
        this.requerimientoFuncionalRepository = requerimientoFuncionalRepository;
        this.requerimientoNoFuncionalRepository = requerimientoNoFuncionalRepository;
        this.reglaNegocioRepository = reglaNegocioRepository;
        this.versionRequerimientoFuncionalRepository = versionRequerimientoFuncionalRepository;
        this.versionRequerimientoNoFuncionalRepository = versionRequerimientoNoFuncionalRepository;
        this.versionReglaNegocioRepository = versionReglaNegocioRepository;
    }

    @Transactional
    public VersionDocumento versionarDocumento(Long documentoId, String numeroVersion, String descripcionCambios,
                                               Long generadoPorId, List<Long> rfIds, List<Long> rnfIds,
                                               List<Long> reglaIds) {
        validarId(documentoId, "documentoId");
        validarTexto(numeroVersion, "numeroVersion");

        DocumentoDfr documento = documentoDfrRepository.findById(documentoId)
                .orElseThrow(() -> new ResourceNotFoundException("Documento DFR no encontrado"));
        Usuario generadoPor = null;
        if (generadoPorId != null) {
            generadoPor = usuarioRepository.findById(generadoPorId)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        }

        VersionDocumento version = new VersionDocumento();
        version.setDocumento(documento);
        version.setNumeroVersion(numeroVersion.trim());
        version.setDescripcionCambios(limpiar(descripcionCambios));
        version.setGeneradoPor(generadoPor);

        VersionDocumento guardada = versionDocumentoRepository.save(version);

        vincularRequerimientosFuncionales(guardada, rfIds);
        vincularRequerimientosNoFuncionales(guardada, rnfIds);
        vincularReglasNegocio(guardada, reglaIds);

        return guardada;
    }

    @Transactional(readOnly = true)
    public VersionDocumento obtenerVersion(Long versionId) {
        validarId(versionId, "versionId");
        return versionDocumentoRepository.findById(versionId)
                .orElseThrow(() -> new ResourceNotFoundException("Version no encontrada"));
    }

    @Transactional(readOnly = true)
    public VersionDocumento obtenerVersionConDocumento(Long versionId) {
        validarId(versionId, "versionId");
        return versionDocumentoRepository.findByIdWithDocumento(versionId)
                .orElseThrow(() -> new ResourceNotFoundException("Version no encontrada"));
    }

    @Transactional(readOnly = true)
    public VersionDocumento obtenerUltimaVersionDocumento(Long documentoId) {
        validarId(documentoId, "documentoId");
        return versionDocumentoRepository.findTopByDocumentoIdOrderByIdDesc(documentoId)
                .orElseThrow(() -> new ResourceNotFoundException("No hay versiones para el documento"));
    }

    @Transactional(readOnly = true)
    public List<VersionDocumento> listarPorDocumento(Long documentoId) {
        validarId(documentoId, "documentoId");
        return versionDocumentoRepository.findByDocumentoId(documentoId);
    }

    @Transactional(readOnly = true)
    public List<VersionDocumento> listarVersionesPorDocumento(Long documentoId) {
        validarId(documentoId, "documentoId");
        return versionDocumentoRepository.findByDocumentoIdOrderByIdDesc(documentoId);
    }

    @Transactional
    public VersionDocumento crearVersionAutomatica(Long documentoId, Long generadoPorId, String descripcionCambios) {
        validarId(documentoId, "documentoId");

        DocumentoDfr documento = documentoDfrRepository.findById(documentoId)
                .orElseThrow(() -> new ResourceNotFoundException("Documento DFR no encontrado"));
        Usuario generadoPor = null;
        if (generadoPorId != null) {
            generadoPor = usuarioRepository.findById(generadoPorId)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        }

        VersionDocumento ultima = versionDocumentoRepository.findTopByDocumentoIdOrderByIdDesc(documentoId).orElse(null);
        VersionDocumento version = new VersionDocumento();
        version.setDocumento(documento);
        version.setNumeroVersion(generarNumeroVersion(ultima == null ? null : ultima.getNumeroVersion()));
        version.setDescripcionCambios(limpiar(descripcionCambios));
        version.setGeneradoPor(generadoPor);

        return versionDocumentoRepository.save(version);
    }

    private String generarNumeroVersion(String anterior) {
        if (anterior == null || anterior.isBlank()) {
            return "1.0";
        }
        String[] partes = anterior.trim().split("\\.");
        if (partes.length < 2) {
            return anterior.trim() + ".1";
        }
        try {
            int mayor = Integer.parseInt(partes[0]);
            int menor = Integer.parseInt(partes[1]);
            return mayor + "." + (menor + 1);
        } catch (NumberFormatException ex) {
            return anterior.trim() + ".1";
        }
    }

    private void vincularRequerimientosFuncionales(VersionDocumento version, List<Long> rfIds) {
        if (rfIds == null || rfIds.isEmpty()) {
            return;
        }
        List<VersionRequerimientoFuncional> relaciones = new ArrayList<>();
        for (Long id : rfIds) {
            if (id == null || id <= 0) {
                throw new BusinessValidationException("Id de requerimiento funcional invalido");
            }
            RequerimientoFuncional rf = requerimientoFuncionalRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Requerimiento funcional no encontrado"));
            VersionRequerimientoFuncional relacion = new VersionRequerimientoFuncional();
            relacion.setVersion(version);
            relacion.setRequerimiento(rf);
            relaciones.add(relacion);
        }
        versionRequerimientoFuncionalRepository.saveAll(relaciones);
    }

    private void vincularRequerimientosNoFuncionales(VersionDocumento version, List<Long> rnfIds) {
        if (rnfIds == null || rnfIds.isEmpty()) {
            return;
        }
        List<VersionRequerimientoNoFuncional> relaciones = new ArrayList<>();
        for (Long id : rnfIds) {
            if (id == null || id <= 0) {
                throw new BusinessValidationException("Id de requerimiento no funcional invalido");
            }
            RequerimientoNoFuncional rnf = requerimientoNoFuncionalRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Requerimiento no funcional no encontrado"));
            VersionRequerimientoNoFuncional relacion = new VersionRequerimientoNoFuncional();
            relacion.setVersion(version);
            relacion.setRequerimiento(rnf);
            relaciones.add(relacion);
        }
        versionRequerimientoNoFuncionalRepository.saveAll(relaciones);
    }

    private void vincularReglasNegocio(VersionDocumento version, List<Long> reglaIds) {
        if (reglaIds == null || reglaIds.isEmpty()) {
            return;
        }
        List<VersionReglaNegocio> relaciones = new ArrayList<>();
        for (Long id : reglaIds) {
            if (id == null || id <= 0) {
                throw new BusinessValidationException("Id de regla de negocio invalido");
            }
            ReglaNegocio regla = reglaNegocioRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Regla de negocio no encontrada"));
            VersionReglaNegocio relacion = new VersionReglaNegocio();
            relacion.setVersion(version);
            relacion.setRegla(regla);
            relaciones.add(relacion);
        }
        versionReglaNegocioRepository.saveAll(relaciones);
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

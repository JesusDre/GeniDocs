package com.jesus.genidocs.service;

import com.jesus.genidocs.entity.CriterioAceptacionDwt;
import com.jesus.genidocs.entity.RequerimientoFuncional;
import com.jesus.genidocs.entity.enums.EstadoCriterioAceptacion;
import com.jesus.genidocs.exception.BusinessValidationException;
import com.jesus.genidocs.exception.ResourceNotFoundException;
import com.jesus.genidocs.repository.CriterioAceptacionDwtRepository;
import com.jesus.genidocs.repository.RequerimientoFuncionalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CriterioAceptacionDwtService {

    private final CriterioAceptacionDwtRepository criterioAceptacionDwtRepository;
    private final RequerimientoFuncionalRepository requerimientoFuncionalRepository;

    public CriterioAceptacionDwtService(CriterioAceptacionDwtRepository criterioAceptacionDwtRepository,
                                        RequerimientoFuncionalRepository requerimientoFuncionalRepository) {
        this.criterioAceptacionDwtRepository = criterioAceptacionDwtRepository;
        this.requerimientoFuncionalRepository = requerimientoFuncionalRepository;
    }

    @Transactional
    public CriterioAceptacionDwt crear(Long requerimientoFuncionalId, String dado, String cuando, String entonces,
                                       EstadoCriterioAceptacion estado) {
        validarId(requerimientoFuncionalId, "requerimientoFuncionalId");
        if (estado == null) {
            throw new BusinessValidationException("El estado es obligatorio");
        }

        RequerimientoFuncional requerimiento = requerimientoFuncionalRepository.findById(requerimientoFuncionalId)
                .orElseThrow(() -> new ResourceNotFoundException("Requerimiento funcional no encontrado"));

        CriterioAceptacionDwt criterio = new CriterioAceptacionDwt();
        criterio.setRequerimientoFuncional(requerimiento);
        criterio.setDado(limpiar(dado));
        criterio.setCuando(limpiar(cuando));
        criterio.setEntonces(limpiar(entonces));
        criterio.setEstado(estado);

        return criterioAceptacionDwtRepository.save(criterio);
    }

    @Transactional(readOnly = true)
    public List<CriterioAceptacionDwt> listarPorRequerimiento(Long requerimientoFuncionalId) {
        validarId(requerimientoFuncionalId, "requerimientoFuncionalId");
        return criterioAceptacionDwtRepository.findByRequerimientoFuncionalId(requerimientoFuncionalId);
    }

    @Transactional
    public CriterioAceptacionDwt guardarPorDocumento(Long documentoId, Long requerimientoFuncionalId, String dado,
                                                     String cuando, String entonces, EstadoCriterioAceptacion estado,
                                                     Long usuarioId) {
        validarId(documentoId, "documentoId");
        validarId(requerimientoFuncionalId, "requerimientoFuncionalId");
        if (estado == null) {
            throw new BusinessValidationException("El estado es obligatorio");
        }

        RequerimientoFuncional requerimiento = requerimientoFuncionalRepository.findById(requerimientoFuncionalId)
                .orElseThrow(() -> new ResourceNotFoundException("Requerimiento funcional no encontrado"));

        CriterioAceptacionDwt criterio = new CriterioAceptacionDwt();
        criterio.setRequerimientoFuncional(requerimiento);
        criterio.setDado(limpiar(dado));
        criterio.setCuando(limpiar(cuando));
        criterio.setEntonces(limpiar(entonces));
        criterio.setEstado(estado);

        return criterioAceptacionDwtRepository.save(criterio);
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

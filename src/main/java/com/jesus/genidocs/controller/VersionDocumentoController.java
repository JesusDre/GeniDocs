package com.jesus.genidocs.controller;

import com.jesus.genidocs.dto.request.VersionDocumentoCreateRequest;
import com.jesus.genidocs.dto.response.VersionDocumentoResponse;
import com.jesus.genidocs.entity.VersionDocumento;
import com.jesus.genidocs.mapper.VersionDocumentoMapper;
import com.jesus.genidocs.service.VersionDocumentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/versiones")
public class VersionDocumentoController {

    private final VersionDocumentoService versionDocumentoService;
    private final VersionDocumentoMapper versionDocumentoMapper;

    public VersionDocumentoController(VersionDocumentoService versionDocumentoService,
                                      VersionDocumentoMapper versionDocumentoMapper) {
        this.versionDocumentoService = versionDocumentoService;
        this.versionDocumentoMapper = versionDocumentoMapper;
    }

    @PostMapping
    public ResponseEntity<VersionDocumentoResponse> crear(@Valid @RequestBody VersionDocumentoCreateRequest request) {
        VersionDocumento version = versionDocumentoService.versionarDocumento(
                request.getDocumentoId(),
                request.getNumeroVersion(),
                request.getDescripcionCambios(),
                request.getGeneradoPorId(),
                request.getRfIds(),
                request.getRnfIds(),
                request.getReglaIds()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(versionDocumentoMapper.toResponse(version));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VersionDocumentoResponse> obtener(@PathVariable Long id) {
        VersionDocumento version = versionDocumentoService.obtenerVersion(id);
        return ResponseEntity.ok(versionDocumentoMapper.toResponse(version));
    }

    @GetMapping
    public ResponseEntity<List<VersionDocumentoResponse>> listarPorDocumento(@RequestParam Long documentoId) {
        List<VersionDocumento> versiones = versionDocumentoService.listarVersionesPorDocumento(documentoId);
        List<VersionDocumentoResponse> response = versiones.stream()
                .map(versionDocumentoMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
}

package com.jesus.genidocs.controller;

import com.jesus.genidocs.dto.request.DescripcionGeneralCreateRequest;
import com.jesus.genidocs.dto.response.DescripcionGeneralResponse;
import com.jesus.genidocs.mapper.DescripcionGeneralMapper;
import com.jesus.genidocs.service.DescripcionGeneralService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/descripciones-generales")
public class DescripcionGeneralController {

    private final DescripcionGeneralService descripcionGeneralService;
    private final DescripcionGeneralMapper descripcionGeneralMapper;

    public DescripcionGeneralController(DescripcionGeneralService descripcionGeneralService,
                                        DescripcionGeneralMapper descripcionGeneralMapper) {
        this.descripcionGeneralService = descripcionGeneralService;
        this.descripcionGeneralMapper = descripcionGeneralMapper;
    }

    @PostMapping
    public ResponseEntity<DescripcionGeneralResponse> crear(@Valid @RequestBody DescripcionGeneralCreateRequest request,
                                                           @RequestParam Long usuarioId) {
        var descripcion = descripcionGeneralService.guardarPorDocumento(
                request.getDocumentoId(),
                request.getObjetivo(),
                request.getProblema(),
                request.getUsuariosRol(),
                usuarioId
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(descripcionGeneralMapper.toResponse(descripcion));
    }

    @GetMapping
    public ResponseEntity<List<DescripcionGeneralResponse>> listarPorVersion(@RequestParam Long versionId) {
        List<DescripcionGeneralResponse> response = descripcionGeneralService.listarPorVersion(versionId).stream()
                .map(descripcionGeneralMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/documentos/{documentoId}")
    public ResponseEntity<DescripcionGeneralResponse> guardarPorDocumento(@PathVariable Long documentoId,
                                                                          @RequestParam Long usuarioId,
                                                                          @Valid @RequestBody DescripcionGeneralCreateRequest request) {
        var descripcion = descripcionGeneralService.guardarPorDocumento(
                documentoId,
                request.getObjetivo(),
                request.getProblema(),
                request.getUsuariosRol(),
                usuarioId
        );
        return ResponseEntity.ok(descripcionGeneralMapper.toResponse(descripcion));
    }
}

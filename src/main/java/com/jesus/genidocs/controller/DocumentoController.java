package com.jesus.genidocs.controller;

import com.jesus.genidocs.dto.request.DocumentoCreateRequest;
import com.jesus.genidocs.dto.response.DocumentoResponse;
import com.jesus.genidocs.entity.DocumentoDfr;
import com.jesus.genidocs.mapper.DocumentoMapper;
import com.jesus.genidocs.service.DocumentoDfrService;
import com.jesus.genidocs.service.PdfService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/documentos")
public class DocumentoController {

    private final DocumentoDfrService documentoDfrService;
    private final DocumentoMapper documentoMapper;
    private final PdfService pdfService;

    public DocumentoController(DocumentoDfrService documentoDfrService, DocumentoMapper documentoMapper,
                               PdfService pdfService) {
        this.documentoDfrService = documentoDfrService;
        this.documentoMapper = documentoMapper;
        this.pdfService = pdfService;
    }

    @PostMapping
    public ResponseEntity<DocumentoResponse> crearDocumento(@Valid @RequestBody DocumentoCreateRequest request) {
        DocumentoDfr documento = documentoDfrService.crearDocumento(
                request.getProyectoId(),
                request.getTitulo(),
                request.getDescripcion()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(documentoMapper.toResponse(documento));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentoResponse> obtenerDocumento(@PathVariable Long id) {
        DocumentoDfr documento = documentoDfrService.obtenerDocumento(id);
        return ResponseEntity.ok(documentoMapper.toResponse(documento));
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> obtenerDocumentoPdf(@PathVariable Long id) {
        DocumentoDfr documento = documentoDfrService.obtenerDocumento(id);
        byte[] pdf = pdfService.generarPdfDocumento(documento);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "documento-" + documento.getId() + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdf);
    }
}

package com.jesus.genidocs.controller;

import com.jesus.genidocs.entity.VersionDocumento;
import com.jesus.genidocs.service.PdfService;
import com.jesus.genidocs.service.VersionDocumentoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/versiones")
public class VersionPdfController {

    private final VersionDocumentoService versionDocumentoService;
    private final PdfService pdfService;

    public VersionPdfController(VersionDocumentoService versionDocumentoService, PdfService pdfService) {
        this.versionDocumentoService = versionDocumentoService;
        this.pdfService = pdfService;
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> generarPdf(@PathVariable Long id) {
        VersionDocumento version = versionDocumentoService.obtenerVersionConDocumento(id);
        byte[] pdf = pdfService.generarPdfVersion(version);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "dfr-version-" + version.getId() + ".pdf");

        return ResponseEntity.ok().headers(headers).body(pdf);
    }
}

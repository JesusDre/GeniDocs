package com.jesus.genidocs.service;

import com.jesus.genidocs.entity.CriterioAceptacionDwt;
import com.jesus.genidocs.entity.DescripcionGeneral;
import com.jesus.genidocs.entity.DocumentoDfr;
import com.jesus.genidocs.entity.IntroduccionDfr;
import com.jesus.genidocs.entity.ModeloDatos;
import com.jesus.genidocs.entity.ReglaNegocio;
import com.jesus.genidocs.entity.RequerimientoFuncional;
import com.jesus.genidocs.entity.RequerimientoNoFuncional;
import com.jesus.genidocs.entity.VersionDocumento;
import com.jesus.genidocs.exception.PdfGenerationException;
import com.jesus.genidocs.repository.DescripcionGeneralRepository;
import com.jesus.genidocs.repository.CriterioAceptacionDwtRepository;
import com.jesus.genidocs.repository.IntroduccionDfrRepository;
import com.jesus.genidocs.repository.ModeloDatosRepository;
import com.jesus.genidocs.repository.ReglaNegocioRepository;
import com.jesus.genidocs.repository.RequerimientoFuncionalRepository;
import com.jesus.genidocs.repository.RequerimientoNoFuncionalRepository;
import com.jesus.genidocs.repository.VersionDocumentoRepository;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PdfService {

    private final RequerimientoFuncionalRepository requerimientoFuncionalRepository;
    private final RequerimientoNoFuncionalRepository requerimientoNoFuncionalRepository;
    private final ReglaNegocioRepository reglaNegocioRepository;
    private final CriterioAceptacionDwtRepository criterioAceptacionDwtRepository;
    private final VersionDocumentoRepository versionDocumentoRepository;
    private final IntroduccionDfrRepository introduccionDfrRepository;
    private final DescripcionGeneralRepository descripcionGeneralRepository;
    private final ModeloDatosRepository modeloDatosRepository;

    public PdfService(RequerimientoFuncionalRepository requerimientoFuncionalRepository,
                      RequerimientoNoFuncionalRepository requerimientoNoFuncionalRepository,
                      ReglaNegocioRepository reglaNegocioRepository,
                      CriterioAceptacionDwtRepository criterioAceptacionDwtRepository,
                      VersionDocumentoRepository versionDocumentoRepository,
                      IntroduccionDfrRepository introduccionDfrRepository,
                      DescripcionGeneralRepository descripcionGeneralRepository,
                      ModeloDatosRepository modeloDatosRepository) {
        this.requerimientoFuncionalRepository = requerimientoFuncionalRepository;
        this.requerimientoNoFuncionalRepository = requerimientoNoFuncionalRepository;
        this.reglaNegocioRepository = reglaNegocioRepository;
        this.criterioAceptacionDwtRepository = criterioAceptacionDwtRepository;
        this.versionDocumentoRepository = versionDocumentoRepository;
        this.introduccionDfrRepository = introduccionDfrRepository;
        this.descripcionGeneralRepository = descripcionGeneralRepository;
        this.modeloDatosRepository = modeloDatosRepository;
    }

    @Transactional(readOnly = true)
    public byte[] generarPdfDocumento(DocumentoDfr documento) {
        VersionDocumento version = obtenerUltimaVersion(documento);
        String html = construirHtml(documento, version, false);
        return renderizarPdf(html);
    }

    @Transactional(readOnly = true)
    public byte[] generarPdfVersion(VersionDocumento version) {
        String html = construirHtml(version.getDocumento(), version, true);
        return renderizarPdf(html);
    }

    private byte[] renderizarPdf(String html) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, null);
            builder.toStream(outputStream);
            builder.run();
            return outputStream.toByteArray();
        } catch (Exception ex) {
            throw new PdfGenerationException("No se pudo generar el PDF", ex);
        }
    }

    private String construirHtml(DocumentoDfr documento, VersionDocumento version, boolean usarContenidoVersionado) {
        String titulo = documento.getTitulo() == null ? "Documento DFR" : documento.getTitulo();
        String descripcion = documento.getDescripcion() == null ? "" : documento.getDescripcion();
        String estado = documento.getEstado() == null ? "" : documento.getEstado().name();
        String creado = documento.getCreatedAt() == null ? "" : documento.getCreatedAt().toString();
        String actualizado = documento.getUpdatedAt() == null ? "" : documento.getUpdatedAt().toString();
        String numeroVersion = version == null ? "" : nvl(version.getNumeroVersion());
        String autor = version != null && version.getGeneradoPor() != null ? nvl(version.getGeneradoPor().getNombre()) : "";
        String fechaVersion = version != null && version.getFechaGeneracion() != null ? version.getFechaGeneracion().toString() : "";

        String proyectoNombre = "";
        String proyectoDescripcion = "";
        String clienteNombre = "";
        String clienteEmpresa = "";
        String responsableNombre = "";
        String responsableEmail = "";
        if (documento.getProyecto() != null) {
            proyectoNombre = nvl(documento.getProyecto().getNombre());
            proyectoDescripcion = nvl(documento.getProyecto().getDescripcion());
            if (documento.getProyecto().getCliente() != null) {
                clienteNombre = nvl(documento.getProyecto().getCliente().getNombre());
                clienteEmpresa = nvl(documento.getProyecto().getCliente().getEmpresa());
            }
            if (documento.getProyecto().getResponsable() != null) {
                responsableNombre = nvl(documento.getProyecto().getResponsable().getNombre());
                responsableEmail = nvl(documento.getProyecto().getResponsable().getEmail());
            }
        }

        List<RequerimientoFuncional> rfList = obtenerRequerimientosFuncionales(documento);
        List<RequerimientoNoFuncional> rnfList = obtenerRequerimientosNoFuncionales(documento);
        List<ReglaNegocio> reglasList = obtenerReglasNegocio(documento);
        Map<Long, List<CriterioAceptacionDwt>> criteriosPorRf = obtenerCriteriosPorRf(rfList);
            IntroduccionDfr introduccion = obtenerIntroduccion(documento, version);
            DescripcionGeneral descripcionGeneral = obtenerDescripcionGeneral(documento, version);
            ModeloDatos modeloDatos = obtenerModeloDatos(documento, version);

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>")
                .append("<html lang='es'>")
                .append("<head>")
                .append("<meta charset='UTF-8' />")
                .append("<style>")
            .append("body{font-family:Arial,Helvetica,sans-serif;color:#0f172a;padding:36px;font-size:12px;}")
            .append(".cover{min-height:90vh;display:block;text-align:center;padding-top:180px;page-break-after:always;}")
            .append(".cover-kicker{font-size:12px;letter-spacing:2px;text-transform:uppercase;color:#64748b;margin-bottom:14px;}")
            .append(".cover-title{font-size:30px;font-weight:bold;margin:0 0 10px 0;}")
            .append(".cover-client{font-size:16px;color:#334155;margin:0;}")
            .append(".cover-line{width:180px;height:3px;background:#0f172a;margin:18px auto;border-radius:2px;}")
                .append(".header{display:flex;justify-content:space-between;align-items:flex-start;border-bottom:2px solid #0f172a;padding-bottom:12px;}")
                .append(".title{font-size:22px;font-weight:bold;margin:0;}")
                .append(".subtitle{font-size:12px;color:#475569;margin-top:4px;}")
                .append(".badge{display:inline-block;background:#0f172a;color:#fff;padding:4px 10px;border-radius:12px;font-size:11px;}")
                .append(".section{margin-top:18px;}")
                .append(".section-title{font-size:13px;font-weight:bold;margin:0 0 8px 0;color:#0f172a;}")
                .append(".grid{width:100%;border-collapse:collapse;}")
                .append(".grid th,.grid td{padding:6px 8px;vertical-align:top;border:1px solid #e2e8f0;}")
                .append(".grid th{background:#f1f5f9;text-align:left;font-weight:bold;}")
                .append(".label{width:24%;font-weight:bold;background:#f8fafc;}")
                .append(".box{border:1px solid #e2e8f0;padding:10px;border-radius:4px;background:#ffffff;}")
                .append(".muted{color:#64748b;}")
                .append(".pill{display:inline-block;background:#e2e8f0;color:#0f172a;padding:2px 8px;border-radius:10px;font-size:10px;}")
                .append("ul{margin:6px 0 0 16px;padding:0;}")
                .append("li{margin:2px 0;}")
                .append("</style>")
                .append("</head>")
                .append("<body>")
                .append("<div class='cover'>")
                .append("<div class='cover-kicker'>Documento DFR</div>")
                .append("<h1 class='cover-title'>").append(escaparHtml(proyectoNombre)).append("</h1>")
                .append("<div class='cover-line'></div>")
                .append("<p class='cover-client'>Cliente: ").append(escaparHtml(clienteNombre)).append("</p>")
                .append("<p class='cover-client'>").append(escaparHtml(clienteEmpresa)).append("</p>")
                .append("</div>")
                .append("<div class='header'>")
                .append("<div>")
                .append("<div class='title'>").append(escaparHtml(titulo)).append("</div>")
                .append("<div class='subtitle'>Documento de Requerimientos Funcionales y No Funcionales</div>")
                .append("</div>")
                .append("<div class='badge'>").append(escaparHtml(estado)).append("</div>")
                .append("</div>")
                .append("<div class='section'>")
                .append("<div class='section-title'>Proyecto</div>")
                .append("<table class='grid'>")
                .append("<tr><td class='label'>Nombre</td><td>").append(escaparHtml(proyectoNombre)).append("</td></tr>")
                .append("<tr><td class='label'>Cliente</td><td>").append(escaparHtml(clienteNombre)).append("</td></tr>")
                .append("<tr><td class='label'>Empresa</td><td>").append(escaparHtml(clienteEmpresa)).append("</td></tr>")
                .append("<tr><td class='label'>Responsable</td><td>").append(escaparHtml(responsableNombre)).append("</td></tr>")
                .append("<tr><td class='label'>Email responsable</td><td>").append(escaparHtml(responsableEmail)).append("</td></tr>")
                .append("</table>")
                .append("</div>")
                .append("<div class='section'>")
                .append("<div class='section-title'>Descripcion del proyecto</div>")
                .append("<div class='box'>").append(escaparHtml(proyectoDescripcion)).append("</div>")
                .append("</div>")
                .append("<div class='section'>")
                .append("<div class='section-title'>Descripcion del documento</div>")
                .append("<div class='box'>").append(escaparHtml(descripcion)).append("</div>")
                .append("</div>")
                .append(renderIntroduccion(introduccion))
                .append(renderDescripcionGeneral(descripcionGeneral))
                .append(renderReglasNegocio(reglasList))
                .append(renderRequerimientosFuncionales(rfList, criteriosPorRf))
                .append(renderCriteriosAceptacion(rfList, criteriosPorRf))
                .append(renderRequerimientosNoFuncionales(rnfList))
                .append(renderModeloDatos(modeloDatos))
                .append("<div class='section'>")
                .append("<div class='section-title'>Resumen del documento</div>")
                .append("<table class='grid'>")
                .append("<tr><td class='label'>ID documento</td><td>").append(documento.getId()).append("</td></tr>")
                .append("<tr><td class='label'>Version</td><td>").append(escaparHtml(numeroVersion)).append("</td></tr>")
                .append("<tr><td class='label'>Autor</td><td>").append(escaparHtml(autor)).append("</td></tr>")
                .append("<tr><td class='label'>Fecha version</td><td>").append(escaparHtml(fechaVersion)).append("</td></tr>")
                .append("<tr><td class='label'>Creado</td><td>").append(escaparHtml(creado)).append("</td></tr>")
                .append("<tr><td class='label'>Actualizado</td><td>").append(escaparHtml(actualizado)).append("</td></tr>")
                .append("</table>")
                .append("</div>")
                .append("</body>")
                .append("</html>");

        return html.toString();
    }

    private VersionDocumento obtenerUltimaVersion(DocumentoDfr documento) {
        if (documento == null || documento.getId() == null) {
            return null;
        }
        return versionDocumentoRepository.findTopByDocumentoIdOrderByIdDesc(documento.getId()).orElse(null);
    }

    private List<RequerimientoFuncional> obtenerRequerimientosFuncionales(DocumentoDfr documento) {
        if (documento.getProyecto() == null || documento.getProyecto().getId() == null) {
            return Collections.emptyList();
        }
        return requerimientoFuncionalRepository.findByProyectoId(documento.getProyecto().getId());
    }

    private List<RequerimientoNoFuncional> obtenerRequerimientosNoFuncionales(DocumentoDfr documento) {
        if (documento.getProyecto() == null || documento.getProyecto().getId() == null) {
            return Collections.emptyList();
        }
        return requerimientoNoFuncionalRepository.findByProyectoId(documento.getProyecto().getId());
    }

    private List<ReglaNegocio> obtenerReglasNegocio(DocumentoDfr documento) {
        if (documento.getProyecto() == null || documento.getProyecto().getId() == null) {
            return Collections.emptyList();
        }
        return reglaNegocioRepository.findByProyectoId(documento.getProyecto().getId());
    }

    private IntroduccionDfr obtenerIntroduccion(DocumentoDfr documento, VersionDocumento version) {
        if (version != null && version.getId() != null) {
            IntroduccionDfr porVersion = introduccionDfrRepository.findByVersionId(version.getId()).stream().findFirst().orElse(null);
            if (porVersion != null) {
                return porVersion;
            }
        }
        if (documento == null || documento.getId() == null) {
            return null;
        }
        return introduccionDfrRepository.findTopByVersionDocumentoIdOrderByIdDesc(documento.getId()).orElse(null);
    }

    private DescripcionGeneral obtenerDescripcionGeneral(DocumentoDfr documento, VersionDocumento version) {
        if (version != null && version.getId() != null) {
            DescripcionGeneral porVersion = descripcionGeneralRepository.findByVersionId(version.getId()).stream().findFirst().orElse(null);
            if (porVersion != null) {
                return porVersion;
            }
        }
        if (documento == null || documento.getId() == null) {
            return null;
        }
        return descripcionGeneralRepository.findTopByVersionDocumentoIdOrderByIdDesc(documento.getId()).orElse(null);
    }

    private ModeloDatos obtenerModeloDatos(DocumentoDfr documento, VersionDocumento version) {
        if (version != null && version.getId() != null) {
            ModeloDatos porVersion = modeloDatosRepository.findByVersionId(version.getId()).stream().findFirst().orElse(null);
            if (porVersion != null) {
                return porVersion;
            }
        }
        if (documento == null || documento.getId() == null) {
            return null;
        }
        return modeloDatosRepository.findTopByVersionDocumentoIdOrderByIdDesc(documento.getId()).orElse(null);
    }

    private Map<Long, List<CriterioAceptacionDwt>> obtenerCriteriosPorRf(List<RequerimientoFuncional> rfList) {
        List<Long> ids = rfList.stream()
                .map(RequerimientoFuncional::getId)
                .filter(Objects::nonNull)
                .toList();
        if (ids.isEmpty()) {
            return Collections.emptyMap();
        }
        List<CriterioAceptacionDwt> criterios = criterioAceptacionDwtRepository.findByRequerimientoFuncionalIdIn(ids);
        return criterios.stream()
                .filter(c -> c.getRequerimientoFuncional() != null && c.getRequerimientoFuncional().getId() != null)
                .collect(Collectors.groupingBy(c -> c.getRequerimientoFuncional().getId()));
    }

    private String renderIntroduccion(IntroduccionDfr introduccion) {
        StringBuilder html = new StringBuilder();
        html.append("<div class='section'>")
                .append("<div class='section-title'>Introduccion</div>");
        if (introduccion == null) {
            html.append("<div class='muted'>No hay introduccion registrada.</div></div>");
            return html.toString();
        }

        html.append("<table class='grid'>")
                .append("<tr><td class='label'>Proposito</td><td>").append(escaparHtml(nvl(introduccion.getProposito()))).append("</td></tr>")
                .append("<tr><td class='label'>Alcance incluye</td><td>").append(escaparHtml(nvl(introduccion.getAlcanceIncluye()))).append("</td></tr>")
                .append("<tr><td class='label'>Alcance excluye</td><td>").append(escaparHtml(nvl(introduccion.getAlcanceExcluye()))).append("</td></tr>")
                .append("<tr><td class='label'>Definiciones</td><td>").append(escaparHtml(nvl(introduccion.getDefiniciones()))).append("</td></tr>")
                .append("</table></div>");
        return html.toString();
    }

    private String renderDescripcionGeneral(DescripcionGeneral descripcionGeneral) {
        StringBuilder html = new StringBuilder();
        html.append("<div class='section'>")
                .append("<div class='section-title'>Descripcion general</div>");
        if (descripcionGeneral == null) {
            html.append("<div class='muted'>No hay descripcion general registrada.</div></div>");
            return html.toString();
        }

        html.append("<table class='grid'>")
                .append("<tr><td class='label'>Objetivo</td><td>").append(escaparHtml(nvl(descripcionGeneral.getObjetivo()))).append("</td></tr>")
                .append("<tr><td class='label'>Problema</td><td>").append(escaparHtml(nvl(descripcionGeneral.getProblema()))).append("</td></tr>")
                .append("<tr><td class='label'>Usuarios / Rol</td><td>").append(escaparHtml(nvl(descripcionGeneral.getUsuariosRol()))).append("</td></tr>")
                .append("</table></div>");
        return html.toString();
    }

    private String renderModeloDatos(ModeloDatos modeloDatos) {
        StringBuilder html = new StringBuilder();
        html.append("<div class='section'>")
                .append("<div class='section-title'>Modelo de datos</div>");
        if (modeloDatos == null) {
            html.append("<div class='muted'>No hay modelo de datos registrado.</div></div>");
            return html.toString();
        }

        html.append("<table class='grid'>")
                .append("<tr><td class='label'>Entidades</td><td>").append(escaparHtml(nvl(modeloDatos.getEntidades()))).append("</td></tr>")
                .append("<tr><td class='label'>Relaciones</td><td>").append(escaparHtml(nvl(modeloDatos.getRelaciones()))).append("</td></tr>")
                .append("<tr><td class='label'>Diagrama</td><td>").append(escaparHtml(nvl(modeloDatos.getDiagrama()))).append("</td></tr>")
                .append("</table></div>");
        return html.toString();
    }

    private String renderCriteriosAceptacion(List<RequerimientoFuncional> rfList,
                                             Map<Long, List<CriterioAceptacionDwt>> criteriosPorRf) {
        StringBuilder html = new StringBuilder();
        html.append("<div class='section'>")
                .append("<div class='section-title'>Criterios de aceptacion</div>");
        if (rfList.isEmpty()) {
            html.append("<div class='muted'>No hay requerimientos funcionales para mostrar criterios.</div></div>");
            return html.toString();
        }

        boolean hayCriterios = criteriosPorRf.values().stream().anyMatch(list -> list != null && !list.isEmpty());
        if (!hayCriterios) {
            html.append("<div class='muted'>No hay criterios de aceptacion registrados.</div></div>");
            return html.toString();
        }

        html.append("<table class='grid'>")
                .append("<tr><th>RF</th><th>Dado</th><th>Cuando</th><th>Entonces</th><th>Estado</th></tr>");
        for (RequerimientoFuncional rf : rfList) {
            List<CriterioAceptacionDwt> criterios = criteriosPorRf.getOrDefault(rf.getId(), Collections.emptyList());
            for (CriterioAceptacionDwt criterio : criterios) {
                String estadoCriterio = criterio.getEstado() == null ? "" : criterio.getEstado().name();
                html.append("<tr>")
                        .append("<td>").append(escaparHtml(nvl(rf.getCodigo()))).append("</td>")
                        .append("<td>").append(escaparHtml(nvl(criterio.getDado()))).append("</td>")
                        .append("<td>").append(escaparHtml(nvl(criterio.getCuando()))).append("</td>")
                        .append("<td>").append(escaparHtml(nvl(criterio.getEntonces()))).append("</td>")
                        .append("<td><span class='pill'>").append(escaparHtml(estadoCriterio)).append("</span></td>")
                        .append("</tr>");
            }
        }
        html.append("</table></div>");
        return html.toString();
    }

    private String renderRequerimientosFuncionales(List<RequerimientoFuncional> rfList,
                                                   Map<Long, List<CriterioAceptacionDwt>> criteriosPorRf) {
        StringBuilder html = new StringBuilder();
        html.append("<div class='section'>")
                .append("<div class='section-title'>Requerimientos funcionales</div>");
        if (rfList.isEmpty()) {
            html.append("<div class='muted'>No hay requerimientos funcionales registrados.</div></div>");
            return html.toString();
        }

        html.append("<table class='grid'>")
                .append("<tr><th>Codigo</th><th>Titulo</th><th>Prioridad</th><th>Estado</th><th>Descripcion y criterios</th></tr>");

        for (RequerimientoFuncional rf : rfList) {
            String codigo = nvl(rf.getCodigo());
            String titulo = nvl(rf.getTitulo());
            String prioridad = rf.getPrioridad() == null ? "" : rf.getPrioridad().name();
            String estado = rf.getEstado() == null ? "" : rf.getEstado().name();
            String descripcion = nvl(rf.getDescripcion());

            html.append("<tr>")
                    .append("<td>").append(escaparHtml(codigo)).append("</td>")
                    .append("<td>").append(escaparHtml(titulo)).append("</td>")
                    .append("<td><span class='pill'>").append(escaparHtml(prioridad)).append("</span></td>")
                    .append("<td>").append(escaparHtml(estado)).append("</td>")
                    .append("<td>")
                    .append("<div>").append(escaparHtml(descripcion)).append("</div>");

            List<CriterioAceptacionDwt> criterios = criteriosPorRf.getOrDefault(rf.getId(), Collections.emptyList());
            if (criterios.isEmpty()) {
                html.append("<div class='muted'>Sin criterios de aceptacion.</div>");
            } else {
                html.append("<ul>");
                for (CriterioAceptacionDwt criterio : criterios) {
                    String estadoCriterio = criterio.getEstado() == null ? "" : criterio.getEstado().name();
                    html.append("<li>")
                            .append("<div><strong>Dado:</strong> ").append(escaparHtml(nvl(criterio.getDado()))).append("</div>")
                            .append("<div><strong>Cuando:</strong> ").append(escaparHtml(nvl(criterio.getCuando()))).append("</div>")
                            .append("<div><strong>Entonces:</strong> ").append(escaparHtml(nvl(criterio.getEntonces()))).append("</div>")
                            .append(" <span class='pill'>").append(escaparHtml(estadoCriterio)).append("</span>")
                            .append("</li>");
                }
                html.append("</ul>");
            }
            html.append("</td>")
                    .append("</tr>");
        }

        html.append("</table></div>");
        return html.toString();
    }

    private String renderRequerimientosNoFuncionales(List<RequerimientoNoFuncional> rnfList) {
        StringBuilder html = new StringBuilder();
        html.append("<div class='section'>")
                .append("<div class='section-title'>Requerimientos no funcionales</div>");
        if (rnfList.isEmpty()) {
            html.append("<div class='muted'>No hay requerimientos no funcionales registrados.</div></div>");
            return html.toString();
        }

        html.append("<table class='grid'>")
                .append("<tr><th>Tipo</th><th>Prioridad</th><th>Estado</th><th>Descripcion</th></tr>");
        for (RequerimientoNoFuncional rnf : rnfList) {
            String tipo = nvl(rnf.getTipo());
            String prioridad = rnf.getPrioridad() == null ? "" : rnf.getPrioridad().name();
            String estado = rnf.getEstado() == null ? "" : rnf.getEstado().name();
            String descripcion = nvl(rnf.getDescripcion());

            html.append("<tr>")
                    .append("<td>").append(escaparHtml(tipo)).append("</td>")
                    .append("<td><span class='pill'>").append(escaparHtml(prioridad)).append("</span></td>")
                    .append("<td>").append(escaparHtml(estado)).append("</td>")
                    .append("<td>").append(escaparHtml(descripcion)).append("</td>")
                    .append("</tr>");
        }
        html.append("</table></div>");
        return html.toString();
    }

    private String renderReglasNegocio(List<ReglaNegocio> reglas) {
        StringBuilder html = new StringBuilder();
        html.append("<div class='section'>")
                .append("<div class='section-title'>Reglas de negocio</div>");
        if (reglas.isEmpty()) {
            html.append("<div class='muted'>No hay reglas de negocio registradas.</div></div>");
            return html.toString();
        }

        html.append("<table class='grid'>")
                .append("<tr><th>Codigo</th><th>Estado</th><th>Descripcion</th></tr>");
        for (ReglaNegocio regla : reglas) {
            String codigo = nvl(regla.getCodigo());
            String estado = regla.getEstado() == null ? "" : regla.getEstado().name();
            String descripcion = nvl(regla.getDescripcion());
            html.append("<tr>")
                    .append("<td>").append(escaparHtml(codigo)).append("</td>")
                    .append("<td>").append(escaparHtml(estado)).append("</td>")
                    .append("<td>").append(escaparHtml(descripcion)).append("</td>")
                    .append("</tr>");
        }

        html.append("</table></div>");
        return html.toString();
    }

    private String nvl(String valor) {
        return valor == null ? "" : valor;
    }

    private String escaparHtml(String valor) {
        if (valor == null) {
            return "";
        }
        return valor.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}

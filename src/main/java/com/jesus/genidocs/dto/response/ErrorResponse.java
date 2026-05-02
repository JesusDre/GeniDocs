package com.jesus.genidocs.dto.response;

import java.time.LocalDateTime;

public class ErrorResponse {

    private String mensaje;
    private LocalDateTime timestamp;
    private String path;

    public ErrorResponse(String mensaje, LocalDateTime timestamp, String path) {
        this.mensaje = mensaje;
        this.timestamp = timestamp;
        this.path = path;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

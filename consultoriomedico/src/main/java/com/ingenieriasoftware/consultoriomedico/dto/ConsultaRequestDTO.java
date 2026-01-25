package com.ingenieriasoftware.consultoriomedico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ConsultaRequestDTO {

    @NotNull(message = "El ID de la cita es obligatorio")
    private Long idCita;

    @NotBlank(message = "El diagnóstico es obligatorio")
    private String diagnostico;

    private String observaciones;

    @NotBlank(message = "La prescripción es obligatoria")
    private String prescripcion;

    public Long getIdCita() {
        return idCita;
    }

    public void setIdCita(Long idCita) {
        this.idCita = idCita;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getPrescripcion() {
        return prescripcion;
    }

    public void setPrescripcion(String prescripcion) {
        this.prescripcion = prescripcion;
    }
}

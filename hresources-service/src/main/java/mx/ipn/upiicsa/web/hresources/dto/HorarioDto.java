package mx.ipn.upiicsa.web.hresources.dto;

import java.time.LocalTime;

public class HorarioDto {
    public Integer id;
    public Integer idDia;
    public String nombreDia;
    public LocalTime horaInicio;
    public LocalTime horaFin;

    public HorarioDto(Integer id, Integer idDia, String nombreDia, LocalTime horaInicio, LocalTime horaFin) {
        this.id = id;
        this.idDia = idDia;
        this.nombreDia = nombreDia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }
}

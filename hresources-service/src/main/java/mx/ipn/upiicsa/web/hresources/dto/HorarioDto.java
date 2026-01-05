package mx.ipn.upiicsa.web.hresources.dto;

import java.time.LocalTime;

public class HorarioDto {
    public Integer id;
    public Integer idDia;
    public String nombreDia;
    public LocalTime horaInicio;
    public LocalTime horaFin;

    public Integer idSucursal;
    public String nombreSucursal;

    public HorarioDto(Integer id, Integer idSucursal, String nombreSucursal, Integer idDia, String nombreDia,
            LocalTime horaInicio, LocalTime horaFin) {
        this.id = id;
        this.idSucursal = idSucursal;
        this.nombreSucursal = nombreSucursal;
        this.idDia = idDia;
        this.nombreDia = nombreDia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }
}

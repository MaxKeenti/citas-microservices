package mx.ipn.upiicsa.web.hresources.dto;

import java.time.LocalDate;

public class DiaDescansoDto {
    public Integer id;
    public Integer idEmpleado;
    public LocalDate fecha;

    public DiaDescansoDto() {
    }

    public DiaDescansoDto(Integer id, Integer idEmpleado, LocalDate fecha) {
        this.id = id;
        this.idEmpleado = idEmpleado;
        this.fecha = fecha;
    }
}

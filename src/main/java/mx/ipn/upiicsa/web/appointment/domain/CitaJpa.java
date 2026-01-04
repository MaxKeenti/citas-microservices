package mx.ipn.upiicsa.web.appointment.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tci05_cita")
public class CitaJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Integer idCita;

    @Column(name = "fk_id_persona")
    private Integer fkIdPersona;

    @Column(name = "fk_id_servicio")
    private Integer fkIdServicio;

    @Column(name = "fk_id_lista_precio")
    private Integer fkIdListaPrecio;

    @Column(name = "fk_id_sucursal")
    private Integer fkIdSucursal;

    @Column(name = "fk_id_empleado")
    private Integer fkIdEmpleado;

    @Column(name = "fh_cita")
    private LocalDateTime fechaHora;

    @Column(name = "nu_duracion")
    private Integer customDuration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_persona", insertable = false, updatable = false)
    private mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.model.PersonaJpa persona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_servicio", insertable = false, updatable = false)
    private mx.ipn.upiicsa.web.catalog.domain.ServicioJpa servicio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_lista_precio", insertable = false, updatable = false)
    private mx.ipn.upiicsa.web.catalog.domain.ListaPrecioJpa listaPrecio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_sucursal", insertable = false, updatable = false)
    private mx.ipn.upiicsa.web.hresources.domain.SucursalJpa sucursal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_empleado", insertable = false, updatable = false)
    private mx.ipn.upiicsa.web.hresources.domain.EmpleadoJpa empleado;

    // getters/setters
    public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    public Integer getFkIdPersona() {
        return fkIdPersona;
    }

    public void setFkIdPersona(Integer fkIdPersona) {
        this.fkIdPersona = fkIdPersona;
    }

    public Integer getFkIdServicio() {
        return fkIdServicio;
    }

    public void setFkIdServicio(Integer fkIdServicio) {
        this.fkIdServicio = fkIdServicio;
    }

    public Integer getFkIdListaPrecio() {
        return fkIdListaPrecio;
    }

    public void setFkIdListaPrecio(Integer fkIdListaPrecio) {
        this.fkIdListaPrecio = fkIdListaPrecio;
    }

    public Integer getFkIdSucursal() {
        return fkIdSucursal;
    }

    public void setFkIdSucursal(Integer fkIdSucursal) {
        this.fkIdSucursal = fkIdSucursal;
    }

    public Integer getFkIdEmpleado() {
        return fkIdEmpleado;
    }

    public void setFkIdEmpleado(Integer fkIdEmpleado) {
        this.fkIdEmpleado = fkIdEmpleado;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Integer getCustomDuration() {
        return customDuration;
    }

    public void setCustomDuration(Integer customDuration) {
        this.customDuration = customDuration;
    }

    public mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.model.PersonaJpa getPersona() {
        return persona;
    }

    public void setPersona(
            mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.model.PersonaJpa persona) {
        this.persona = persona;
    }

    public mx.ipn.upiicsa.web.catalog.domain.ServicioJpa getServicio() {
        return servicio;
    }

    public void setServicio(mx.ipn.upiicsa.web.catalog.domain.ServicioJpa servicio) {
        this.servicio = servicio;
    }

    public mx.ipn.upiicsa.web.catalog.domain.ListaPrecioJpa getListaPrecio() {
        return listaPrecio;
    }

    public void setListaPrecio(mx.ipn.upiicsa.web.catalog.domain.ListaPrecioJpa listaPrecio) {
        this.listaPrecio = listaPrecio;
    }

    public mx.ipn.upiicsa.web.hresources.domain.SucursalJpa getSucursal() {
        return sucursal;
    }

    public void setSucursal(mx.ipn.upiicsa.web.hresources.domain.SucursalJpa sucursal) {
        this.sucursal = sucursal;
    }

    public mx.ipn.upiicsa.web.hresources.domain.EmpleadoJpa getEmpleado() {
        return empleado;
    }

    public void setEmpleado(mx.ipn.upiicsa.web.hresources.domain.EmpleadoJpa empleado) {
        this.empleado = empleado;
    }
}

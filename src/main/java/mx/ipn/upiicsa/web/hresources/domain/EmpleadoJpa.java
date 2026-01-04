package mx.ipn.upiicsa.web.hresources.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "tce03_empleado")
public class EmpleadoJpa {
    @Id
    @Column(name = "id_empleado")
    private Integer idEmpleado; // Same as Persona ID

    @Column(name = "fk_id_sucursal")
    private Integer fkIdSucursal;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_persona", insertable = false, updatable = false)
    private mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.model.PersonaJpa persona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_sucursal", insertable = false, updatable = false)
    private SucursalJpa sucursal;

    // getters/setters
    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Integer getFkIdSucursal() {
        return fkIdSucursal;
    }

    public void setFkIdSucursal(Integer fkIdSucursal) {
        this.fkIdSucursal = fkIdSucursal;
    }

    public mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.model.PersonaJpa getPersona() {
        return persona;
    }

    public void setPersona(
            mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.model.PersonaJpa persona) {
        this.persona = persona;
    }

    public SucursalJpa getSucursal() {
        return sucursal;
    }

    public void setSucursal(SucursalJpa sucursal) {
        this.sucursal = sucursal;
    }
}

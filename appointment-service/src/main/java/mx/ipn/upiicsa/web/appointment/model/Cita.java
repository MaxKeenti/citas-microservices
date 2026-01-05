package mx.ipn.upiicsa.web.appointment.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tci05_cita")
public class Cita extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    public Integer id;

    @Column(name = "fk_id_persona")
    public Integer idPersona;

    @Column(name = "fk_id_servicio")
    public Integer idServicio;

    @Column(name = "fk_id_lista_precio")
    public Integer idListaPrecio;

    @Column(name = "fk_id_sucursal")
    public Integer idSucursal;

    @Column(name = "fk_id_empleado")
    public Integer idEmpleado;

    @Column(name = "fh_cita")
    public LocalDateTime fechaHora;

    @Column(name = "nu_duracion")
    public Integer duracion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_estado")
    public EstadoCita estado;
}

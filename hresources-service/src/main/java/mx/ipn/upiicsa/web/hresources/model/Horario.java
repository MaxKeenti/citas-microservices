package mx.ipn.upiicsa.web.hresources.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "tce08_horario")
public class Horario extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_horario")
    public Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_id_sucursal")
    public Sucursal sucursal;

    @ManyToOne
    @JoinColumn(name = "fk_id_dia")
    public DiaLaboral diaLaboral;

    @Column(name = "tm_inicio")
    public LocalTime horaInicio;

    @Column(name = "tm_fin")
    public LocalTime horaFin;
}

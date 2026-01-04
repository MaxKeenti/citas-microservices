package mx.ipn.upiicsa.web.hresources.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tce05_dia_descanso")
public class DiaDescanso extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dia_descanso")
    public Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_id_empleado")
    public Empleado empleado;

    @Column(name = "fh_descanso")
    public LocalDate fechaDescanso;
}

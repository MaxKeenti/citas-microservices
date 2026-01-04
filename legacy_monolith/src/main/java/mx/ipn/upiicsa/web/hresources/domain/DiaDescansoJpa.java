package mx.ipn.upiicsa.web.hresources.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "tce05_dia_descanso")
public class DiaDescansoJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dia_descanso")
    private Integer id;

    @Column(name = "fk_id_empleado")
    private Integer idEmpleado;

    @ManyToOne
    @JoinColumn(name = "fk_id_empleado", insertable = false, updatable = false)
    private EmpleadoJpa empleado;

    @Column(name = "fh_descanso")
    private LocalDate fechaDescanso;
}

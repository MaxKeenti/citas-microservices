package mx.ipn.upiicsa.web.hresources.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "tce08_horario")
public class HorarioJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_horario")
    private Integer id;

    @Column(name = "fk_id_sucursal")
    private Integer idSucursal;

    @ManyToOne
    @JoinColumn(name = "fk_id_sucursal", insertable = false, updatable = false)
    private SucursalJpa sucursal;

    @Column(name = "fk_id_dia")
    private Integer idDia;

    @ManyToOne
    @JoinColumn(name = "fk_id_dia", insertable = false, updatable = false)
    private DiaLaboralJpa diaLaboral;

    @Column(name = "tm_inicio")
    private LocalTime horaInicio;

    @Column(name = "tm_fin")
    private LocalTime horaFin;
}

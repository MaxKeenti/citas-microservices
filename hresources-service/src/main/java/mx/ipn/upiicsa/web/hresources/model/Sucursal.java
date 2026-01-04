package mx.ipn.upiicsa.web.hresources.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "tce02_sucursal")
public class Sucursal extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sucursal")
    public Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_id_establecimiento")
    public Establecimiento establecimiento;

    @Column(name = "tx_nombre")
    public String nombre;

    @Column(name = "gm_ubicacion", columnDefinition = "geometry(Point,4326)")
    public Point ubicacion;
}

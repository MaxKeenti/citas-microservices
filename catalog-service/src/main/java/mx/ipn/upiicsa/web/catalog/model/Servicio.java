package mx.ipn.upiicsa.web.catalog.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "cci01_servicio")
public class Servicio extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    public Integer id;

    @Column(name = "tx_nombre")
    public String nombre;

    @Column(name = "tx_descripcion")
    public String descripcion;

    @Column(name = "nu_duracion")
    public Integer duracion;

    @Column(name = "st_activo")
    public Boolean activo;
}

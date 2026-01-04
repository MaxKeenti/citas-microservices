package mx.ipn.upiicsa.web.hresources.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "tce04_dia_laboral")
public class DiaLaboral extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dia")
    public Integer id;

    @Column(name = "tx_nombre")
    public String nombre;

    @Column(name = "tx_descripcion")
    public String descripcion;

    @Column(name = "st_activo")
    public Integer activo;
}

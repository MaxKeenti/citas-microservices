package mx.ipn.upiicsa.web.hresources.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "tce01_establecimiento")
public class Establecimiento extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_establecimiento")
    public Integer id;

    @Column(name = "tx_nombre")
    public String nombre;
}

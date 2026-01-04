package mx.ipn.upiicsa.web.catalog.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "tci01_estado_lista_precio")
public class EstadoListaPrecio extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado")
    public Integer id;

    @Column(name = "tx_nombre")
    public String nombre;
}

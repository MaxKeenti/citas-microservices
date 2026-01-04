package mx.ipn.upiicsa.web.catalog.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "tci02_servicio_lista_precio")
public class ServicioListaPrecio extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio_lista_precio")
    public Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_id_servicio")
    public Servicio servicio;

    @ManyToOne
    @JoinColumn(name = "fk_id_lista_precio")
    public ListaPrecio listaPrecio;

    @Column(name = "nu_precio")
    public Integer precio;
}

package mx.ipn.upiicsa.web.catalog.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tci02_servicio_lista_precio")
public class ServicioListaPrecioJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio_lista_precio")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_id_servicio")
    private ServicioJpa servicio;

    @ManyToOne
    @JoinColumn(name = "fk_id_lista_precio")
    private ListaPrecioJpa listaPrecio;

    @Column(name = "nu_precio")
    private Integer precio;
}

package mx.ipn.upiicsa.web.catalog.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tci01_estado_lista_precio")
public class EstadoListaPrecioJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado")
    private Integer id;

    @Column(name = "tx_nombre")
    private String nombre;
}

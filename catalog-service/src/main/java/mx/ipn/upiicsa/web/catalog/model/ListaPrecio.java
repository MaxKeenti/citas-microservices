package mx.ipn.upiicsa.web.catalog.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tci03_lista_precio")
public class ListaPrecio extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lista_precio")
    public Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_id_estado")
    public EstadoListaPrecio estado;

    @Column(name = "tx_nombre")
    public String nombre;

    @Column(name = "fh_inicio")
    public LocalDateTime inicio;

    @Column(name = "fh_fin")
    public LocalDateTime fin;

    @Column(name = "st_activo")
    public Boolean activo;
}

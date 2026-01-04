package mx.ipn.upiicsa.web.catalog.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime; // Correct import for timestamp
import org.springframework.format.annotation.DateTimeFormat; // For binding

@Data
@Entity
@Table(name = "tci03_lista_precio")
public class ListaPrecioJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lista_precio")
    private Integer idListaPrecio;

    @ManyToOne
    @JoinColumn(name = "fk_id_estado")
    private EstadoListaPrecioJpa estado;

    @Column(name = "tx_nombre")
    private String txNombre;

    @Column(name = "fh_inicio")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fhInicio;

    @Column(name = "fh_fin")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fhFin;

    @Column(name = "st_activo")
    private Boolean stActivo;
}

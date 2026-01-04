package mx.ipn.upiicsa.web.hresources.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tce04_dia_laboral")
public class DiaLaboralJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dia")
    private Integer id;

    @Column(name = "tx_nombre")
    private String nombre;

    @Column(name = "tx_descripcion")
    private String descripcion;

    @Column(name = "st_activo")
    private Integer activo;
}

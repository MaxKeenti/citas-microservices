package mx.ipn.upiicsa.web.hresources.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "tce01_establecimiento")
public class EstablecimientoJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_establecimiento")
    private Integer id;

    @Column(name = "tx_nombre")
    private String txNombre;

    // getters/setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTxNombre() { return txNombre; }
    public void setTxNombre(String txNombre) { this.txNombre = txNombre; }
}

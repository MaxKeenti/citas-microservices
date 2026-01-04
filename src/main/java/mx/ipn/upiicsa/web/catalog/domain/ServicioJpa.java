package mx.ipn.upiicsa.web.catalog.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "cci01_servicio")
public class ServicioJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private Integer idServicio;

    @Column(name = "tx_nombre")
    private String txNombre;

    @Column(name = "tx_descripcion")
    private String descripcion;

    @Column(name = "nu_duracion")
    private Integer nuDuracion;

    @Column(name = "st_activo")
    private Boolean stActivo;

    // getters/setters
    public Integer getIdServicio() { return idServicio; }
    public void setIdServicio(Integer idServicio) { this.idServicio = idServicio; }
    public String getTxNombre() { return txNombre; }
    public void setTxNombre(String txNombre) { this.txNombre = txNombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Integer getNuDuracion() { return nuDuracion; }
    public void setNuDuracion(Integer nuDuracion) { this.nuDuracion = nuDuracion; }
    public Boolean getStActivo() { return stActivo; }
    public void setStActivo(Boolean stActivo) { this.stActivo = stActivo; }
    
    // Alias for controller compatibility if needed, or update controller to use correct getters
    public Integer getId() { return idServicio; }
    public void setNombre(String nombre) { this.txNombre = nombre; }
}

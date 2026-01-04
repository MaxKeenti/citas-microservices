package mx.ipn.upiicsa.web.hresources.domain;

import jakarta.persistence.*;
import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "tce02_sucursal")
public class SucursalJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sucursal")
    private Integer idSucursal;

    @Column(name = "fk_id_establecimiento")
    private Integer fkIdEstablecimiento;

    @ManyToOne
    @JoinColumn(name = "fk_id_establecimiento", insertable = false, updatable = false)
    private EstablecimientoJpa establecimiento;

    @Column(name = "tx_nombre")
    private String txNombre;

    @Column(name = "gm_ubicacion", columnDefinition = "geometry(Point,4326)")
    private Point gmUbicacion;

    // getters/setters
    public Integer getIdSucursal() { return idSucursal; }
    public void setIdSucursal(Integer idSucursal) { this.idSucursal = idSucursal; }
    public Integer getFkIdEstablecimiento() { return fkIdEstablecimiento; }
    public void setFkIdEstablecimiento(Integer fkIdEstablecimiento) { this.fkIdEstablecimiento = fkIdEstablecimiento; }
    public EstablecimientoJpa getEstablecimiento() { return establecimiento; }
    public void setEstablecimiento(EstablecimientoJpa establecimiento) { this.establecimiento = establecimiento; }
    public String getTxNombre() { return txNombre; }
    public void setTxNombre(String txNombre) { this.txNombre = txNombre; }
    public Point getGmUbicacion() { return gmUbicacion; }
    public void setGmUbicacion(Point gmUbicacion) { this.gmUbicacion = gmUbicacion; }
}

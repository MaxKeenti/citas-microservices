package mx.ipn.upiicsa.web.accesscontrol.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cca01_genero")
public class Genero extends PanacheEntityBase {
    @Id
    @Column(name = "id_genero")
    public Integer id;

    @Column(name = "tx_nombre")
    public String nombre;

    @Column(name = "tx_descripcion")
    public String descripcion;

    @Column(name = "st_activo")
    public Boolean activo;
}

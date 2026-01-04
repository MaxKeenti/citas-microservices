package mx.ipn.upiicsa.web.accesscontrol.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "tca02_usuario")
public class Usuario extends PanacheEntityBase {
    @Id
    @Column(name = "id_usuario")
    public Integer id;

    @Column(name = "fk_id_rol")
    public Integer idRol;

    @Column(name = "tx_login")
    public String login;

    @Column(name = "tx_password")
    public String password;

    @Column(name = "st_activo")
    public Boolean activo;

    @ManyToOne
    @JoinColumn(name = "fk_id_rol", referencedColumnName = "id_rol", insertable = false, updatable = false)
    public Rol rol;

    @OneToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_persona", insertable = false, updatable = false)
    public Persona persona;
}

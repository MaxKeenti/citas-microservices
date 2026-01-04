package mx.ipn.upiicsa.web.hresources.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "tce03_empleado")
public class Empleado extends PanacheEntityBase {
    @Id
    @Column(name = "id_empleado")
    public Integer id; // Same as Persona ID

    @ManyToOne
    @JoinColumn(name = "fk_id_sucursal")
    public Sucursal sucursal;

    // References Persona in AccessControl service
    // We can't map @OneToOne here properly without the entity.
    // So we assume the ID is shared manually.
}

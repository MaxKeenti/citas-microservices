package mx.ipn.upiicsa.web.accesscontrol.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tca01_persona")
public class Persona extends PanacheEntityBase {
    @Id
    @SequenceGenerator(name = "tca01_persona_id_persona_seq", sequenceName = "tca01_persona_id_persona_seq", allocationSize = 1)
    @GeneratedValue(generator = "tca01_persona_id_persona_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id_persona")
    public Integer id;

    @Column(name = "fk_id_genero")
    public Integer idGenero;

    @Column(name = "tx_nombre")
    public String nombre;

    @Column(name = "tx_primer_apellido")
    public String primerApellido;

    @Column(name = "tx_segundo_apellido")
    public String segundoApellido;

    @Column(name = "fh_nacimiento")
    public LocalDate fechaNacimiento;

    @ManyToOne
    @JoinColumn(name = "fk_id_genero", referencedColumnName = "id_genero", insertable = false, updatable = false)
    public Genero genero;
}

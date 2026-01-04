package mx.ipn.upiicsa.web.accesscontrol.infrastructure.adapter.out.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import mx.ipn.upiicsa.web.accesscontrol.domain.Usuario;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "tca02_usuario")
public class UsuarioJpa {
    @Id
    @Column(name = "id_usuario")
    private Integer id;
    @Column(name = "fk_id_rol")
    private Integer idRol;
    @Column(name = "tx_login")
    private String login;
    @Column(name = "tx_password")
    private String password;
    @Column(name = "st_activo")
    private Boolean activo;
    @ManyToOne
    @JoinColumn(name = "fk_id_rol", referencedColumnName = "id_rol", insertable = false, updatable = false)
    private RolJpa rol;
    @OneToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_persona", insertable = false, updatable = false)
    private PersonaJpa persona;

    public static UsuarioJpa fromEntity(Usuario entity) {
        return UsuarioJpa.builder()
                .id(entity.getId())
                .idRol(entity.getIdRol())
                .login(entity.getLogin())
                .password(entity.getPassword())
                .activo(entity.getActivo())
                .build();
    }

    public Usuario toEntity() {
        return Usuario.builder()
                .id(id)
                .idRol(idRol)
                .login(login)
                .password(password)
                .activo(activo)
                .build();
    }
}

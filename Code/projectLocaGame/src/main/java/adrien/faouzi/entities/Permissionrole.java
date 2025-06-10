package adrien.faouzi.entities;

import javax.persistence.*;
import java.util.Objects;
@NamedQueries(value = {
        @NamedQuery(name = "Permissionrole.selectPermissionRoleByIdRole", query = "SELECT pr from Permissionrole pr " +
                "where pr.idRole.id = :idRole")
})


@Entity
@Table(name = "permissionrole")
public class Permissionrole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPermissionRole", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idPermission", nullable = false)
    private Permission idPermission;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idRole", nullable = false)
    private Role idRole;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permissionrole that = (Permissionrole) o;
        return idPermission == that.idPermission && idRole == that.idRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPermission, idRole);
    }


    public Role getIdRole() {
        return idRole;
    }

    public void setIdRole(Role idRole) {
        this.idRole = idRole;
    }

    public Permission getIdPermission() {
        return idPermission;
    }

    public void setIdPermission(Permission idPermission) {
        this.idPermission = idPermission;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
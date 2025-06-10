package adrien.faouzi.entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@NamedQueries(value = {
        @NamedQuery(name = "Permission.selectPermissionAll", query = "SELECT p from Permission p order by p.id desc")
})


@Entity
@Table(name = "permission", indexes = {
        @Index(name = "permissionName", columnList = "permissionName", unique = true)
})
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPermission", nullable = false)
    private int id;

    @Column(name = "permissionName", nullable = false, length = 60)
    private String permissionName;

    @OneToMany(mappedBy = "idPermission")
    private Set<Permissionrole> permissionroles = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return id == that.id && Objects.equals(permissionName, that.permissionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, permissionName);
    }

    public Set<Permissionrole> getPermissionroles() {
        return permissionroles;
    }

    public void setPermissionroles(Set<Permissionrole> permissionroles) {
        this.permissionroles = permissionroles;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
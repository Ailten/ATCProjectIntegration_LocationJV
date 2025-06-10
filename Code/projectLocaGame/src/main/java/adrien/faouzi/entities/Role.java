package adrien.faouzi.entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@NamedQueries(value = {
        @NamedQuery(name = "Role.SelectRoleByRoleName", query = "SELECT r from Role r " +
                "where r.roleName = :roleName"),
        @NamedQuery(name="Role.SelectRoleAll", query ="SELECT r from Role r"),
        @NamedQuery(name="Role.SelectRoleById", query="SELECT r from Role r where r.id = :id")
})

@Entity
@Table(name = "role", indexes = {
        @Index(name = "roleName", columnList = "roleName", unique = true)
})
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRole", nullable = false)
    private int id;

    @Column(name = "roleName", nullable = false, length = 60)
    private String roleName;

    @OneToMany(mappedBy = "idRole")
    private Set<User> users = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idRole")
    private Set<Permissionrole> permissionroles = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return id == role.id && Objects.equals(roleName, role.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleName);
    }


    public Set<Permissionrole> getPermissionroles() {
        return permissionroles;
    }

    public void setPermissionroles(Set<Permissionrole> permissionroles) {
        this.permissionroles = permissionroles;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
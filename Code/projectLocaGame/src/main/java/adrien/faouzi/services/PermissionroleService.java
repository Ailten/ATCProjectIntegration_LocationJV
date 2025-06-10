package adrien.faouzi.services;

import adrien.faouzi.entities.Permissionrole;

import javax.persistence.EntityManager;
import java.util.List;

public class PermissionroleService
{
    /**
     * Permissionrole request method by idRole
     * @param idRole
     * @param em
     * @return
     */
    public List<Permissionrole> findPermissionRoleByIdRole(int idRole, EntityManager em)
    {
        return em.createNamedQuery("Permissionrole.selectPermissionRoleByIdRole", Permissionrole.class)
                .setParameter("idRole", idRole)
                .getResultList();
    }

}

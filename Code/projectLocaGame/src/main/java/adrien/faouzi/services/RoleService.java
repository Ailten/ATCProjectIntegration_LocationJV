package adrien.faouzi.services;

import adrien.faouzi.entities.Role;

import javax.persistence.EntityManager;
import java.util.List;

public class RoleService
{
    /**
     * Role request method by roleName
     * @param roleName
     * @return
     */
    public Role findRoleByRoleName(String roleName, EntityManager em)
    {
        return em.createNamedQuery("Role.SelectRoleByRoleName", Role.class)
                .setParameter("roleName", roleName)
                .getSingleResult();
    }


    /**
     * Find role by id of role
     * @param id
     * @param em
     * @return
     */
    public Role findRoleById(int id, EntityManager em)
    {
        return em.createNamedQuery("Role.SelectRoleById", Role.class)
                .setParameter("id", id)
                .getSingleResult();
    }



    /**
     * Role request method for all
     */
    public List<Role> findRoleAll (EntityManager em)
    {
        return em.createNamedQuery("Role.SelectRoleAll", Role.class)
                .getResultList();
    }
}

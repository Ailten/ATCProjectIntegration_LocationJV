package adrien.faouzi.services;

import adrien.faouzi.entities.Permission;
import adrien.faouzi.entities.Permissionrole;

import javax.persistence.EntityManager;
import java.util.List;

public class PermissionService
{
    public List<Permission> findPermissionAll(EntityManager em)
    {
        return em.createNamedQuery("Permission.selectPermissionAll", Permission.class)
                .getResultList();
    }
}

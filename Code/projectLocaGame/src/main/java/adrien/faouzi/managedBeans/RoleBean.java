package adrien.faouzi.managedBeans;

import adrien.faouzi.entities.Permission;
import adrien.faouzi.entities.Permissionrole;
import adrien.faouzi.entities.Role;
import adrien.faouzi.projetlocagame.connexion.EMF;
import adrien.faouzi.services.PermissionService;
import adrien.faouzi.services.PermissionroleService;
import adrien.faouzi.utility.UtilityProcessing;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Named
@SessionScoped
public class RoleBean implements Serializable
{

    /**
     * Field
     */
    private Role role;
    private List<Role> roleList;
    private char modeSelected = 'r';
    private List<Permission> permissionListAll;
    private List<Permissionrole> permissionrolesList;



    public void initPermission()
    {
        EntityManager em = EMF.getEM();
        PermissionService permissionService = new PermissionService();
        EntityTransaction transaction = em.getTransaction();

        try{
            transaction.begin();

            //Call of the service that will use the NamedQuery of the "permission" entity
            this.permissionListAll = permissionService.findPermissionAll(em);

            transaction.commit();
        }catch (Exception e)
        {
            UtilityProcessing.debug("je suis dans le catch de la recherche de toute les permissions" + e);
            this.permissionListAll = new ArrayList<>();
        }
        finally {
            if(transaction.isActive())
                transaction.rollback();
            em.close();
        }
    }


    public void updatePermissionRoleWithPermission()
    {
        EntityManager em = EMF.getEM();
        PermissionroleService permissionroleService = new PermissionroleService();
        EntityTransaction transaction = em.getTransaction();

        try{
            transaction.begin();

            //Call of the service that will use the NamedQuery of the "permission" entity
            this.permissionrolesList = permissionroleService.findPermissionRoleByIdRole(this.role.getId(),em);

            transaction.commit();
        }catch (Exception e)
        {
            UtilityProcessing.debug("je suis dans le catch de la recherche de toute les permissions" + e);
            this.permissionrolesList = new ArrayList<>();
        }
        finally {
            if(transaction.isActive())
                transaction.rollback();
            em.close();
        }
    }


    /**
     * char for type of page generate (c,r,u,d)
     * @param modeAsk
     * @return
     */
    public boolean isModeSelected(char modeAsk){
        return (this.modeSelected == modeAsk);
    }

    /**
     * Load user selected in previous page
     * @param userBean
     */
    public void loadUserSelected (UserBean userBean)
    {
        boolean isNewRedirect = userBean.getNewRedirect();
        if(!isNewRedirect){ //reload form from same page.
            return; //do not reload entity from db.
        }

        this.modeSelected = userBean.getModeRedirection();
    }


    /**
     * For commandButton input disabled method
     * @param idPermissionAsk
     * @return
     */
    public boolean isPermessionInPermissionApply(int idPermissionAsk){
        return permissionrolesList.stream().filter(pr -> pr.getId() == idPermissionAsk)
                .findFirst()
                .orElse(null) != null;
    }



    /**
     * Getter and Setter
     * @return
     */
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public char getModeSelected() {
        return modeSelected;
    }

    public void setModeSelected(char modeSelected) {
        this.modeSelected = modeSelected;
    }

    public List<Permission> getPermissionListAll() {
        return permissionListAll;
    }

    public void setPermissionListAll(List<Permission> permissionList) {
        this.permissionListAll = permissionList;
    }

    public List<Permissionrole> getPermissionrolesList() {
        return permissionrolesList;
    }

    public void setPermissionrolesList(List<Permissionrole> permissionrolesList) {
        this.permissionrolesList = permissionrolesList;
    }
}

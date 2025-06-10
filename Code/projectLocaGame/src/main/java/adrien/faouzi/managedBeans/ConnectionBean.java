package adrien.faouzi.managedBeans;

import adrien.faouzi.entities.User;
import adrien.faouzi.exeption.ConnectionUserExecption;
import adrien.faouzi.projetlocagame.connexion.EMF;
import adrien.faouzi.services.PermissionroleService;
import adrien.faouzi.services.UserService;
import adrien.faouzi.utility.UtilityProcessing;
import org.primefaces.PrimeFaces;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;

@Named
@SessionScoped
public class ConnectionBean implements Serializable
{

    private User user;
    private User userForm = new User();
    private String messageErrorConnection ="hidden";
    private String password;

    /**
     * Verification connection method
     */
    public String connection()
    {
        //UtilityProcessing.debug("session user : " + this.user);
        //initialize.
        EntityManager em = EMF.getEM();
        UserService userService = new UserService();
        EntityTransaction transaction = em.getTransaction();
        String redirect;

        this.user = new User();

        try
        {
            transaction.begin();
            //Call of the service that will use the NamedQuery of the "User" entity
            this.userForm = userService.findUserByMail(this.userForm.getMail(), em);
            checkUserConnection(this.userForm, this.password);
            this.user = userForm;
            this.messageErrorConnection = "hidden";
            redirect = "/accueil";
            transaction.commit();
        }
        catch(Exception e)
        {
            UtilityProcessing.debug(" je suis dans le catch de la connexion : " + e);

            //Put an error message
            this.messageErrorConnection = "";
            redirect = "/view/connection";
        }
        finally
        {
            if(transaction.isActive())
            transaction.rollback();
            em.close();
        }

        return redirect;
    }

    /**
     * destroy session connected method
     */
    public String destroySession()
    {
        String redirect;

        // Logout session connectionBean
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        if("/accueil.xhtml".equals(FacesContext.getCurrentInstance().getViewRoot().getViewId()))
        {
             redirect = "";
        }
        else
        {
            redirect = "/accueil";
        }
        // managed bean go to js
        PrimeFaces.current().executeScript("submitLanguageForm(\"headerLanguageButtonContainer\")");
        return redirect;
    }

    /**
     * User processing method
     */
    public void checkUserConnection (User userRequest, String password) throws ConnectionUserExecption
    {
        if (! (UtilityProcessing.checkPassword(password,userRequest)
                && userRequest.getEnable()))
        {
            throw new ConnectionUserExecption();
        }
    }

    /**
     * Initialize list Permissionrole
     */
    public static void initListPermissionRole(User user)
    {
        EntityManager em = EMF.getEM();
        PermissionroleService permissionroleService = new PermissionroleService();
        EntityTransaction transaction = em.getTransaction();
        try
        {
            transaction.begin();
            //Call of the service that will use the NamedQuery of the "Permissionrole" entity
            user.listPermissionRole = permissionroleService.findPermissionRoleByIdRole(user.getIdRole().getId(), em);
            transaction.commit();
        }
        catch(Exception e)
        {
            UtilityProcessing.debug(" je suis dans le catch de l'initialization du Permissionrole : " + e);
        }
        finally
        {
            if(transaction.isActive())
                transaction.rollback();
            em.close();
        }

    }




    /**
     * Redirection go to connection page
     * @return
     */
    public String goToPageConnection()
    {
        return "/view/connection";

    }

    /**
     * Redirection go to home page
     * @return
     */
    public String goToPageAccueil(){

        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        PrimeFaces.current().executeScript("submitLanguageForm(\"headerLanguageButtonContainer\")");

        return "/accueil";
    }


    /**
     * Getter and setter method
     */

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessageErrorConnection() {
        String message = this.messageErrorConnection;
        this.messageErrorConnection = "hidden";
        return message;
    }
    public void setMessageErrorConnection(String messageErrorConnection) {
        this.messageErrorConnection = messageErrorConnection;
    }

    public User getUserForm() {
        return userForm;
    }

    public void setUserForm(User userForm) {
        this.userForm = userForm;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }




    //ask is user log has permissions send.
    public boolean verifyPermissionUser(String permissionName){
        if(this.user == null || this.user.getId()==0)
            return false;
        return this.user.verifyPermission(permissionName);
    }

    public boolean verifyNotPermissionUser(String permissionName){
        return !(verifyPermissionUser(permissionName));
    }
}

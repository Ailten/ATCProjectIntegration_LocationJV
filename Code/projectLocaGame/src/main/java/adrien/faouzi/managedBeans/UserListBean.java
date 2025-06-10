package adrien.faouzi.managedBeans;

import adrien.faouzi.entities.User;
import adrien.faouzi.projetlocagame.connexion.EMF;
import adrien.faouzi.services.UserService;
import adrien.faouzi.utility.TableFilter;
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
public class UserListBean extends TableFilter<User> implements Serializable {

    //all users filtered by user.
    private List<User> userFiltered;


    /**
     * do SQL research method (width filter, order by, pagination managed by PrimeFaces).
     */
    public void initialiseUserFiltered(){
        userFiltered = new ArrayList<>();

        //UtilityProcessing.debug(" order : " +this.orderAsc);
        EntityManager em = EMF.getEM();
        UserService userService = new UserService();
        EntityTransaction transaction = em.getTransaction();
        boolean orderby = this.orderAsc;
        try
        {
            transaction.begin();

            if(this.order.equals("enable"))
            {
                orderby = !orderby;
            }
            if(orderby)
            {
                this.userFiltered = userService.findUserByFilterAsc(this.filter, this.order, em);
            }
            else
            {
                this.userFiltered = userService.findUserByFilterDesc(this.filter, this.order, em);
            }
            transaction.commit();
        }catch(Exception e)
        {
            UtilityProcessing.debug("Je suis dans le catch de la recherche par utilisateur : " +e);

        }finally {
            if(transaction.isActive())
            {
                transaction.rollback();
            }
            em.close();
        }
    }



    /**
     * getter method
     */

    public void setUserFiltered(List<User> userFiltered) {
        this.userFiltered = userFiltered;
    }

    public List<User> getUserFiltered() {
        return userFiltered;
    }

}

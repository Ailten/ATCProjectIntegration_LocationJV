package adrien.faouzi.managedBeans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;

import adrien.faouzi.entities.Store;
import adrien.faouzi.projetlocagame.connexion.EMF;
import javax.persistence.EntityManager;

import adrien.faouzi.services.StoreService;
import adrien.faouzi.utility.UtilityProcessing;
import org.apache.log4j.Logger;

@Named
@ApplicationScoped
public class StoreBean implements Serializable {

    private Store store;

    /**
     *getter and setter method
     * @return Store
     */
    public Store getStore()
    {
        if(this.store == null)
            this.loadDataStore();
        return store;
    }
    public void setStore(Store store) {
        this.store = store;
    }

    /**
     * DB data recovery
     */
    public void loadDataStore()
    {
        //initialize.
        //EntityManager em = EMF.getEM(); //--> in parent service.
        EntityManager em = EMF.getEM();
        StoreService storeService = new StoreService();

        try
        {
            //Call of the service that will use the NamedQuery of the "Store" entity
             this.store = storeService.findStoreByIdStore(1, em);
        }
        catch(Exception e)
        {
            UtilityProcessing.debug("Erreur de récupération de données de la page du magasin " + e);
        }
        finally
        {
            //em.close(); //--> in parent service. warning, close all services instanciated.
            em.close();
        }
    }
}

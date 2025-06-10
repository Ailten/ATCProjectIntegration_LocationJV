package adrien.faouzi.managedBeans;

import adrien.faouzi.convectorCustom.PricePlatformConverter;
import adrien.faouzi.convectorCustom.ProductConverter;
import adrien.faouzi.entities.Priceplatform;
import adrien.faouzi.projetlocagame.connexion.EMF;
import adrien.faouzi.services.PricePlatformService;
import adrien.faouzi.services.ProductService;
import adrien.faouzi.utility.TableFilter;
import adrien.faouzi.utility.UtilityProcessing;
import org.primefaces.PrimeFaces;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ManagedBean
@SessionScoped
public class PricePlatformListBean extends TableFilter<Priceplatform> implements Serializable {

    /**
     * make a research in db for list entity with filter.
     */
    public void doResearch(){
        EntityManager em = EMF.getEM();
        PricePlatformService pricePlatformService = new PricePlatformService();
        try{
            entityFiltered = pricePlatformService.findPricePlatformByFilter(this.filter, this.order, this.orderAsc, em);
        }catch(Exception e){
            UtilityProcessing.debug(e.getMessage());
            entityFiltered = new ArrayList<>();
        }finally{
            em.close();
        }

    }



    /**
     * delete an entity from page list (delete db).
     * @param idEntity id of entity to delete.
     * @param permission permission to delete entity.
     */
    public void deleteEntity(int idEntity, boolean permission){
        if(!permission)
            return;
        int countJoin;

        EntityManager em = EMF.getEM();
        PricePlatformService pricePlatformService = new PricePlatformService();
        EntityTransaction transaction = em.getTransaction();
        try{
            countJoin = pricePlatformService.getCountOfJoinCopy(idEntity, em);
            if(countJoin==0){ //this entity has no join in db.

                //delete entity
                transaction.begin();
                pricePlatformService.delete(PricePlatformConverter.getAsObjectStatic(String.valueOf(idEntity)), em); //delete product.
                transaction.commit();

            }
        }catch(Exception e){
            UtilityProcessing.debug(e.getMessage());
            countJoin = -1;
        }finally{
            if(transaction.isActive())
                transaction.rollback();
            em.close();
        }

        if(countJoin>0){ //impossible delete because join.
            PrimeFaces.current().executeScript("alertI18NMessage(\"errorDeleteBecauseJoin\")");
        }else if(countJoin==0){ //success delete.
            PrimeFaces.current().executeScript("alertI18NMessage(\"successDelete\", true)");
        }else{ //error process.
            PrimeFaces.current().executeScript("alertI18NMessage(\"errorProcessDelete\")");
        }

    }



    /**
     * function for add basket in theory, not full script, just alert js with name product selected.
     * @param idPricePlatform id of entity to add basket.
     * @param permission permission to delete entity.
     */
    public void AddBasket(int idPricePlatform, boolean permission){
        if(!permission)
            return;

        //add basket not scripted.
        //insert join order-price-platform (idPricePlatform).
        Priceplatform pricePlatformToAddBasket = PricePlatformConverter.getAsObjectStatic(String.valueOf(idPricePlatform));

        PrimeFaces.current().executeScript("alertI18NAddBasket(\"successAddBasket\", \""+pricePlatformToAddBasket.getPricePlatformName()+"\")");
    }

}

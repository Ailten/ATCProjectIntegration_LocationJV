package adrien.faouzi.managedBeans;

import adrien.faouzi.convectorCustom.CategoryConverter;
import adrien.faouzi.entities.Category;
import adrien.faouzi.projetlocagame.connexion.EMF;
import adrien.faouzi.services.CategoryService;
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

@Named
@ManagedBean
@SessionScoped
public class CategoryListBean extends TableFilter<Category> implements Serializable {

    /**
     * make a research in db for list entity with filter.
     */
    public void doResearch(){

        EntityManager em = EMF.getEM();
        CategoryService categoryService = new CategoryService();
        try{
            entityFiltered = categoryService.findCategoryByFilter(this.filter, this.order, this.orderAsc, em);
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
        CategoryService categoryService = new CategoryService();
        EntityTransaction transaction = em.getTransaction();
        try{
            countJoin = categoryService.getCountOfJoin(idEntity, em);
            if(countJoin==0){ //this entity has no join in db.

                //delete entity
                transaction.begin();
                categoryService.delete(CategoryConverter.getAsObjectStatic(String.valueOf(idEntity)), em);
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

}

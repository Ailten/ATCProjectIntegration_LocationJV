package adrien.faouzi.managedBeans;

import adrien.faouzi.convectorCustom.CategoryConverter;
import adrien.faouzi.entities.Category;
import adrien.faouzi.projetlocagame.connexion.EMF;
import adrien.faouzi.services.CategoryService;
import adrien.faouzi.utility.CrudManaging;
import adrien.faouzi.utility.TableFilter;
import adrien.faouzi.utility.UtilityProcessing;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;

@Named
@SessionScoped
public class CategoryBean extends CrudManaging<Category> implements Serializable {

    /**
     * load entity (in parent CrudBean) for crud form.
     * @param tableFilter object parent of listBean contain redirection page information and id of entity selected.
     */
    public void loadCategorySelected(TableFilter<Category> tableFilter){

        //when update form from this same form. --->
        setTableFilter(tableFilter);
        if(!tableFilter.getNewRedirect()){ //reload form from same page.
            return; //do not reload entity from db.
        }

        setErrorSubmitDB(false);

        //when first load form in create. --->
        this.modeSelected = tableFilter.getModeRedirection();
        if(modeSelected == 'c') {
            elementCrudSelected = new Category();
            return;
        }

        //get element selected from db by id send by other page.
        elementCrudSelected = CategoryConverter.getAsObjectStatic(String.valueOf(tableFilter.getIdRedirection()));

    }



    /**
     * submit form entity (create or update mode).
     * @param historicalBean historic management class.
     * @param permission the permission for submit form (create or update).
     * @return last page historic or null.
     */
    public String submitForm(HistoricalBean historicalBean, boolean permission){
        if(!permission)
            return null;
        boolean submitIsSuccess = true;
        setErrorSubmitDB(false);

        //do create or update.
        if(isModeSelected('c') || isModeSelected('u')){

            EntityManager em = EMF.getEM();
            CategoryService categoryService = new CategoryService();
            EntityTransaction transaction = em.getTransaction();
            try{
                transaction.begin();
                if(isModeSelected('c')) {
                    categoryService.insertCategory(this.elementCrudSelected, em); //insert.
                    resetFilterOfTableFilter(); //if create mode and success insert, reset filter from page list.
                }else
                    categoryService.updateCategory(this.elementCrudSelected, em); //update.
                transaction.commit();
            }catch(Exception e){
                UtilityProcessing.debug("error catch (in create/update Category) : "+e.getMessage());
                submitIsSuccess=false;
                setErrorSubmitDB(true);
            }finally{
                if(transaction.isActive())
                    transaction.rollback();
                em.close();
            }

        }else{ //error
            submitIsSuccess=false;
            setErrorSubmitDB(true);
        }

        return ((submitIsSuccess)? historicalBean.backLastPageHistoric(): null);

    }

}

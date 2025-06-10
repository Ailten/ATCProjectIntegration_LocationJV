package adrien.faouzi.managedBeans;

import adrien.faouzi.convectorCustom.CopyConverter;
import adrien.faouzi.entities.Copy;
import adrien.faouzi.entities.Priceplatform;
import adrien.faouzi.entities.Store;
import adrien.faouzi.projetlocagame.connexion.EMF;
import adrien.faouzi.services.CopyService;
import adrien.faouzi.services.PricePlatformService;
import adrien.faouzi.services.StoreService;
import adrien.faouzi.utility.CrudManaging;
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
public class CopyBean extends CrudManaging<Copy> implements Serializable {

    /**
     * load entity (in parent CrudBean) for crud form.
     * @param tableFilter object parent of listBean contain redirection page information and id of entity selected.
     */
    public void loadCopySelected(TableFilter<Copy> tableFilter){

        //when update form from this same form. --->
        setTableFilter(tableFilter);
        if(!tableFilter.getNewRedirect()){ //reload form from same page.
            return; //do not reload entity from db.
        }

        setErrorSubmitDB(false);

        //when first load form in create. --->
        this.modeSelected = tableFilter.getModeRedirection();
        if(modeSelected == 'c') {
            elementCrudSelected = new Copy();
            return;
        }

        //get element selected from db by id send by other page.
        elementCrudSelected = CopyConverter.getAsObjectStatic(String.valueOf(tableFilter.getIdRedirection()));

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
            CopyService copyService = new CopyService();
            EntityTransaction transaction = em.getTransaction();
            Copy copy;
            try{
                transaction.begin();

                this.elementCrudSelected.setCopyNameFalse(); //set a copy name false, for validate insert.
                copy = ((isModeSelected('c'))?
                        copyService.insert(this.elementCrudSelected, em): //insert.
                        copyService.update(this.elementCrudSelected, em) //update.
                );
                copy.setCopyName(); //re-apply copy name based on his own id.
                copy = copyService.update(this.elementCrudSelected, em); //update copy name.
                copyService.reCalculatePricePlatformAvailableStock(copy.getIdPricePlatform(), em); //re calculate available stock.

                if(isModeSelected('c')){
                    resetFilterOfTableFilter(); //if create mode and success insert, reset filter from page list.
                }

                transaction.commit();
            }catch(Exception e){
                UtilityProcessing.debug("error catch (in create/update Copy) : "+e.getMessage());
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



    //list all store for select.
    private List<Store> allStore;
    public List<Store> getAllStore(){ return this.allStore; }
    public void initAllStore(){
        EntityManager em = EMF.getEM();
        StoreService storeService = new StoreService();
        try{
            this.allStore = storeService.selectStoreAll(em);
        }catch(Exception e){
            this.allStore = new ArrayList<>();
        }finally{
            em.close();
        }
    }



    //list all pricePlatform for select.
    private List<Priceplatform> allPricePlatform;
    public List<Priceplatform> getAllPricePlatform(){ return this.allPricePlatform; }
    public void initAllPricePlatform(){
        EntityManager em = EMF.getEM();
        PricePlatformService pricePlatformService = new PricePlatformService();
        try{
            this.allPricePlatform = pricePlatformService.selectPricePlatformAll(em);
        }catch(Exception e){
            this.allPricePlatform = new ArrayList<>();
        }finally{
            em.close();
        }
    }

}

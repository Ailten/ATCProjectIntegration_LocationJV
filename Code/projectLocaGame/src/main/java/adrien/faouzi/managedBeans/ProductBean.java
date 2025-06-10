package adrien.faouzi.managedBeans;

import adrien.faouzi.convectorCustom.ProductConverter;
import adrien.faouzi.entities.Category;
import adrien.faouzi.entities.Editor;
import adrien.faouzi.entities.Languagegame;
import adrien.faouzi.entities.Product;
import adrien.faouzi.projetlocagame.connexion.EMF;
import adrien.faouzi.services.*;
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
public class ProductBean extends CrudManaging<Product> implements Serializable {

    private boolean isNewRedirect; //using for many to many exception.

    /**
     * load entity (in parent CrudBean) for crud form.
     * @param tableFilter object parent of listBean contain redirection page information and id of entity selected.
     */
    public void loadProductSelected(TableFilter<Product> tableFilter){

        //when update form from this same form. --->
        setTableFilter(tableFilter);
        isNewRedirect = tableFilter.getNewRedirect();
        if(!isNewRedirect){ //reload form from same page.
            return; //do not reload entity from db.
        }

        setErrorSubmitDB(false);

        //when first load form in create. --->
        this.modeSelected = tableFilter.getModeRedirection();
        if(modeSelected == 'c') {
            elementCrudSelected = new Product();
            listCategoryApply = new ArrayList<>(); //set list empty for many to many.
            listLanguageApply = new ArrayList<>();
            return;
        }

        //get product seleted from db by id send by other page.
        elementCrudSelected = ProductConverter.getAsObjectStatic(String.valueOf(tableFilter.getIdRedirection()));
        if(elementCrudSelected!=null){
            listCategoryApply = elementCrudSelected.getListCategory(); //set list from db for many to many.
            listLanguageApply = elementCrudSelected.getListLanguageGame();
        }

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

        //do verification.
        if(isCategoryApplyError(false)){
            submitIsSuccess=false;
        }else if(isLanguageApplyError(false)){
            submitIsSuccess=false;
        }else{

            //do create or update.
            if(isModeSelected('c') || isModeSelected('u')){

                EntityManager em = EMF.getEM();
                ProductService productService = new ProductService();
                CategoryProductService categoryProductService = new CategoryProductService();
                LanguageProductService languageProductService = new LanguageProductService();
                EntityTransaction transaction = em.getTransaction();
                Product product;
                try{
                    transaction.begin();


                    //create or update product.
                    product = ((isModeSelected('c'))?
                            productService.insertProduct(this.elementCrudSelected, em) : //insert.
                            productService.updateProduct(this.elementCrudSelected, em)   //update.
                    );


                    //insert or delete join of product and category.
                    product.setListCategory(null); //force reload list category from db.
                    List<Category> listCategoryFromDB = product.getListCategory();
                    int i, j;
                    boolean findMatch;
                    for(i=0; i<Math.max(listCategoryFromDB.size(), listCategoryApply.size()); i++){

                        //delete find.
                        if(listCategoryFromDB.size()>i){
                            findMatch=false;
                            for(j=0; j<listCategoryApply.size(); j++){
                                if(listCategoryFromDB.get(i).getId() == listCategoryApply.get(j).getId()){
                                    findMatch=true;
                                    break;
                                }
                            }
                            if(!findMatch){ //no match, so delete from DB.
                                categoryProductService.deleteCategoryProduct(
                                        listCategoryFromDB.get(i),
                                        this.elementCrudSelected,
                                        em);
                            }
                        }

                        //insert find.
                        if(listCategoryApply.size()>i){
                            findMatch=false;
                            for(j=0; j<listCategoryFromDB.size(); j++){
                                if(listCategoryApply.get(i).getId() == listCategoryFromDB.get(j).getId()){
                                    findMatch=true;
                                    break;
                                }
                            }
                            if(!findMatch){ //no match, so insert from Apply.
                                categoryProductService.insertCategoryProduct(
                                        listCategoryApply.get(i),
                                        this.elementCrudSelected,
                                        em);
                            }
                        }

                    }


                    //insert or delete join of product and language.
                    product.setListLanguageGame(null); //force reload list language from db.
                    List<Languagegame> listLanguageFromDB = product.getListLanguageGame();
                    for(i=0; i<Math.max(listLanguageFromDB.size(), listLanguageApply.size()); i++){

                        //delete find.
                        if(listLanguageFromDB.size()>i){
                            findMatch=false;
                            for(j=0; j<listLanguageApply.size(); j++){
                                if(listLanguageFromDB.get(i).getId() == listLanguageApply.get(j).getId()){
                                    findMatch=true;
                                    break;
                                }
                            }
                            if(!findMatch){ //no match, so delete from DB.
                                languageProductService.deleteLanguageProduct(
                                        listLanguageFromDB.get(i),
                                        this.elementCrudSelected,
                                        em);
                            }
                        }

                        //insert find.
                        if(listLanguageApply.size()>i){
                            findMatch=false;
                            for(j=0; j<listLanguageFromDB.size(); j++){
                                if(listLanguageApply.get(i).getId() == listLanguageFromDB.get(j).getId()){
                                    findMatch=true;
                                    break;
                                }
                            }
                            if(!findMatch){ //no match, so insert from Apply.
                                languageProductService.insertLanguageProduct(
                                        listLanguageApply.get(i),
                                        this.elementCrudSelected,
                                        em);
                            }
                        }

                    }

                    if(isModeSelected('c')){ //if create mode and success insert, reset filter from page list.
                        resetFilterOfTableFilter();
                    }

                    transaction.commit();
                }catch(Exception e){
                    UtilityProcessing.debug("error catch (in create/update Product) : "+e.getMessage());
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

        }

        return ((submitIsSuccess)? historicalBean.backLastPageHistoric(): null);
    }



    //list editor for select input.
    private List<Editor> allEditor;
    public List<Editor> getAllEditor(){
        return this.allEditor;
    }
    public void initAllEditor(){
        EntityManager em = EMF.getEM();
        EditorService editorService = new EditorService();
        try{
            this.allEditor = editorService.selectEditorAll(em);
        }catch(Exception e){
            this.allEditor = new ArrayList<>();
        }finally{
            em.close();
        }
    }



    //list category get all from db.
    private List<Category> allCategory;
    public List<Category> getAllCategory(){
        return this.allCategory;
    }
    public void initAllCategory(){
        EntityManager em = EMF.getEM();
        CategoryService categoryService = new CategoryService();
        try{
            this.allCategory = categoryService.selectCategoryAll(em);
        }catch(Exception e){
            this.allCategory = new ArrayList<>();
        }finally{
            em.close();
        }
    }
    //get set list category into product.
    private List<Category> listCategoryApply;
    public List<Category> getListCategoryApply(){
        return this.listCategoryApply;
    }
    public void setListCategoryApply(List<Category> listCategoryApply){
        this.listCategoryApply = listCategoryApply;
    }
    public boolean isCategoryInCategoryApply(int idCategoryAsk){
        return listCategoryApply.stream().filter(c -> c.getId() == idCategoryAsk)
                .findFirst()
                .orElse(null) != null;
    }
    //when user select category for apply to product.
    public void applyCategory(int idCategory){
        listCategoryApply.add(
                allCategory.stream().filter(c -> c.getId() == idCategory)
                        .findFirst()
                        .orElse(null)
        );
    }
    //when user select category for delete to product.
    public void deleteCategoryApply(int idCategory){
        listCategoryApply.remove(
                listCategoryApply.stream().filter(c -> c.getId() == idCategory)
                        .findFirst()
                        .orElse(null)
        );
    }
    //rendered error for many to many (min relation need).
    public boolean isCategoryApplyError(){
        return isCategoryApplyError(true);
    }
    public boolean isCategoryApplyError(boolean applyVerifyNewRedirect){
        return false;
        //return ( //it was an error when...
        //        listCategoryApply.size() < 0 && //length is lower than relation MCD.
        //        (!applyVerifyNewRedirect || !isNewRedirect) //and don't render error if it's first load. (or unable verification new redirect)
        //);
    }



    //list language for select input.
    private List<Languagegame> allLanguage;
    public List<Languagegame> getAllLanguage(){
        return this.allLanguage;
    }
    public void initAllLanguage(){
        EntityManager em = EMF.getEM();
        LanguageGameService languageGameService = new LanguageGameService();
        try{
            this.allLanguage = languageGameService.selectLanguageGameAll(em);
        }catch(Exception e){
            this.allLanguage = new ArrayList<>();
        }finally{
            em.close();
        }
    }
    //get set list language into product.
    private List<Languagegame> listLanguageApply;
    public List<Languagegame> getListLanguageApply(){
        return this.listLanguageApply;
    }
    public void setListLanguageApply(List<Languagegame> listLanguageApply){
        this.listLanguageApply = listLanguageApply;
    }
    public boolean isLanguageInLanguageApply(int idLanguageAsk){
        return listLanguageApply.stream().filter(l -> l.getId() == idLanguageAsk)
                .findFirst()
                .orElse(null) != null;
    }
    //when user select language for apply to product.
    public void applyLanguage(int idLanguage){
        listLanguageApply.add(
                allLanguage.stream().filter(l -> l.getId() == idLanguage)
                        .findFirst()
                        .orElse(null)
        );
    }
    //when user select language for delete to product.
    public void deleteLanguageApply(int idLanguage){
        listLanguageApply.remove(
                listLanguageApply.stream().filter(l -> l.getId() == idLanguage)
                .findFirst()
                .orElse(null)
        );
    }
    //rendered error for many to many (min relation need).
    public boolean isLanguageApplyError(){
        return isLanguageApplyError(true);
    }
    public boolean isLanguageApplyError(boolean applyVerifyNewRedirect){
        return ( //it was an error when...
                listLanguageApply.size() < 1 && //length is lower than relation MCD.
                (!applyVerifyNewRedirect || !isNewRedirect) //and don't render error if it's first load. (or unable verification new redirect)
        );
    }


}

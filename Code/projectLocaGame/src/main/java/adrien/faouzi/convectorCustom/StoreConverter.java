package adrien.faouzi.convectorCustom;

import adrien.faouzi.entities.Store;
import adrien.faouzi.projetlocagame.connexion.EMF;
import adrien.faouzi.services.StoreService;
import adrien.faouzi.utility.UtilityProcessing;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("StoreConverter")
public class StoreConverter implements Converter {

    //cast from string to object.
    @Override
    public Store getAsObject(FacesContext context, UIComponent component, String value)
    {
        return getAsObjectStatic(value);
    }

    //cast from object to string.
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        return getAsStringStatic(value);
    }

    //static cast from string to object.
    public static Store getAsObjectStatic(String value)
    {
        if (value==null || value.equals("0") || value.equals("")) {
            return null;
        }

        EntityManager em = EMF.getEM();
        StoreService storeService = new StoreService();
        Store store;
        try{
            store = storeService.findStoreByIdStore(Integer.parseInt(value), em);
        }catch(Exception e){
            store = null;
        }finally{
            em.close();
        }
        return store;
    }

    //static cast from object to string.
    public String getAsStringStatic(Object value)
    {
        if(value==null){
            return "0";
        }
        Store store = (Store) value;
        return String.valueOf(store.getId());
    }

}



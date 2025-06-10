package adrien.faouzi.convectorCustom;

import adrien.faouzi.entities.Priceplatform;
import adrien.faouzi.projetlocagame.connexion.EMF;
import adrien.faouzi.services.PricePlatformService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("PricePlatformConverter")
public class PricePlatformConverter implements Converter {

    //cast from string to object.
    @Override
    public Priceplatform getAsObject(FacesContext context, UIComponent component, String value)
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
    public static Priceplatform getAsObjectStatic(String value)
    {
        if (value==null || value.equals("0") || value.equals("")) {
            return null;
        }

        EntityManager em = EMF.getEM();
        PricePlatformService pricePlatformService = new PricePlatformService();
        Priceplatform pricePlatform;
        try{
            pricePlatform = pricePlatformService.findPricePlatformById(Integer.parseInt(value), em);
        }catch(Exception e){
            pricePlatform = null;
        }finally{
            em.close();
        }
        return pricePlatform;
    }

    //static cast from object to string.
    public String getAsStringStatic(Object value)
    {
        if(value==null){
            return "0";
        }
        Priceplatform pricePlatform = (Priceplatform) value;
        return String.valueOf(pricePlatform.getId());
    }

}


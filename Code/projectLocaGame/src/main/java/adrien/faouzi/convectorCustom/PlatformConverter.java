package adrien.faouzi.convectorCustom;

import adrien.faouzi.entities.Platform;
import adrien.faouzi.projetlocagame.connexion.EMF;
import adrien.faouzi.services.PlatformService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("PlatformConverter")
public class PlatformConverter implements Converter {

    //cast from string to object.
    @Override
    public Platform getAsObject(FacesContext context, UIComponent component, String value)
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
    public static Platform getAsObjectStatic(String value)
    {
        if (value==null || value.equals("0") || value.equals("")) {
            return null;
        }

        EntityManager em = EMF.getEM();
        PlatformService platformService = new PlatformService();
        Platform platform;
        try{
            platform = platformService.selectPlatformByIdPlatform(Integer.parseInt(value), em);
        }catch(Exception e){
            platform = null;
        }finally{
            em.close();
        }
        return platform;
    }

    //static cast from object to string.
    public String getAsStringStatic(Object value)
    {
        if(value==null){
            return "0";
        }
        Platform platform = (Platform) value;
        return String.valueOf(platform.getId());
    }

}

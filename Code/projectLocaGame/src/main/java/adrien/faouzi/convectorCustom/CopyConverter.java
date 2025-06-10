package adrien.faouzi.convectorCustom;

import adrien.faouzi.entities.Copy;
import adrien.faouzi.projetlocagame.connexion.EMF;
import adrien.faouzi.services.CopyService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("CopyConverter")
public class CopyConverter implements Converter {

    //cast from string to object.
    @Override
    public Copy getAsObject(FacesContext context, UIComponent component, String value)
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
    public static Copy getAsObjectStatic(String value)
    {
        if (value==null || value.equals("0") || value.equals("")) {
            return null;
        }

        EntityManager em = EMF.getEM();
        CopyService copyService = new CopyService();
        Copy copy;
        try{
            copy = copyService.selectCopyByIdCopy(Integer.parseInt(value), em);
        }catch(Exception e){
            copy = null;
        }finally{
            em.close();
        }
        return copy;
    }

    //static cast from object to string.
    public String getAsStringStatic(Object value)
    {
        if(value==null){
            return "0";
        }
        Copy copy = (Copy) value;
        return String.valueOf(copy.getId());
    }

}

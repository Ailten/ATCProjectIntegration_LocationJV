package adrien.faouzi.convectorCustom;

import adrien.faouzi.enumeration.MultiPlayer;
import adrien.faouzi.enumeration.Pegi;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("PegiConverter")
public class PegiConverter implements Converter {

    @Override
    public Pegi getAsObject(FacesContext context, UIComponent component, String value)
    {
        return getAsObjectStatic(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        return getAsStringStatic(value);
    }

    //---> static methode for call without instantiate in bean.

    public static Pegi getAsObjectStatic(String value){

        if (value==null || value.equals("null") || value.equals("")) {
            return null;
        }

        return Pegi.intToEnum(Integer.valueOf(value));

    }


    public String getAsStringStatic(Object value)
    {
        if(value==null)
            return "null";
        Pegi pegi = (Pegi) value;
        return pegi.getPegi();
    }

}
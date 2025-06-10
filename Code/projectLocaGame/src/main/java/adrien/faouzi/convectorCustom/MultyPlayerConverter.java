package adrien.faouzi.convectorCustom;

import adrien.faouzi.enumeration.MultiPlayer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("MultyPlayerConverter")
public class MultyPlayerConverter implements Converter {

    //Enum version.

    @Override
    public MultiPlayer getAsObject(FacesContext context, UIComponent component, String value)
    {
        return getAsObjectStatic(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        return getAsStringStatic(value);
    }

    //---> static methode for call without instantiate in bean.

    public static MultiPlayer getAsObjectStatic(String value){

        if (value==null || value.equals("null") || value.equals("")) {
            return null;
        }

        return MultiPlayer.strToEnum(value);

    }


    public String getAsStringStatic(Object value)
    {
        if(value==null)
            return "null";
        MultiPlayer multiPlayer = (MultiPlayer) value;
        return multiPlayer.getMultiPlayer();
    }

}
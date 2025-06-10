package adrien.faouzi.convectorCustom;

import adrien.faouzi.enumeration.StatusCopy;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("StatusCopyConverter")
public class StatusCopyConverter implements Converter {

    //cast from string to object.
    @Override
    public StatusCopy getAsObject(FacesContext context, UIComponent component, String value)
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
    public static StatusCopy getAsObjectStatic(String value)
    {
        if (value==null || value.equals("null") || value.equals(""))
            return null;
        return StatusCopy.strToEnum(value);
    }

    //static cast from object to string.
    public String getAsStringStatic(Object value)
    {
        if(value==null){
            return "0";
        }
        StatusCopy statusCopy = (StatusCopy) value;
        return String.valueOf(statusCopy.getStatusCopy());
    }

}

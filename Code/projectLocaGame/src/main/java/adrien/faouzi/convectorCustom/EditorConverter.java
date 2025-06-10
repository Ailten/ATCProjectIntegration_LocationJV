package adrien.faouzi.convectorCustom;

import adrien.faouzi.entities.Editor;
import adrien.faouzi.projetlocagame.connexion.EMF;
import adrien.faouzi.services.EditorService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("EditorConverter")
public class EditorConverter implements Converter {

    @Override
    public Editor getAsObject(FacesContext context, UIComponent component, String value)
    {
        return getAsObjectStatic(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        return getAsStringStatic(value);
    }

    //---> static methode for call without instantiate in bean.

    public static Editor getAsObjectStatic(String value){

        if (value==null || value.equals("0") || value.equals("")) {
            return null;
        }

        EntityManager em = EMF.getEM();
        EditorService editorService = new EditorService();
        Editor editor;
        try
        {
            editor = editorService.selectEditorByIdEditor(Integer.parseInt(value), em);
        }catch (Exception e)
        {
            editor = null;
        }
        finally {
            em.close();
        }
        return editor;

    }


    public String getAsStringStatic(Object value)
    {
        if(value==null){
            return "0";
        }
        Editor editor = (Editor) value;
        return String.valueOf(editor.getId());
    }

}

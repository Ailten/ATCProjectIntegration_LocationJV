package adrien.faouzi.convectorCustom;

import adrien.faouzi.entities.City;
import adrien.faouzi.projetlocagame.connexion.EMF;
import adrien.faouzi.services.CityService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@FacesConverter ("CityConverter")
public class CityConverter implements Converter
{
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (null == value || 0 == value.length()) {
            return null;
        }


        EntityManager em = EMF.getEM();
        CityService cityService = new CityService();
        //EntityTransaction transaction = em.getTransaction();
        City city;
        try
        {
            //transaction.begin();
            city = cityService.findCityById(Integer.parseInt(value), em);
            //transaction.commit();
        }catch (Exception e)
        {
            return null;
        }
        finally {
            //if(transaction.isActive())
            //{
            //    transaction.rollback();
            //}
            em.close();
        }
        return city;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        City city = (City) value;
        if(city==null){
            return "0";
        }
        return String.valueOf(city.getId());
    }

}

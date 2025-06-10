package adrien.faouzi.services;

import adrien.faouzi.entities.City;
import javax.persistence.EntityManager;
import java.util.List;

public class CityService {

    /**
     *  City request method by Postal Code
     * @param postalCode
     * @return
     */
    public List<City> findCityByPostalCode(int postalCode, EntityManager em)
    {
        return em.createNamedQuery("City.SelectCityByPostalCode", City.class)
                .setParameter("postalCode", postalCode)
                .getResultList();
    }

    /**
     * City request method by id
     */
    public City findCityById (int id, EntityManager em)
    {
        return em.createNamedQuery("City.SelectCityById", City.class)
                .setParameter("id", id)
                .getSingleResult();
    }

}

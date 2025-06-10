package adrien.faouzi.services;

import adrien.faouzi.entities.City;
import adrien.faouzi.entities.Country;
import adrien.faouzi.projetlocagame.connexion.EMF;

import javax.persistence.EntityManager;
import java.util.List;

public class CountryService
{
    /**
     *  Country global request method
     * @return
     */
    public List<Country> findCountryAll(EntityManager em)
    {
        return em.createNamedQuery("Country.SelectCountryAll", Country.class)
                .getResultList();
    }
}

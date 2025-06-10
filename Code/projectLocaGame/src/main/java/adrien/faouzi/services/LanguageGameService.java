package adrien.faouzi.services;

import adrien.faouzi.entities.Languagegame;

import javax.persistence.EntityManager;
import java.util.List;

public class LanguageGameService{

    /**
     * get list of entity by fk product.
     * @param idProduct fk product.
     * @param em entity manager.
     * @return list entity match.
     */
    public List<Languagegame> findLanguageGameByIdProduct(int idProduct, EntityManager em)
    {
        return em.createNamedQuery("LanguageGame.SelectLanguageGameByIdProduct", Languagegame.class)
                .setParameter("idProduct", idProduct)
                .getResultList();
    }



    /**
     * get all entity from db.
     * @param em entity manager.
     * @return list entity.
     */
    public List<Languagegame> selectLanguageGameAll(EntityManager em)
    {
        return em.createNamedQuery("LanguageGame.SelectLanguageGameAll", Languagegame.class)
                .getResultList();
    }



    /**
     * get a single entity from db.
     * @param idLanguage id of entity ask.
     * @param em entity manager.
     * @return entity match.
     */
    public Languagegame selectLanguageByIdLanguage(int idLanguage, EntityManager em)
    {
        return em.createNamedQuery("LanguageGame.SelectLanguageGameByIdLanguage", Languagegame.class)
                .setParameter("idLanguage", idLanguage)
                .getSingleResult();
    }

}

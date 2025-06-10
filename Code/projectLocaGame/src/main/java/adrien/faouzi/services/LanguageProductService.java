package adrien.faouzi.services;

import adrien.faouzi.entities.*;

import javax.persistence.EntityManager;

public class LanguageProductService {

    /**
     * insert in db an entity LanguageProduct (join).
     * @param language entity language for join.
     * @param product entity product for join.
     * @param em entity manager.
     * @return entity join inserted.
     */
    public Languageproduct insertLanguageProduct(Languagegame language, Product product, EntityManager em)
    {
        //make join.
        Languageproduct languageProduct = new Languageproduct();
        languageProduct.setIdLanguage(language);
        languageProduct.setIdProduct(product);

        //do insert.
        em.persist(languageProduct);
        em.flush();
        return languageProduct;
    }



    /**
     * delete in db an entity LanguageProduct (join).
     * @param language entity language for join.
     * @param product entity product for join.
     * @param em entity manager.
     */
    public void deleteLanguageProduct(Languagegame language, Product product, EntityManager em)
    {
        em.createNamedQuery("LanguageProduct.deleteLanguageProduct")
                .setParameter("idLanguage", language.getId())
                .setParameter("idProduct", product.getId())
                .executeUpdate();
    }

}

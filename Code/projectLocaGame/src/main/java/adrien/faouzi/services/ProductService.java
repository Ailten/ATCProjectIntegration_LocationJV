package adrien.faouzi.services;

import adrien.faouzi.Interface.IService;
import adrien.faouzi.entities.*;

import javax.persistence.EntityManager;
import java.util.List;

public class ProductService {

    /**
     * get all entity from db.
     * @param em entity manager.
     * @return list entity.
     */
    public List<Product> selectProductAll(EntityManager em)
    {
        return em.createNamedQuery("Product.SelectProductAll", Product.class)
                .getResultList();
    }



    /**
     * get single entity by id entity.
     * @param idProduct id entity.
     * @param em entity manager.
     * @return entity match.
     */
    public Product selectProductByIdProduct(int idProduct, EntityManager em)
    {
        return em.createNamedQuery("Product.SelectProductByIdProduct", Product.class)
                .setParameter("idProduct", idProduct)
                .getSingleResult();
    }



    /**
     * research entity matching with a filter.
     * @param researchWord word using for research.
     * @param orderBy word using for order.
     * @param asc is order ascending.
     * @param em entity manager.
     * @return list entity matching.
     */
    public List<Product> findProductByFilter(String researchWord, String orderBy, boolean asc, EntityManager em)
    {
        if(orderBy.equals("enable"))
            asc = !asc;

        return em.createNamedQuery("Product.SelectProductByFilter"+
                ((orderBy.equals("id"))? "OrderByNum": "OrderByStr")+
                ((asc)? "Asc": "Desc"),
                Product.class)
                .setParameter("researchWord", researchWord.toLowerCase())
                .setParameter("orderBy", orderBy)
                //.setParameter("ascOrDesc", ((asc)? "asc": "desc"))
                .getResultList();
    }



    /**
     * insert an entity in db.
     * @param product entity to insert.
     * @param em entity manager.
     * @return entity inserted.
     */
    public Product insertProduct(Product product, EntityManager em)
    {
        em.persist(product);
        em.flush();
        return product;
    }



    /**
     * update an entity in db.
     * @param product entity to update.
     * @param em entity manager.
     * @return entity updated.
     */
    public Product updateProduct(Product product, EntityManager em)
    {
        em.merge(product);
        em.flush();
        return product;
    }



    /**
     * count join of an entity before delete.
     * @param idProduct id of entity ask.
     * @param em entity manager.
     * @return count of join.
     */
    public int getCountOfJoinPricePlatform(int idProduct, EntityManager em){
        return em.createNamedQuery("Product.SelectJoinPricePlatform", Priceplatform.class)
                .setParameter("idProduct", idProduct)
                .getResultList().size();
    }



    /**
     * delete entity from db.
     * @param product entity ask delete.
     * @param em entity manager.
     */
    public void delete(Product product, EntityManager em){
        if(!em.contains(product))
            product = em.merge(product);
        em.remove(product);
        em.flush();
    }



    /**
     * delete join of categoryProduct from db, before delete a product.
     * @param idProduct entity gonna be delete.
     * @param em entity manager.
     */
    public void deleteCategoryProductJoinToAProduct(int idProduct, EntityManager em){
        List<Categoryproduct> categoryProducts = em.createNamedQuery("Product.SelectJoinCategoryProduct", Categoryproduct.class)
                .setParameter("idProduct", idProduct)
                .getResultList();
        int i;
        for(i=0; i<categoryProducts.size(); i++){
            if(!em.contains(categoryProducts.get(i)))
                categoryProducts.set(i, em.merge(categoryProducts.get(i)));
            em.remove(categoryProducts.get(i));
        }
        em.flush();
    }



    /**
     * delete join of languageProduct from db, before delete a product.
     * @param idProduct entity gonna be delete.
     * @param em entity manager.
     */
    public void deleteLanguageProductJoinToAProduct(int idProduct, EntityManager em){
        List<Languageproduct> languageProducts = em.createNamedQuery("Product.SelectJoinLanguageProduct", Languageproduct.class)
                .setParameter("idProduct", idProduct)
                .getResultList();
        int i;
        for(i=0; i<languageProducts.size(); i++){
            if(!em.contains(languageProducts.get(i)))
                languageProducts.set(i, em.merge(languageProducts.get(i)));
            em.remove(languageProducts.get(i));
        }
        em.flush();
    }

}

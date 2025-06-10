package adrien.faouzi.services;

import adrien.faouzi.entities.Category;
import adrien.faouzi.entities.Categoryproduct;
import adrien.faouzi.entities.Product;

import javax.persistence.EntityManager;

public class CategoryProductService {

    /**
     * insert in db an entity CategoryProduct (join).
     * @param category entity category for join.
     * @param product entity product for join.
     * @param em entity manager.
     * @return entity join inserted.
     */
    public Categoryproduct insertCategoryProduct(Category category, Product product, EntityManager em)
    {
        //make join.
        Categoryproduct categoryProduct = new Categoryproduct();
        categoryProduct.setIdCategory(category);
        categoryProduct.setIdProduct(product);

        //do insert.
        em.persist(categoryProduct);
        em.flush();
        return categoryProduct;
    }



    /**
     * delete in db an entity CategoryProduct (join).
     * @param category entity category for join.
     * @param product entity product for join.
     * @param em entity manager.
     */
    public void deleteCategoryProduct(Category category, Product product, EntityManager em)
    {
        em.createNamedQuery("CategoryProduct.deleteCategoryProduct")
                .setParameter("idCategory", category.getId())
                .setParameter("idProduct", product.getId())
                .executeUpdate();
    }

}

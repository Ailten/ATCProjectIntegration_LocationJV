package adrien.faouzi.services;

import adrien.faouzi.entities.Category;
import adrien.faouzi.entities.Categoryproduct;

import javax.persistence.EntityManager;
import java.util.List;

public class CategoryService {

    /**
     * get list of entity by fk product.
     * @param idProduct fk product.
     * @param em entity manager.
     * @return list entity match.
     */
    public List<Category> findCategoryByIdProduct(int idProduct, EntityManager em)
    {
        return em.createNamedQuery("Category.SelectCategoryByIdProduct", Category.class)
                .setParameter("idProduct", idProduct)
                .getResultList();
    }



    /**
     * get all entity from db.
     * @param em entity manager.
     * @return list entity.
     */
    public List<Category> selectCategoryAll(EntityManager em)
    {
        return em.createNamedQuery("Category.SelectCategoryAll", Category.class)
                .getResultList();
    }



    /**
     * get single entity by id entity.
     * @param idCategory id entity.
     * @param em entity manager.
     * @return entity match.
     */
    public Category selectCategoryByIdCategory(int idCategory, EntityManager em)
    {
        return em.createNamedQuery("Category.SelectCategoryByIdCategory", Category.class)
                .setParameter("idCategory", idCategory)
                .getSingleResult();
    }



    /**
     * insert an entity in db.
     * @param category entity to insert.
     * @param em entity manager.
     * @return entity inserted.
     */
    public Category insertCategory(Category category, EntityManager em)
    {
        em.persist(category);
        em.flush();
        return category;
    }



    /**
     * update an entity in db.
     * @param category entity to update.
     * @param em entity manager.
     * @return entity updated.
     */
    public Category updateCategory(Category category, EntityManager em)
    {
        em.merge(category);
        em.flush();
        return category;
    }



    /**
     * research entity matching with a filter.
     * @param researchWord word using for research.
     * @param orderBy word using for order.
     * @param asc is order ascending.
     * @param em entity manager.
     * @return list entity matching.
     */
    public List<Category> findCategoryByFilter(String researchWord, String orderBy, boolean asc, EntityManager em)
    {
        return em.createNamedQuery("Category.SelectCategoryByFilter"+
                ((orderBy.equals("id"))? "OrderByNum": "OrderByStr")+
                ((asc)? "Asc": "Desc"),
                Category.class)
                .setParameter("researchWord", researchWord.toLowerCase())
                .setParameter("orderBy", orderBy)
                //.setParameter("ascOrDesc", ((asc)? "asc": "desc"))
                .getResultList();
    }



    /**
     * count join of an entity before delete.
     * @param idCategory id of entity ask.
     * @param em entity manager.
     * @return count of join.
     */
    public int getCountOfJoin(int idCategory, EntityManager em){
        return em.createNamedQuery("Category.SelectJoin", Categoryproduct.class)
                .setParameter("idCategory", idCategory)
                .getResultList().size();
    }



    /**
     * delete entity from db.
     * @param category entity ask delete.
     * @param em entity manager.
     */
    public void delete(Category category, EntityManager em){
        if(!em.contains(category))
            category = em.merge(category);
        em.remove(category);
        em.flush();
    }

}

package adrien.faouzi.services;

import adrien.faouzi.entities.*;

import javax.persistence.EntityManager;
import java.util.List;

public class PricePlatformService
{

    /**
     * research entity matching with a filter.
     * @param researchWord word using for research.
     * @param orderBy word using for order.
     * @param asc is order ascending.
     * @param em entity manager.
     * @return list entity matching.
     */
    public List<Priceplatform> findPricePlatformByFilter(String researchWord, String orderBy, boolean asc, EntityManager em)
    {
        if(orderBy.equals("enable")) //reverse bool and order like a string ("false" > "true")
            asc = !asc;

        return em.createNamedQuery("PricePlatform.SelectPricePlatformByFilter"+
                ((orderBy.equals("id") || orderBy.equals("rentalprice"))? "OrderByNum": "OrderByStr")+ //force 2 type query (order string and order number).
                ((asc)? "Asc": "Desc"), //force 2 type query (asc and desc).
                Priceplatform.class)
                .setParameter("researchWord", researchWord.toLowerCase())
                .setParameter("orderBy", orderBy)
                //.setParameter("ascOrDesc", ((asc)? "asc": "desc"))
                .getResultList();
    }



    /**
     * get single entity by id entity.
     * @param idPricePlatform id entity.
     * @param em entity manager.
     * @return entity match.
     */
    public Priceplatform findPricePlatformById(int idPricePlatform, EntityManager em){
        return em.createNamedQuery("PricePlatform.SelectPricePlatformById", Priceplatform.class)
                .setParameter("idPricePlatform", idPricePlatform)
                .getSingleResult();
    }



    /**
     * count join of an entity before delete.
     * @param idPricePlatform id of entity ask.
     * @param em entity manager.
     * @return count of join.
     */
    public int getCountOfJoinCopy(int idPricePlatform, EntityManager em){
        return em.createNamedQuery("PricePlatform.SelectJoinCopy", Copy.class)
                .setParameter("idPricePlatform", idPricePlatform)
                .getResultList().size();
    }



    /**
     * delete entity from db.
     * @param pricePlatform entity ask delete.
     * @param em entity manager.
     */
    public void delete(Priceplatform pricePlatform, EntityManager em){
        if(!em.contains(pricePlatform))
            pricePlatform = em.merge(pricePlatform);
        em.remove(pricePlatform);
        em.flush();
    }



    /**
     * get all pricePlatform from db.
     * @param em entity manager for aces to db.
     * @return list of all pricePlatform from db.
     */
    public List<Priceplatform> selectPricePlatformAll(EntityManager em)
    {
        return em.createNamedQuery("PricePlatform.SelectPricePlatformAll", Priceplatform.class)
                .getResultList();
    }



    /**
     * insert an entity in db.
     * @param pricePlatform entity to insert.
     * @param em entity manager.
     * @return entity inserted.
     */
    public Priceplatform insert(Priceplatform pricePlatform, EntityManager em)
    {
        em.persist(pricePlatform);
        em.flush();
        return pricePlatform;
    }



    /**
     * update an entity in db.
     * @param pricePlatform entity to update.
     * @param em entity manager.
     * @return entity updated.
     */
    public Priceplatform update(Priceplatform pricePlatform, EntityManager em)
    {
        em.merge(pricePlatform);
        em.flush();
        return pricePlatform;
    }

}

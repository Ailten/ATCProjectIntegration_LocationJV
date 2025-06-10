package adrien.faouzi.services;

import adrien.faouzi.convectorCustom.PricePlatformConverter;
import adrien.faouzi.entities.Copy;
import adrien.faouzi.entities.Priceplatform;
import adrien.faouzi.enumeration.StatusCopy;

import javax.persistence.EntityManager;
import java.util.List;

public class CopyService {

    /**
     * select one copy from db, selected by id.
     * @param idCopy id of copy research in db.
     * @param em entity manager for aces to db.
     * @return one instance of copy.
     */
    public Copy selectCopyByIdCopy(int idCopy, EntityManager em)
    {
        return em.createNamedQuery("Copy.SelectCopyByIdCopy", Copy.class)
                .setParameter("idCopy", idCopy)
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
    public List<Copy> findCopyByFilter(String researchWord, String orderBy, boolean asc, EntityManager em)
    {
        return em.createNamedQuery("Copy.SelectCopyByFilter"+
                ((orderBy.equals("id") || orderBy.equals("buyprice"))? "OrderByNum": "OrderByStr")+
                ((asc)? "Asc": "Desc"),
                Copy.class)
                .setParameter("researchWord", researchWord.toLowerCase())
                .setParameter("orderBy", orderBy)
                //.setParameter("ascOrDesc", ((asc)? "asc": "desc"))
                .getResultList();
    }



    /**
     * count join of an entity before delete.
     * @param idCopy id of entity ask.
     * @param em entity manager.
     * @return count of join.
     */
    public int getCountOfJoin(int idCopy, EntityManager em){
        return 0;
    }



    /**
     * delete entity from db.
     * @param copy entity ask delete.
     * @param em entity manager.
     */
    public void delete(Copy copy, EntityManager em){
        if(!em.contains(copy))
            copy = em.merge(copy);
        em.remove(copy);
        em.flush();
    }



    /**
     * count copy link to the same pricePlatform (using for copy name).
     * @param copy entity.
     * @param em entity manager.
     * @return count of copy of the same pricePlatform.
     */
    public int getCountCopy(Copy copy, EntityManager em){
        if(copy.getId()==0 || copy.getIdPricePlatform()==null)
            return 0;
        return em.createNamedQuery("Copy.SelectCountCopy", Copy.class)
                .setParameter("idCopy", copy.getId())
                .setParameter("idPricePlatform", copy.getIdPricePlatform().getId())
                .getResultList().size();
    }



    /**
     * insert an entity in db.
     * @param copy entity to insert.
     * @param em entity manager.
     * @return entity inserted.
     */
    public Copy insert(Copy copy, EntityManager em)
    {
        em.persist(copy);
        em.flush();
        return copy;
    }



    /**
     * update an entity in db.
     * @param copy entity to update.
     * @param em entity manager.
     * @return entity updated.
     */
    public Copy update(Copy copy, EntityManager em)
    {
        em.merge(copy);
        em.flush();
        return copy;
    }



    /**
     * re calculate available stock in pricePlatform (before delete copy or after create/update copy).
     * @param pricePlatform pricePlatform who need to be re calculate available stock.
     * @param em entity manager.
     */
    public void reCalculatePricePlatformAvailableStock(Priceplatform pricePlatform, EntityManager em){
        pricePlatform.setAvailableStock(
                em.createNamedQuery("Copy.SelectCountCopyAvailableForAPricePlatform", Copy.class)
                        .setParameter("idPricePlatform", pricePlatform.getId())
                        .setParameter("statusDisponible", StatusCopy.DISPONIBLE)
                        .getResultList().size()
        );
        em.merge(pricePlatform);
        em.flush();
    }

}

package adrien.faouzi.services;

import adrien.faouzi.entities.Platform;
import adrien.faouzi.entities.Product;

import javax.persistence.EntityManager;
import java.util.List;

public class PlatformService {

    /**
     * get all entity from db.
     * @param em entity manager.
     * @return list entity.
     */
    public List<Platform> selectPlatformAll(EntityManager em)
    {
        return em.createNamedQuery("Platform.SelectPlatformAll", Platform.class)
                .getResultList();
    }



    /**
     * get single entity by id entity.
     * @param idPlatform id entity.
     * @param em entity manager.
     * @return entity match.
     */
    public Platform selectPlatformByIdPlatform(int idPlatform, EntityManager em)
    {
        return em.createNamedQuery("Platform.SelectPlatformByIdPlatform", Platform.class)
                .setParameter("idPlatform", idPlatform)
                .getSingleResult();
    }



    /**
     * insert an entity in db.
     * @param platform entity to insert.
     * @param em entity manager.
     * @return entity inserted.
     */
    public Platform insertPlatform(Platform platform, EntityManager em)
    {
        em.persist(platform);
        em.flush();
        return platform;
    }



    /**
     * update an entity in db.
     * @param platform entity to update.
     * @param em entity manager.
     * @return entity updated.
     */
    public Platform updatePlatform(Platform platform, EntityManager em)
    {
        em.merge(platform);
        em.flush();
        return platform;
    }



    /**
     * research entity matching with a filter.
     * @param researchWord word using for research.
     * @param orderBy word using for order.
     * @param asc is order ascending.
     * @param em entity manager.
     * @return list entity matching.
     */
    public List<Platform> findPlatformByFilter(String researchWord, String orderBy, boolean asc, EntityManager em)
    {
        return em.createNamedQuery("Platform.SelectPlatformByFilter"+
                ((orderBy.equals("id"))? "OrderByNum": "OrderByStr")+
                ((asc)? "Asc": "Desc"),
                Platform.class)
                .setParameter("researchWord", researchWord.toLowerCase())
                .setParameter("orderBy", orderBy)
                //.setParameter("ascOrDesc", ((asc)? "asc": "desc"))
                .getResultList();
    }



    /**
     * count join of an entity before delete.
     * @param idPlatform id of entity ask.
     * @param em entity manager.
     * @return count of join.
     */
    public int getCountOfJoin(int idPlatform, EntityManager em){
        return em.createNamedQuery("Platform.SelectJoin", Product.class)
                .setParameter("idPlatform", idPlatform)
                .getResultList().size();
    }



    /**
     * delete entity from db.
     * @param platform entity ask delete.
     * @param em entity manager.
     */
    public void delete(Platform platform, EntityManager em){
        if(!em.contains(platform))
            platform = em.merge(platform);
        em.remove(platform);
        em.flush();
    }

}

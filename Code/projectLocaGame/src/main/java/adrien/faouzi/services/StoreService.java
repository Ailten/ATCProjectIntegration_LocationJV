package adrien.faouzi.services;

import adrien.faouzi.entities.Store;
import adrien.faouzi.projetlocagame.connexion.EMF;

import javax.persistence.EntityManager;
import java.util.List;

public class StoreService
{
    /**
     * Store recovery method
     */
    public Store findStoreByIdStore(int id, EntityManager em)
    {
        return em.createNamedQuery("Store.SelectStoreByIdStore", Store.class)
                .setParameter("idStore", id)
                .getSingleResult();
    }


    /**
     * get all store from db.
     * @param em entity manager for aces to db.
     * @return list of all store from db.
     */
    public List<Store> selectStoreAll(EntityManager em)
    {
        return em.createNamedQuery("Store.SelectStoreAll", Store.class)
                .getResultList();
    }

}

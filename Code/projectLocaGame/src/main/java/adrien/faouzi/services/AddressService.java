package adrien.faouzi.services;

import adrien.faouzi.entities.Address;

import javax.persistence.EntityManager;

public class AddressService
{
    /**
     * Add address in DB method
     */
    public void addAddress(Address address, EntityManager em)
    {
        em.persist(address);
    }
}

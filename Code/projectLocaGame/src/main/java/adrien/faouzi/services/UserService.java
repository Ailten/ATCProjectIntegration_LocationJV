package adrien.faouzi.services;

import adrien.faouzi.entities.User;

import javax.persistence.EntityManager;
import java.util.List;

public class UserService
{
    /**
     * User request method by email and password
     * @param mail
     * @return
     */
    public User findUserByMail(String mail, EntityManager em)
    {
        return em.createNamedQuery("User.SelectUserConnexion", User.class)
                .setParameter("mail", mail)
                .getSingleResult();
    }

    /**
     * User request method by filter order by asc
     */
    public List<User> findUserByFilterAsc(String resseachWord, String orderBy, EntityManager em)
    {
        return em.createNamedQuery("User.SelectUserByFilterAsc", User.class)
                .setParameter("resseachWord",resseachWord.toLowerCase())
                .setParameter("orderBy", orderBy)
                .getResultList();
    }


    /**
     * User request method by filter order by Desc
     */

    public List<User> findUserByFilterDesc(String resseachWord, String orderBy, EntityManager em)
    {
        return em.createNamedQuery("User.SelectUserByFilterDesc", User.class)
                .setParameter("resseachWord",resseachWord.toLowerCase())
                .setParameter("orderBy", orderBy)
                .getResultList();
    }


    /**
     * Add user in DB method
     */
    public User addUser(User user, EntityManager em)
    {
        em.persist(user);
        em.flush();
        return user;
    }




}

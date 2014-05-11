package com.sh.connection.persistence.jpa.pl;

import com.sh.connection.persistence.interfaces.UserDao;
import com.sh.connection.persistence.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

/**
 * Created by ievgen on 5/10/2014.
 */
@Repository
public class UserPL extends GenericPL<User> implements UserDao {

    public UserPL() {
        super(User.class);
    }

    @Override
    public User findByLogin(String login) {
        User result = null;
        try {
            result = em.createQuery("SELECT u FROM User u WHERE u.login =:login", User.class).setParameter("login", login).getSingleResult();
        } catch (NoResultException e) {
            return result;
        }
        return result;
    }

    @Override
    public User findByEmail(String email) {
        User result = null;
        try {
            result = em.createQuery("SELECT u from User u WHERE u.email =:email", User.class).setParameter("email", email
            ).getSingleResult();
        } catch (NoResultException e) {
            return result;
        }
        return result;
    }

    @Override
    public User save(User user) {
        return create(user);
    }

    @Override
    public User findOne(Long id) {
        return getById(id);
    }

    @Override
    public Iterable findAll() {
        return em.createQuery("select u from User u ").getResultList();
    }
}

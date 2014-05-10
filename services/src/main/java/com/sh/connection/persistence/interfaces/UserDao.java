package com.sh.connection.persistence.interfaces;

import com.sh.connection.persistence.model.User;

import java.util.Set;

/**
 * Created by ievgen on 5/10/2014.
 */
public interface UserDao {

    public User findByLogin(String login);
    public User findByEmail(String email);
    public User save(User user);
    public User findOne(Long id);
    public Iterable findAll();
}

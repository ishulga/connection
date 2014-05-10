package com.sh.connection.persistence.interfaces;

import com.sh.connection.persistence.model.Message;
import com.sh.connection.persistence.model.User;

import java.util.Set;

/**
 * Created by ievgen on 5/10/2014.
 */
public interface MessageDao {

    public Message save(Message user);
    public Message findOne(Long id);
    public void delete(Long id);
}

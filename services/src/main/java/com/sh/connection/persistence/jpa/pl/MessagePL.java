package com.sh.connection.persistence.jpa.pl;

import com.sh.connection.persistence.interfaces.MessageDao;
import com.sh.connection.persistence.model.Message;
import org.springframework.stereotype.Repository;

/**
 * Created by ievgen on 5/10/2014.
 */
@Repository
public class MessagePL extends GenericPL<Message> implements MessageDao {
    public MessagePL() {
        super(Message.class);
    }

    @Override
    public Message save(Message message) {
        return  create(message);
    }

    @Override
    public Message findOne(Long id) {
        return getById(id);
    }

}

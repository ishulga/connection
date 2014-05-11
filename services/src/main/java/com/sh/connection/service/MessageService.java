package com.sh.connection.service;

import com.sh.connection.persistence.interfaces.MessageDao;
import com.sh.connection.persistence.jpa.repository.MessageRepository;
import com.sh.connection.persistence.model.Message;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.sh.connection.service.Messages.EMPTY_POST_CONTENT;
import static com.sh.connection.service.Messages.EMPTY_POST_TITLE;

@Service
public class MessageService {

    @Autowired
    private MessageDao messagePL;

    public Message save(Message message) throws ServiceException {
        validatePost(message);
        Date date = new Date();
        message.setCreatedAt(date);
        message.setUpdatedAt(date);
        return messagePL.save(message);
    }

    public Message findOne(Long postId) {
        return messagePL.findOne(postId);
    }

    public void update(Message message) throws ServiceException {
        validatePost(message);
        message.setUpdatedAt(new Date());
        messagePL.save(message);
    }

    public void delete(Long messageId) {
        messagePL.delete(messageId);
    }

    private void validatePost(Message message) throws ServiceException {
        if (StringUtils.isEmpty(message.getText())) {
            throw new ServiceException(EMPTY_POST_CONTENT);
        }
        if (StringUtils.isEmpty(message.getTitle())) {
            throw new ServiceException(EMPTY_POST_TITLE);
        }
    }

}

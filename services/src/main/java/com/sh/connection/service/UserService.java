package com.sh.connection.service;

import com.sh.connection.persistence.jpa.repository.UserRepository;
import com.sh.connection.persistence.model.Message;
import com.sh.connection.persistence.model.User;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.sh.connection.service.Messages.*;

public class UserService {

    @Autowired
    private UserRepository userPL;
    @Autowired
    private MessageService messageService;


    public void sendMessage(User current, Long targetUserId, Message message) throws ServiceException {
        User targetUser = get(targetUserId);
        current.getMessages().add(message);
        current.getConversations().add(targetUser);
        update(current);
        targetUser.getMessages().add(message);
        targetUser.getConversations().add(current);
        update(targetUser);
    }

    public User save(User user) throws ServiceException {
        validate(user);
        return userPL.save(user);
    }

    public User update(User user) throws ServiceException {
        return userPL.save(user);
    }

    public User get(Long id) {
        return userPL.findOne(id);
    }

    public List<User> getAll() {
        return toList(userPL.findAll());
    }

    public List<User> toList(Iterable<User> iterable) {
        List<User> list = new ArrayList<User>();
        for (User user : iterable) {
            list.add(user);
        }
        return list;
    }

    public void addMessage(Long userId, Long messageId) {
        User user = userPL.findOne(userId);
        Message message = messageService.findOne(messageId);
        user.getMessages().add(message);
        userPL.save(user);
    }

    public User login(String login, String password) throws ServiceException {
        User user = userPL.findByLogin(login);
        if (user != null && user.getPassword().equals(password.trim())) {
            return get(user.getId());
        } else {
            throw new ServiceException(WRONG_CREDENTIALS);
        }
    }

    public void validate(User user) throws ServiceException {
        if (StringUtils.isEmpty(user.getLogin())) {
            throw new ServiceException(EMPTY_LOGIN);
        }
        if (userPL.findByLogin(user.getLogin()) != null) {
            throw new ServiceException(LOGIN_EXISTS);
        }
        if (StringUtils.isEmpty(user.getEmail())) {
            throw new ServiceException(EMPTY_EMAIL);
        }
        if (!EmailValidator.getInstance().isValid(user.getEmail())) {
            throw new ServiceException(INVALID_EMAIL);
        }
        if (userPL.findByEmail(user.getEmail()) != null) {
            throw new ServiceException(EMAIL_EXISTS);
        }
        if (StringUtils.isEmpty(user.getName())) {
            throw new ServiceException(EMPTY_NAME);
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            throw new ServiceException(EMPTY_PASSWORD);
        }
    }

}

package com.sh.connection.service;

import com.sh.connection.persistence.model.Message;
import com.sh.connection.persistence.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
//@ContextConfiguration(classes = ApplicationConfig.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MessageServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    private MessageService messageService;

    @Test
    public void testCreateValidPost() throws ServiceException {
        Message origPost = createMessage("Post content.", "Post title.");
        Message persistedPost = messageService.save(origPost);
        isMessagesEqual(origPost, persistedPost);
    }

    @Test(expected = ServiceException.class)
    public void testCreatePostWithEmptyContent() throws ServiceException {
        String postContent = "";
        String postTitle = "Post title.";
        Message post = createMessage(postContent, postTitle);
        messageService.save(post);
    }

    @Test(expected = ServiceException.class)
    public void testCreatePostWithEmptyTitle() throws ServiceException {
        String postContent = "Post with default title.";
        String postTitle = "";
        Message post = createMessage(postContent, postTitle);
        messageService.save(post);
    }

    @Test
    public void testDeletePost() throws ServiceException {
        Message post = createMessage("Post content.", "Post title.");
        Message persistedPost = messageService.save(post);
        messageService.delete(persistedPost.getId());

        assertNull(messageService.findOne(persistedPost.getId()));
    }

    @Test
    public void testUpdatePost() throws ServiceException {
        Message origPost = createMessage("Original post content.",
                "Original Post title.");

        origPost = messageService.save(origPost);
        origPost.setTitle("Updated post title.");
        origPost.setText("Updated post content.");

        messageService.save(origPost);

        Message updatedPost = messageService.findOne(origPost.getId());

        isMessagesEqual(origPost, updatedPost);
    }

    @Test(expected = ServiceException.class)
    public void testUpdatePostWithEmptyTitle() throws ServiceException {
        Message origPost = createMessage("Original post content.",
                "Original Post title.");

        origPost = messageService.save(origPost);
        origPost.setTitle("");
        origPost.setText("Updated post content.");

        messageService.save(origPost);

        Message updatedPost = messageService.findOne(origPost.getId());

        isMessagesEqual(origPost, updatedPost);
    }

    @Test
    public void testGetUnexistingPost() {
        Long postId = 6666666666L;
        assertEquals(null, messageService.findOne(postId));
    }

    @Test
    public void testAddComment() throws ServiceException {
        User user = new User();
        user.setLogin("user_with_post_and_comment_login");
        user.setName("user_with_post_and_comment_name");
        user.setPassword("user_with_post_and_comment_password");
        user.setEmail("user_with_post_and_comment_email@example.com");
        user = userService.save(user);

        Message post = createMessage("Post with comment content.",
                "Post with comment title.");
        post = messageService.save(post);
        userService.addMessage(user.getId(), post.getId());
    }

    private void isMessagesEqual(Message origPost, Message persistedPost) {
        assertEquals(origPost.getText(), persistedPost.getText());
        assertEquals(origPost.getTitle(), persistedPost.getTitle());
    }

    private Message createMessage(String text, String title) {
        Message message = new Message();
        message.setText(text);
        message.setTitle(title);
        return message;
    }
}

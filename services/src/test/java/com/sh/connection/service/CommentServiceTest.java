package com.sh.connection.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.BeforeClass;
import org.junit.Test;

import com.sh.connection.persistence.model.Comment;
import com.sh.connection.persistence.model.User;
import com.sh.connection.service.CommentService;
import com.sh.connection.service.ServiceException;
import com.sh.connection.service.UserService;
import com.sh.connection.util.ServiceFactory;

public class CommentServiceTest {

	private CommentService commentService = ServiceFactory.INSTANCE
	        .getBean(CommentService.class);

	private static User user;

	@BeforeClass
	public static void createUser() throws ServiceException {
		UserService userService = ServiceFactory.INSTANCE
		        .getBean(UserService.class);

		user = new User();
		user.setLogin("user_with_comments_login");
		user.setName("user_with_comments_name");
		user.setPassword("user_with_comments_password");
		user.setEmail("user_with_comments@example.com");
		user = userService.register(user);
	}

	@Test
	public void testCreateValidComment() throws ServiceException {
		Comment comment = createComment("Comment content.", "Comment title.");
		Long commentId = commentService.create(user.getId(), comment);

		Comment persistedComment = commentService.get(commentId);

		assertEqualComments(comment, persistedComment);
	}

	@Test(expected = ServiceException.class)
	public void testCreateCommentWithEmptyContent() throws ServiceException {
		Comment comment = createComment("", "Comment title.");
		commentService.create(user.getId(), comment);
	}

	@Test
	public void testGetUnexistingComment() {
		Long unesistingCommentId = 666666666L;
		assertNull(commentService.get(unesistingCommentId));
	}

	@Test
	public void testUpdateComment() throws ServiceException {
		Comment origComment = createComment("Comment to update content",
		        "Comment to update title");
		Long origCommentId = commentService.create(user.getId(), origComment);
		origComment = commentService.get(origCommentId);

		origComment.setComment("Updated comment comment");
		origComment.setTitle("Updated comment title");
		commentService.update(origComment);
		Comment updatedComment = commentService.get(origCommentId);

		assertEqualComments(origComment, updatedComment);
	}

	@Test
	public void testUpdateCommentWithEmptyContent() throws ServiceException {
		Comment origComment = createComment("Comment to update content",
		        "Comment to update title");
		Long origCommentId = commentService.create(user.getId(), origComment);
		origComment = commentService.get(origCommentId);

		origComment.setComment("Updated comment comment");
		origComment.setTitle("Updated comment title");
		commentService.update(origComment);
		Comment updatedComment = commentService.get(origCommentId);

		assertEqualComments(origComment, updatedComment);
	}

	@Test
	public void testDeleteComment() throws ServiceException {
		Comment comment = createComment("Comment to delete content",
		        "Comment to delete title");
		Long commentId = commentService.create(user.getId(), comment);

		commentService.delete(commentId);

		assertNull(commentService.get(commentId));
	}

	private Comment createComment(String commentContent, String commentTitle) {
		Comment comment = new Comment();
		comment.setComment(commentContent);
		comment.setTitle(commentTitle);
		return comment;
	}

	private void assertEqualComments(Comment comment, Comment persistedComment) {
		assertEquals(comment.getComment(), persistedComment.getComment());
		assertEquals(comment.getTitle(), persistedComment.getTitle());
	}
}

package com.sh.connection.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.sh.connection.persistence.model.Comment;
import com.sh.connection.persistence.model.Post;
import com.sh.connection.persistence.model.User;
import com.sh.connection.service.CommentService;
import com.sh.connection.service.PostService;
import com.sh.connection.service.ServiceException;
import com.sh.connection.service.UserService;
import com.sh.connection.util.ServiceFactory;

public class PostServiceTest {

	private PostService postService = ServiceFactory.INSTANCE
	        .getBean(PostService.class);
	private UserService userService = ServiceFactory.INSTANCE
	        .getBean(UserService.class);
	private CommentService commentService = ServiceFactory.INSTANCE
	        .getBean(CommentService.class);

	@Test
	public void testCreateValidPost() throws ServiceException {
		Post origPost = createPost("Post content.", "Post title.");
		Post persistedPost = postService.create(origPost);
		assertEqualPosts(origPost, persistedPost);
	}

	@Test(expected = ServiceException.class)
	public void testCreatePostWithEmptyContent() throws ServiceException {
		String postContent = "";
		String postTitle = "Post title.";
		Post post = createPost(postContent, postTitle);
		postService.create(post);
	}

	@Test(expected = ServiceException.class)
	public void testCreatePostWithEmptyTitle() throws ServiceException {
		String postContent = "Post with default title.";
		String postTitle = "";
		Post post = createPost(postContent, postTitle);
		postService.create(post);
	}

	@Test
	public void testDeletePost() throws ServiceException {
		Post post = createPost("Post content.", "Post title.");
		Post persistedPost = postService.create(post);
		postService.delete(persistedPost.getId());

		assertNull(postService.get(persistedPost.getId()));
	}

	@Test
	public void testUpdatePost() throws ServiceException {
		Post origPost = createPost("Original post content.",
		        "Original Post title.");

		origPost = postService.create(origPost);
		origPost.setTitle("Updated post title.");
		origPost.setPost("Updated post content.");

		postService.update(origPost);

		Post updatedPost = postService.get(origPost.getId());

		assertEqualPosts(origPost, updatedPost);
	}

	@Test(expected = ServiceException.class)
	public void testUpdatePostWithEmptyTitle() throws ServiceException {
		Post origPost = createPost("Original post content.",
		        "Original Post title.");

		origPost = postService.create(origPost);
		origPost.setTitle("");
		origPost.setPost("Updated post content.");

		postService.update(origPost);

		Post updatedPost = postService.get(origPost.getId());

		assertEqualPosts(origPost, updatedPost);
	}

	@Test
	public void testGetUnexistingPost() {
		Long postId = 6666666666L;
		assertEquals(null, postService.get(postId));
	}

	@Test
	public void testAddComment() throws ServiceException {
		User user = new User();
		user.setLogin("user_with_post_and_comment_login");
		user.setName("user_with_post_and_comment_name");
		user.setPassword("user_with_post_and_comment_password");
		user.setEmail("user_with_post_and_comment_email@example.com");
		user = userService.register(user);

		Post post = createPost("Post with comment content.",
		        "Post with comment title.");
		post = postService.create(post);
		userService.addPost(user.getId(), post.getId());

		String commentContent = "Comment content";
		Comment comment = new Comment();
		comment.setComment(commentContent);
		Long commentId = commentService.create(user.getId(), comment);

		postService.addComment(post.getId(), commentId);

		Post postWtihComment = postService.getPostWithComments(post.getId());

		comment = postWtihComment.getComments().iterator().next();
		assertEquals(1, postWtihComment.getComments().size());
		assertEquals(commentContent, comment.getComment());
		assertEquals(user.getId(), comment.getUser().getId());
	}

	private void assertEqualPosts(Post origPost, Post persistedPost) {
		assertEquals(origPost.getPost(), persistedPost.getPost());
		assertEquals(origPost.getTitle(), persistedPost.getTitle());
	}

	private Post createPost(String postContent, String postTitle) {
		Post post = new Post();
		post.setPost(postContent);
		post.setTitle(postTitle);
		return post;
	}
}

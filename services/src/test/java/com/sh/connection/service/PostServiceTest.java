package com.sh.connection.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sh.connection.ApplicationConfig;
import com.sh.connection.persistence.model.Comment;
import com.sh.connection.persistence.model.Post;
import com.sh.connection.persistence.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = ApplicationConfig.class)
public class PostServiceTest {

	@Autowired
	UserService userService;
	
	@Autowired
	private PostService postService;
	@Autowired
	private CommentService commentService;

	@Test
	public void testCreateValidPost() throws ServiceException {
		Post origPost = createPost("Post content.", "Post title.");
		Post persistedPost = postService.save(origPost);
		assertEqualPosts(origPost, persistedPost);
	}

	@Test(expected = ServiceException.class)
	public void testCreatePostWithEmptyContent() throws ServiceException {
		String postContent = "";
		String postTitle = "Post title.";
		Post post = createPost(postContent, postTitle);
		postService.save(post);
	}

	@Test(expected = ServiceException.class)
	public void testCreatePostWithEmptyTitle() throws ServiceException {
		String postContent = "Post with default title.";
		String postTitle = "";
		Post post = createPost(postContent, postTitle);
		postService.save(post);
	}

	@Test
	public void testDeletePost() throws ServiceException {
		Post post = createPost("Post content.", "Post title.");
		Post persistedPost = postService.save(post);
		postService.delete(persistedPost.getId());

		assertNull(postService.findOne(persistedPost.getId()));
	}

	@Test
	public void testUpdatePost() throws ServiceException {
		Post origPost = createPost("Original post content.",
		        "Original Post title.");

		origPost = postService.save(origPost);
		origPost.setTitle("Updated post title.");
		origPost.setPost("Updated post content.");

		postService.save(origPost);

		Post updatedPost = postService.findOne(origPost.getId());

		assertEqualPosts(origPost, updatedPost);
	}

	@Test(expected = ServiceException.class)
	public void testUpdatePostWithEmptyTitle() throws ServiceException {
		Post origPost = createPost("Original post content.",
		        "Original Post title.");

		origPost = postService.save(origPost);
		origPost.setTitle("");
		origPost.setPost("Updated post content.");

		postService.save(origPost);

		Post updatedPost = postService.findOne(origPost.getId());

		assertEqualPosts(origPost, updatedPost);
	}

	@Test
	public void testGetUnexistingPost() {
		Long postId = 6666666666L;
		assertEquals(null, postService.findOne(postId));
	}

	@Test
	public void testAddComment() throws ServiceException {
		User user = new User();
		user.setLogin("user_with_post_and_comment_login");
		user.setName("user_with_post_and_comment_name");
		user.setPassword("user_with_post_and_comment_password");
		user.setEmail("user_with_post_and_comment_email@example.com");
		user = userService.save(user);

		Post post = createPost("Post with comment content.",
		        "Post with comment title.");
		post = postService.save(post);
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

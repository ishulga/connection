package com.sh.connection.service;

import static com.sh.connection.service.Messages.EMPTY_POST_CONTENT;
import static com.sh.connection.service.Messages.EMPTY_POST_TITLE;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sh.connection.persistence.jpa.repository.PostRepository;
import com.sh.connection.persistence.model.Comment;
import com.sh.connection.persistence.model.Post;

public class PostService {

	@Autowired
	private CommentService commentService;
	@Autowired
	private PostRepository postPL;

	public Post save(Post post) throws ServiceException {
		validatePost(post);
		Date date = new Date();
		post.setCreatedAt(date);
		post.setUpdatedAt(date);
		return getPostWithComments(postPL.save(post).getId());
	}

	public Post findOne(Long postId) {
		return postPL.findOne(postId);
	}

	public Post getPostWithComments(Long id) {
		return postPL.findOne(id);
	}

	public void update(Post post) throws ServiceException {
		validatePost(post);
		post.setUpdatedAt(new Date());
		postPL.save(post);
	}

	public void delete(Long postId) {
		postPL.delete(postId);
	}

	public void addComment(Long postId, Long commentId) {
		Post post = getPostWithComments(postId);
		Comment comment = commentService.get(commentId);
		post.getComments().add(comment);
		postPL.save(post);
	}

	private void validatePost(Post post) throws ServiceException {
		if (StringUtils.isEmpty(post.getPost())) {
			throw new ServiceException(EMPTY_POST_CONTENT);
		}
		if (StringUtils.isEmpty(post.getTitle())) {
			throw new ServiceException(EMPTY_POST_TITLE);
		}
	}

	public void deleteComment(Post post, Comment comment) {
		post.getComments().remove(comment);
		commentService.delete(comment.getId());
	}
}

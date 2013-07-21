package com.sh.connection.service;

import static com.sh.connection.service.Messages.EMPTY_POST_CONTENT;
import static com.sh.connection.service.Messages.EMPTY_POST_TITLE;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sh.connection.persistence.PostPL;
import com.sh.connection.persistence.model.Comment;
import com.sh.connection.persistence.model.Post;
import com.sh.connection.util.ServiceFactory;

public class PostService {

	@Autowired
	private CommentService commentService;
	@Autowired
	private PostPL postPL;

	public Post create(Post post) throws ServiceException {
		validatePost(post);
		Date date = new Date();
		post.setCreatedAt(date);
		post.setUpdatedAt(date);
		return getPostWithComments(postPL.create(post));
	}

	public Post get(Long postId) {
		return postPL.getById(postId);
	}

	public Post getPostWithComments(Long id) {
		return postPL.getPostWithComments(id);
	}

	public void update(Post post) throws ServiceException {
		validatePost(post);
		post.setUpdatedAt(new Date());
		postPL.merge(post);
	}

	public void delete(Long postId) {
		postPL.delete(postId);
	}

	public void addComment(Long postId, Long commentId) {
		Post post = getPostWithComments(postId);
		Comment comment = commentService.get(commentId);
		post.getComments().add(comment);
		postPL.merge(post);
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

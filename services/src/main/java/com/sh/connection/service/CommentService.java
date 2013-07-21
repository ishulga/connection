package com.sh.connection.service;

import static com.sh.connection.service.Messages.EMPTY_COMMENT_CONTENT;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sh.connection.persistence.CommentPL;
import com.sh.connection.persistence.model.Comment;

public class CommentService {

	@Autowired
	private UserService userService;
	@Autowired
	private CommentPL commentPL;

	public Long create(Long userId, Comment comment) throws ServiceException {
		validateComment(comment);
		Date date = new Date();
		comment.setCreatedAt(date);
		comment.setUpdatedAt(date);
		comment.setUser(userService.get(userId));
		return commentPL.create(comment);
	}

	public Comment get(Long commentId) {
		return commentPL.getById(commentId);
	}

	public void update(Comment comment) throws ServiceException {
		validateComment(comment);
		comment.setUpdatedAt(new Date());
		commentPL.merge(comment);
	}

	public void delete(Long commentId) {
		commentPL.delete(commentId);
	}

	private void validateComment(Comment comment) throws ServiceException {
		if (StringUtils.isEmpty(comment.getComment())) {
			throw new ServiceException(EMPTY_COMMENT_CONTENT);
		}
	}
}

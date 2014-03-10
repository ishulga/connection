package com.sh.connection.persistence.jpa;

import com.sh.connection.persistence.model.Post;

public class PostPL extends GenericPL<Post> {

	public PostPL() {
		super(Post.class);
	}

	public Post getPostWithComments(Long id) {
		return (Post) getSession()
		        .createQuery(
		                "select p from Post p left join p.comments c where p.id = :id")
		        .setParameter("id", id).uniqueResult();
	}

}

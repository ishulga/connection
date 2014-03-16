package com.sh.connection.persistence.model.mongo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Post implements HasId, Serializable {

	private static final long serialVersionUID = -4494469997254040650L;

	@Id
	@GeneratedValue
	private Long id;

	@Size(max = 128)
	@NotNull
	private String title;

	@NotNull
	@Column(length = 65535)
	private String post;

	@OneToMany(cascade = CascadeType.REMOVE)
	@JoinColumn
	private Set<Comment> comments = new HashSet<Comment>();

	private Date updatedAt;

	private Date createdAt;

	public Post() {
	}

	@Override
	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getPost() {
		return post;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + ", post=" + post
		        + ", comments=" + comments + ", updatedAt=" + updatedAt
		        + ", createdAt=" + createdAt + "]";
	}
}

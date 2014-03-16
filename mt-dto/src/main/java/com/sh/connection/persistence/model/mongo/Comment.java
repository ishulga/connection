package com.sh.connection.persistence.model.mongo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Comment implements HasId, Serializable {
	private static final long serialVersionUID = 2528140579425021435L;
  
	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Column(length = 65535)
	private String comment;

	@Column(length = 128)
	private String title;

	@ManyToOne
	@JoinColumn
	private User user;

	private Date createdAt;

	private Date updatedAt;

	public Comment() {
	}

	@Override
	public Long getId() {
		return id;
	}

	public String getComment() {
		return comment;
	}

	public String getTitle() {
		return title;
	}

	public User getUser() {
		return user;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", comment=" + comment + ", title="
		        + title + ", userId=" + user.getId() + ", createdAt="
		        + createdAt + ", updatedAt=" + updatedAt + "]";
	}
}

package com.sh.connection.persistence.model.mongo;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sh.connection.persistence.model.HasId;
import com.sh.connection.persistence.model.Post;
import com.sh.connection.persistence.model.User;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "login", "email" }))
public class Customer implements HasId, Serializable {
	private static final long serialVersionUID = 8046792734615717626L;

	@Id
	@GeneratedValue
	private Long id;

	@Size(max = 128)
	private String login;

	@NotNull
	@Size(max = 128)
	private String name;

	private String password;

	@Size(max = 256)
	private String email;
	
	@OneToMany(cascade = CascadeType.REMOVE)
	@JoinColumn
	private Set<Post> posts;

	// TODO use ENUM here
	private Boolean visible = true;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", name=" + name
		        + ", password=" + password + ", email=" + email + ", posts="
		         + ", subscriptions="  + ", visible="
		        + visible + "]";
	}

	/**
	 * Overridden to use with methods of Collection interface such as remove,
	 * contains etc.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}
}
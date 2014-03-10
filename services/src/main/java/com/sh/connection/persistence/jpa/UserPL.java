package com.sh.connection.persistence.jpa;

import java.util.List;

import com.sh.connection.persistence.model.User;

public class UserPL extends GenericPL<User> {

	public UserPL() {
		super(User.class);
	}

	public User getByLogin(String userLogin) {
		return (User) getSession()
		        .createQuery("select u from User u where u.login = :login")
		        .setParameter("login", userLogin).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<User> getAll() {
		return getSession().createQuery("select u from User u").list();
	}

	public User getUserWithPosts(Long userId) {
		return (User) getSession()
		        .createQuery(
		                "select u from User u left join fetch u.posts as p left join fetch p.comments as c where u.id = :userId")
		        .setParameter("userId", userId).uniqueResult();
	}

	public User getUserWithSubscriptions(Long userId) {
		return (User) getSession()
		        .createQuery(
		                "select u from User u left join u.subscriptions where u.id = :userId")
		        .setParameter("userId", userId).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<User> getListEager() {
		return getSession()
		        .createQuery(
		                "select distinct u from User u left join fetch u.posts left join fetch u.subscriptions")
		        .list();
	}

	public User getByEmail(String userEmail) {
		return (User) getSession()
		        .createQuery("select u from User u where u.email = :email")
		        .setParameter("email", userEmail).uniqueResult();
	}

	public User getEager(Long id) {
		return (User) getSession()
		        .createQuery(
		                "from User u left join fetch u.posts left join fetch u.subscriptions where u.id = :id")
		        .setParameter("id", id).uniqueResult();
	}
}

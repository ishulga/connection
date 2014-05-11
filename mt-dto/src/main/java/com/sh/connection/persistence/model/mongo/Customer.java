package com.sh.connection.persistence.model.mongo;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document
public class Customer extends AbstractDocument {

    private String login;
    private String name;
    private String password;
    private String email;
    @DBRef
    private Set<Review> reviews;
    @DBRef
    private Review currentReview;
    private Boolean visible = true;


    public Review getCurrentReview() {
        return currentReview;
    }

    public void setCurrentReview(Review currentReview) {
        this.currentReview = currentReview;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> posts) {
        this.reviews = posts;
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
                + ", subscriptions=" + ", visible="
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

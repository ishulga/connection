package com.sh.connection.persistence.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"login", "email"}))
public class User implements HasId, Serializable {
    private static final long serialVersionUID = 8046792734615717626L;

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(max = 128)
    private String login;

    @Size(max = 128)
    private String name;

    private String password;

    @Size(max = 256)
    private String email;

    @ManyToMany
    @JoinTable(name = "user_conversation", inverseJoinColumns = {@JoinColumn(name = "converse_user_id")})
    private Set<User> conversations = new HashSet<User>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Set<Message> messages = new HashSet<Message>();

    @ManyToMany
    @JoinTable(name = "user_subscription", inverseJoinColumns = {@JoinColumn(name = "subscription_id")})
    private Set<User> subscriptions = new HashSet<User>();

    private Boolean visible = true;

    public Set<User> getConversations() {
        return conversations;
    }

    public void setConversations(Set<User> conversations) {
        this.conversations = conversations;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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

    public Set<User> getSubscriptions() {
        return subscriptions;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", conversations=" + conversations +
                ", messages=" + messages +
                '}';
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
        User other = (User) obj;
        if (login == null) {
            if (other.login != null)
                return false;
        } else if (!login.equals(other.login))
            return false;
        return true;
    }
}

package org.step.linked.step.entity;

import javax.persistence.*;

@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "description", nullable = false, length = 512)
    private String description;

    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_profile_user"))
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    public Profile() {
    }

    public Profile(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

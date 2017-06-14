package by.itransition.data.model;

import javax.persistence.*;

/**
 * Created by ilya on 6/14/17.
 */
@Entity
@Table(name = "recovery_tokens")
public class RecoveryToken {
    @Id
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "token")
    private String token;

    public RecoveryToken(User user, String token) {
        this.user = user;
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

package by.itransition.data.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by ilya on 5/26/17.
 */
//@Entity
//@Table(name = "authorities")
public class Authority implements GrantedAuthority {
    @Column(name = "role")
    private final String role;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private final User user;

    public Authority(String role, User user) {
        this.user = user;
        Assert.hasText(role, "A granted authority textual representation is required");
        this.role = role;
    }

    public String getAuthority() {
        return this.role;
    }

    public boolean equals(Object obj) {
        return this == obj || (obj instanceof Authority && this.role.equals(((Authority) obj).role));
    }

    public int hashCode() {
        return this.role.hashCode();
    }

    public String toString() {
        return this.role;
    }
}

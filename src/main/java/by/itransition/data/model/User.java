package by.itransition.data.model;

import com.google.common.collect.Lists;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Ilya Ivanov
 */
@Entity
@Table(name = "users")
@ToString(of = {"id", "email", "username", "password", "enabled", "authorities"}, doNotUseGetters = true)
@EqualsAndHashCode(of = {"id", "email", "username"})
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private Boolean enabled;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<Project> projects = new HashSet<>();

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "authorities", joinColumns = @JoinColumn(name="user_id"))
    @Column(name = "authorities")
    private List<String> authorities = new ArrayList<>();

    public User(String email, String password) {
        this(email, password, Lists.newArrayList());
    }

    public User(String email, String password, GrantedAuthority authority) {
        this(email, password, Lists.newArrayList(authority));
    }

    public User(String email, String password, List<? extends GrantedAuthority> authorities) {
        this.email = email;
        this.password = password;
        this.enabled = false;
        this.projects = new HashSet<>();
        this.authorities = new ArrayList<>();
        if (authorities != null && !authorities.isEmpty())
            this.addAllAuthorities(authorities);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void addProject(Project project) {
        this.projects.add(project);
        if (project.getOwner() != this) {
            project.setOwner(this);
        }
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities.clear();
        this.addAllAuthorities(authorities);
    }

    public <T extends GrantedAuthority> void addAuthority(T authority) {
        this.authorities.add(authority.getAuthority());
    }

    public void addAllAuthorities(List<? extends GrantedAuthority> authorities) {
        this.authorities.addAll(authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
    }
}

package by.itransition.data.model;

import com.google.common.collect.Lists;
import lombok.*;
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
//@ToString(of = {"id", "email", "username", "password", "enabled", "authorities"}, doNotUseGetters = true)
//@EqualsAndHashCode(of = {"id", "email", "username"})
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
//@Getter
//@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private Boolean enabled;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "authorities", joinColumns = @JoinColumn(name="user_id"))
    @Column(name = "authorities")
    private List<String> authorities = new ArrayList<>();

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "github_nick", unique = true)
    private String githubNick;

    @Column(name = "photo")
    private String photo;

    @ManyToMany(mappedBy = "developers")
    private Set<Project> projects = new HashSet<>();

    @ManyToMany(mappedBy = "managers")
    private List<Project> managedProjects;

    private User() {
    }

    public User(String email, String password) {
        this(email, password, Lists.newArrayList(),null,null,
                null,null,null);
    }

    public User(String email, String password, String lastName,
                String firstName, String username, String githubNick, String photo) {
        this(email, password, Lists.newArrayList(), lastName,
                firstName, username, githubNick, photo);
    }

    public User(String email, String password, GrantedAuthority authority, String lastName,
                String firstName, String username, String githubNick, String photo) {
        this(email, password, Lists.newArrayList(authority), lastName,
                firstName, username, githubNick, photo);
    }

    public User(String email, String password, List<? extends GrantedAuthority> authorities, String lastName,
                String firstName, String username, String githubNick, String photo) {
        this.email = email;
        this.password = password;
        this.enabled = true;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.githubNick = githubNick;
        this.photo = photo;
        this.authorities = new ArrayList<>();
        if (authorities != null && !authorities.isEmpty())
            this.addAllAuthority(authorities);
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
        if (!project.getManagers().contains(this)) {
            project.getManagers().add(this);
        }
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities.clear();
        this.addAllAuthority(authorities);
    }

    public <T extends GrantedAuthority> void addAuthority(T authority) {
        this.authorities.add(authority.getAuthority());
    }

    public void addAllAuthority(List<? extends GrantedAuthority> authorities) {
        this.authorities.addAll(authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGithubNick() {
        return githubNick;
    }

    public void setGithubNick(String githubNick) {
        this.githubNick = githubNick;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public List<Project> getManagedProjects() {
        return managedProjects;
    }

    public void setManagedProjects(List<Project> managedProjects) {
        this.managedProjects = managedProjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        return username != null ? username.equals(user.username) : user.username == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", githubNick='" + githubNick + '\'' +
                '}';
    }
}

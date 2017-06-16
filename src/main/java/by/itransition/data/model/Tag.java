package by.itransition.data.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @Column(name = "tag_name")
    private String tagName;

    @Column(name = "tag_weight")
    private double weight;

    @ManyToMany(mappedBy = "tags")
    private Set<Project> projects;

    public Long getId() {
        return id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    private Tag() {
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
}

package by.itransition.data.model;

import lombok.Getter;

import javax.persistence.*;

/**
 * Created by ilya on 5/29/17.
 */
@Entity
@Table(name = "projects")
@Getter
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    public void setOwner(User owner) {
        this.owner = owner;
        if (!owner.getProjects().contains(this)) {
            owner.addProject(this);
        }
    }
}

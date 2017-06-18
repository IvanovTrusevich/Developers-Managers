package by.itransition.service.elasticsearch.model;

import by.itransition.data.model.User;

public class ElasticUser implements ElasticModelInterface{

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String middleName;
    private String username;

    public ElasticUser(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.middleName = user.getMiddleName();
        this.username = user.getUsername();
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {return middleName;}

    public String getUsername() {
        return username;
    }
}

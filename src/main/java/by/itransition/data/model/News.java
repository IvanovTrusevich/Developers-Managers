package by.itransition.data.model;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    private Long id;

    @Enumerated
    @Column(name = "news_type")
    private NewsType newsType;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User userNews;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="project_id")
    private Project projectNews;

    @Column(name = "news_date")
    private Date date;

    public News(NewsType newsType, User userNews, Project projectNews, Date date) {
        this.newsType = newsType;
        this.userNews = userNews;
        this.projectNews = projectNews;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public NewsType getNewsType() {
        return newsType;
    }

    public User getUserNews() {
        return userNews;
    }

    public Project getProjectNews() {
        return projectNews;
    }

    public Date getDate() {
        return date;
    }

    public void setNewsType(NewsType newsType) {
        this.newsType = newsType;
    }

    public void setUserNews(User userNews) {
        this.userNews = userNews;
    }

    public void setProjectNews(Project projectNews) {
        this.projectNews = projectNews;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private News() {
    }
}

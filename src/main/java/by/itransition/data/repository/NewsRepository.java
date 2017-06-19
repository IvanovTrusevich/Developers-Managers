package by.itransition.data.repository;

import by.itransition.data.model.News;
import by.itransition.data.model.Project;
import by.itransition.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findByUserNews(User user);
    List<News> findByProjectNews(Project project);
}

package by.itransition.data.repository;

import by.itransition.data.model.News;
import by.itransition.data.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;


@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findByProjectNews(Project projectNews, Pageable pageable);
    List<News> find(Pageable pageable);
}

package by.itransition.data.repository;

import by.itransition.data.model.News;
import by.itransition.data.model.Project;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    Slice<News> findByProjectNews(Project projectNews, PageRequest pageRequest);
}

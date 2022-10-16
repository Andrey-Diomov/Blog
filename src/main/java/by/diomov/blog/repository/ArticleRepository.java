package by.diomov.blog.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import by.diomov.blog.model.Article;

public interface ArticleRepository extends MongoRepository<Article, String> {
  List<Article> findByTitleContaining(String title);
}

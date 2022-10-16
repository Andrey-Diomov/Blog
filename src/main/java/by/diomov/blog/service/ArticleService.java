package by.diomov.blog.service;

import by.diomov.blog.dto.ArticleDTO;

import java.util.List;

public interface ArticleService {
    ArticleDTO create(ArticleDTO articleDTO);

    List<ArticleDTO> getAll();

    ArticleDTO getById(String id);

    List<ArticleDTO> findByTitleContaining(String title);

    ArticleDTO update (String id, ArticleDTO newArticleDTO);

    void deleteById(String id);
}

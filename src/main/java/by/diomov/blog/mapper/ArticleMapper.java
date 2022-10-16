package by.diomov.blog.mapper;

import by.diomov.blog.dto.ArticleDTO;
import by.diomov.blog.model.Article;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ArticleMapper {
    private final ModelMapper mapper;

    public ArticleDTO convertToDTO(Article article) {
        return mapper.map(article, ArticleDTO.class);
    }

    public Article convertToEntity(ArticleDTO articleDTO) {
        return mapper.map(articleDTO, Article.class);
    }
}
package by.diomov.blog.service.Impl;

import by.diomov.blog.exception.ArticleNotFoundException;
import by.diomov.blog.dto.ArticleDTO;
import by.diomov.blog.model.Article;
import by.diomov.blog.repository.ArticleRepository;
import by.diomov.blog.mapper.ArticleMapper;
import by.diomov.blog.service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleMapper mapper;

    @Override
    public ArticleDTO create(ArticleDTO tutorialDTO) {
        Article article = mapper.convertToEntity(tutorialDTO);
        return mapper.convertToDTO(articleRepository.save(article));
    }

    @Override
    public List<ArticleDTO> getAll() {
        return articleRepository.findAll().stream()
                .map(mapper::convertToDTO)
                .collect(toList());
    }

    @Override
    public ArticleDTO getById(String id) {
        return mapper.convertToDTO(articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id)));
    }

    @Override
    public List<ArticleDTO> findByTitleContaining(String title) {
        List<Article> articles = articleRepository.findByTitleContaining(title);
        return articles.stream()
                .map(mapper::convertToDTO)
                .collect(toList());
    }

    @Override
    public ArticleDTO update(String id, ArticleDTO newArticleDTO) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));

        article.setTitle(newArticleDTO.getTitle());
        article.setText(newArticleDTO.getText());
        return mapper.convertToDTO(articleRepository.save(article));
    }

    @Override
    public void deleteById(String id) {
        articleRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException(id));
        articleRepository.deleteById(id);
    }
}
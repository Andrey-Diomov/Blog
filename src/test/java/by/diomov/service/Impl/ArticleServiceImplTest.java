package by.diomov.service.Impl;

import by.diomov.blog.controller.exception.ArticleNotFoundException;
import by.diomov.blog.dto.ArticleDTO;
import by.diomov.blog.mapper.ArticleMapper;
import by.diomov.blog.model.Article;
import by.diomov.blog.repository.ArticleRepository;
import by.diomov.blog.service.Impl.ArticleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ArticleServiceImplTest {
    private final ArticleRepository articleRepositoryMock = mock(ArticleRepository.class);
    private final ArticleServiceImpl articleService = new ArticleServiceImpl(articleRepositoryMock,
            new ArticleMapper(new ModelMapper()));

    private Article article;
    private ArticleDTO articleDTO;

    @BeforeEach
    public void initArticle() {
        Date date = new Date(System.currentTimeMillis());

        article = new Article();
        article.setId("myId");
        article.setTitle("Step by step");
        article.setText("Film about a miner life");
        article.setCreated(date);

        articleDTO = new ArticleDTO();
        articleDTO.setId("myId");
        articleDTO.setTitle("Step by step");
        articleDTO.setText("Film about a miner life");
        articleDTO.setCreated(date);
    }

    @Test
    public void testGetById() {
        String articleId = "myId";

        when(articleRepositoryMock.findById(articleId)).thenReturn(Optional.of(article));

        ArticleDTO actualArticleDTO = articleService.getById(articleId);

        assertEquals(articleDTO.getId(), actualArticleDTO.getId());
        assertEquals(articleDTO.getTitle(), actualArticleDTO.getTitle());
        assertEquals(articleDTO.getText(), actualArticleDTO.getText());
        assertEquals(articleDTO.getCreated(), actualArticleDTO.getCreated());
    }

    @Test
    public void testGetByIdToNonExistingId() {
        String nonExistingId = "nonExistingId";
        when(articleRepositoryMock.findById(nonExistingId)).thenReturn(Optional.empty());
        assertThrows(ArticleNotFoundException.class, () -> articleService.getById(nonExistingId));
    }

    @Test
    public void testDeleteById() {
        String articleId = "myId";
        when(articleRepositoryMock.findById(articleId)).thenReturn(Optional.of(article));
        articleService.deleteById(articleId);
        verify(articleRepositoryMock, times(1)).deleteById(articleId);
    }

    @Test
    public void testDeleteByNonExistingId() {
        String nonExistingId = "nonExistingId";
        when(articleRepositoryMock.findById(nonExistingId)).thenReturn(Optional.empty());
        assertThrows(ArticleNotFoundException.class, () -> articleService.deleteById(nonExistingId));
    }

    @Test
    public void testUpdate() {
        String Id = "myId";

        ArticleDTO newInfo = new ArticleDTO("myId", "Update title", "Update Text",
                new Date(System.currentTimeMillis()));

        when(articleRepositoryMock.findById(Id)).thenReturn(Optional.of(article));
        when(articleRepositoryMock.save(article)).thenReturn(article);

        ArticleDTO updatedInfo = articleService.update(Id, newInfo);

        assertEquals(newInfo.getId(), updatedInfo.getId());
        assertEquals(newInfo.getTitle(), updatedInfo.getTitle());
        assertEquals(newInfo.getText(), updatedInfo.getText());
    }

    @Test
    public void testUpdateByNonExistingId() {
        String nonExistingId = "nonExistingId";
        when(articleRepositoryMock.findById(nonExistingId)).thenReturn(Optional.empty());
        assertThrows(ArticleNotFoundException.class, () -> articleService.getById(nonExistingId));
    }
}
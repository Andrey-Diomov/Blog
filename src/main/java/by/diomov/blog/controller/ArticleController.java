package by.diomov.blog.controller;

import by.diomov.blog.controller.exception.WrongDataException;
import by.diomov.blog.dto.ArticleDTO;
import by.diomov.blog.service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@AllArgsConstructor
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ArticleDTO> getAll(@RequestParam(required = false) String title) {

        if (title == null) {
            return articleService.getAll();
        } else {
            return articleService.findByTitleContaining(title);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ArticleDTO getById(@PathVariable("id") String id) {
        return articleService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArticleDTO create(@RequestBody @Valid ArticleDTO articleDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new WrongDataException(bindingResult);
        }
        return articleService.create(articleDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ArticleDTO update(@PathVariable("id") String id, @RequestBody @Valid ArticleDTO articleDTO) {
        return articleService.update(id, articleDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") String id) {
        articleService.deleteById(id);
    }
}
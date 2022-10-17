package by.diomov.blog.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticleNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "articleNotFoundMessage";
    private final String id;

    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
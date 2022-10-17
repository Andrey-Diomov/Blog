package by.diomov.blog.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserNameExistsException extends RuntimeException {
    private static final String ERROR_MESSAGE = "userNameExistsMessage";
    private final String username;

    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
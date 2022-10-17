package by.diomov.blog.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserEmailExistsException extends RuntimeException{
    private static final String ERROR_MESSAGE = "userEmailExistsMessage";
    private final String userEmail;

    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}

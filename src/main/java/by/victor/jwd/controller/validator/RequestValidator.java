package by.victor.jwd.controller.validator;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public interface RequestValidator {
    String EMAIL_PATTERN = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
    String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])[a-zA-Z0-9-_!@]{7,40}$";

    boolean validate(HttpServletRequest request);

    static boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(email).matches();
    }

   static boolean isPasswordValid(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        return pattern.matcher(password).matches();
    }

}

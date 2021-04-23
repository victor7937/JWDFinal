package by.victor.jwd.controller.validator;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * Interface for validating request
 * Contains some static methods which helps to validate some request data
 */
public interface RequestValidator {
    String EMAIL_PATTERN = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
    String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])[a-zA-Z0-9-_!@]{7,40}$";
    String SIZE_PATTERN = "^[0-9]{2}(\\.[0-9])?$";
    String PRICE_PATTERN = "^[0-9]{1,4}(\\.[0-9]{1,2})?$";
    String INTEGER_PATTERN = "^[0-9]+$";
    float MIN_SIZE = 34.0f;
    float MAX_SIZE = 50.0f;

    boolean validate(HttpServletRequest request);

    static boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(email).matches();
    }

   static boolean isPasswordValid(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        return pattern.matcher(password).matches();
    }

    static boolean isSizeValid(String size) {
        Pattern pattern = Pattern.compile(SIZE_PATTERN);
        if (pattern.matcher(size).matches()) {
            float sizeF = Float.parseFloat(size);
            return sizeF > MIN_SIZE && sizeF < MAX_SIZE;
        }
        return false;
    }

    static boolean isPriceValid(String price) {
        Pattern pattern = Pattern.compile(PRICE_PATTERN);
        return pattern.matcher(price).matches();
    }

    static boolean isInteger(String num) {
        Pattern pattern = Pattern.compile(INTEGER_PATTERN);
        return pattern.matcher(num).matches();
    }

}

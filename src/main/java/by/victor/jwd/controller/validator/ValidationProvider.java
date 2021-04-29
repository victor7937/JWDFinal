package by.victor.jwd.controller.validator;

import by.victor.jwd.controller.validator.impl.FootwearValidator;
import by.victor.jwd.controller.validator.impl.PasswordValidator;
import by.victor.jwd.controller.validator.impl.ProfileValidator;
import by.victor.jwd.controller.validator.impl.RegistrationValidator;

/**
 * Provider class for getting reference to necessary validator objects
 */
public class ValidationProvider {
    private static final ValidationProvider instance = new ValidationProvider();

    private final RequestValidator registrationValidator = new RegistrationValidator();
    private final RequestValidator profileValidator = new ProfileValidator();
    private final RequestValidator footwearValidator = new FootwearValidator();
    private final RequestValidator passwordValidator = new PasswordValidator();

    private ValidationProvider() {}

    public static ValidationProvider getInstance() { return instance; }

    public RequestValidator getRegistrationValidator() { return registrationValidator; }

    public RequestValidator getProfileValidator() { return profileValidator; }

    public RequestValidator getFootwearValidator() {
        return footwearValidator;
    }

    public RequestValidator getPasswordValidator() {
        return passwordValidator;
    }
}
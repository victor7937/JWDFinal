package by.victor.jwd.controller.validator;

import by.victor.jwd.controller.validator.impl.ProfileValidator;
import by.victor.jwd.controller.validator.impl.RegistrationValidator;

public class ValidationProvider {
    private static final ValidationProvider instance = new ValidationProvider();

    private final RequestValidator registrationValidator = new RegistrationValidator();
    private final RequestValidator profileValidator = new ProfileValidator();

    private ValidationProvider() {}

    public static ValidationProvider getInstance() { return instance; }

    public RequestValidator getRegistrationValidator() { return registrationValidator; }

    public RequestValidator getProfileValidator() { return profileValidator; }
}
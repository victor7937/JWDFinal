package by.victor.jwd.controller.validator;

import by.victor.jwd.controller.validator.impl.RegistrationValidator;
import by.victor.jwd.dao.CustomerDAO;
import by.victor.jwd.dao.DAOProvider;
import by.victor.jwd.dao.impl.SQLCustomerDAO;

public class ValidationProvider {
    private static final ValidationProvider instance = new ValidationProvider();

    private final RequestValidator registrationValidator = new RegistrationValidator();

    private ValidationProvider() {}

    public static ValidationProvider getInstance() { return instance; }

    public RequestValidator getRegistrationValidator() { return registrationValidator; }
}
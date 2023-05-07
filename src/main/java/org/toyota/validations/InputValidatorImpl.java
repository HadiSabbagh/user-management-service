package org.toyota.validations;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.toyota.domain.user.User;
import org.toyota.dto.UserDTO;
import org.toyota.mapper.UserDTOConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * This class is responsible for checking input violations before accessing the database.
 */
@Component
public class InputValidatorImpl implements InputValidator
{
    @Autowired
    Validator validator;

    @Autowired
    UserDTOConverter userConverter;


    private final Logger logger = LogManager.getLogger(InputValidatorImpl.class);

    /**
     * @param userDTO This method takes the userDTO and calls UserDTOConverter to get User out of it.
     * Then it calls Generic Validator to get Constraint Violations set from User
     * @return List of strings violations messages.
     */
    @Override
    public List<String> validateUserInput(UserDTO userDTO)
    {
        logger.info("Validating UserDTO");
        User user = userConverter.DTO_To_User(userDTO);
        Set<ConstraintViolation<User>> violationSet = validator.validate(user);
        List<String> violationsMsg = new ArrayList<>();
        violationSet.forEach(userConstraintViolation ->
                                     violationsMsg.add(userConstraintViolation.getPropertyPath() + ": " + userConstraintViolation.getMessage()));
        return violationsMsg;
    }


}

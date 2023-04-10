package org.toyota.validations;

import jakarta.validation.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.toyota.dao.UserRepository;
import org.toyota.domain.user.ERoles;
import org.toyota.domain.user.User;
import org.toyota.mapper.UserDTOConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * This class is responsible for checking constraint violations when accessing the database.
 */
@Component
public class DatabaseValidatorImpl implements DatabaseValidator
{

    @Autowired
    Validator validator;

    @Autowired
    UserDTOConverter userConverter;

    @Autowired
    UserRepository UserRepository;


    @Autowired
    UserRoleValidator roleValidator;

    private final Logger logger = LogManager.getLogger(DatabaseValidatorImpl.class);


    @Override
    public List<String> validateUser(User user)
    {
        List<String> violationsMsg = new ArrayList<>();
        logger.info("Validating User");
        if (UserRepository.existsByUsername(user.getUsername()))
        {
            violationsMsg.add("Error: username is already taken");
        }
        if (UserRepository.existsByEmail(user.getEmail()))
        {
            violationsMsg.add("Error: email is already taken");
        } else
        {
            Set<ERoles> roles = roleValidator.autoFillRolesIfEmpty(user);
            return violationsMsg;
        }
        return violationsMsg;
    }


}

package org.toyota.validations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.toyota.domain.user.ERoles;
import org.toyota.domain.user.User;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserRoleValidatorImpl implements UserRoleValidator
{
    private final Logger logger = LogManager.getLogger(DatabaseValidatorImpl.class);

    @Override
    public Set<ERoles> autoFillRolesIfEmpty(User user)
    {
        Set<ERoles> strRoles = user.getRoles();
        Set<ERoles> roles = new HashSet<>();

        if (strRoles == null) // default role
        {
            logger.warn("Empty roles when saving user with username: " + user.getUsername() + ". Default role assigned.");
            roles.add(ERoles.ROLE_OPERATOR);
        } else
        {
            roles = strRoles;
        }
        return roles;
    }
}

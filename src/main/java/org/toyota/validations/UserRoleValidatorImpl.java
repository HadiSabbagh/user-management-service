package org.toyota.validations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.toyota.domain.user.ERoles;
import org.toyota.domain.user.User;

import java.util.HashSet;
import java.util.Set;

/**
 * This class is responsible for assigning the OPERATOR role when the user does not specify a role.
 */
@Component
public class UserRoleValidatorImpl implements UserRoleValidator
{
    private final Logger logger = LogManager.getLogger(DatabaseValidatorImpl.class);

    /**
     * @param user //First get the user roles and check if they are null or empty.
     * @return //Must return a set of roles with the OPERATOR role in the set.
     */
    @Override
    public Set<ERoles> autoFillRolesIfEmpty(User user)
    {
        Set<ERoles> strRoles = user.getRoles();
        Set<ERoles> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) // default role
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

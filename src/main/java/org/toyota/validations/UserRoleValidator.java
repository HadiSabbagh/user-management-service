package org.toyota.validations;

import org.toyota.domain.user.ERoles;
import org.toyota.domain.user.User;

import java.util.Set;

public interface UserRoleValidator
{
    Set<ERoles> autoFillRolesIfEmpty(User user);

}

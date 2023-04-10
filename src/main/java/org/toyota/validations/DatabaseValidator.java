package org.toyota.validations;

import org.toyota.domain.user.User;

import java.util.List;

public interface DatabaseValidator
{
    List<String> validateUser(User user);

}

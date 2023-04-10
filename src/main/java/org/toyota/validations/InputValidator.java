package org.toyota.validations;

import org.toyota.dto.UserDTO;

import java.util.List;

public interface InputValidator
{
    List<String> validateUserInput(UserDTO userDTO);


}

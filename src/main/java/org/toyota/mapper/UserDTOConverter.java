package org.toyota.mapper;


import org.springframework.stereotype.Component;
import org.toyota.domain.user.User;
import org.toyota.dto.UserDTO;

import java.util.List;
import java.util.stream.Collectors;


/**
 * This class is responsible for converting back and forth between the User and UserDTO.
 */
@Component
public class UserDTOConverter
{
    public UserDTO user_To_DTO(User user)
    {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getActive(),
                user.getRoles()


        );
    }

    public User DTO_To_User(UserDTO userDTO)
    {

        return new User(
                userDTO.getId(),
                userDTO.getName(),
                userDTO.getUsername(),
                userDTO.getEmail(),
                userDTO.getPassword(),
                userDTO.getActive(),
                userDTO.getRoles()
        );
    }


    public List<UserDTO> users_To_DTOs(List<User> users)
    {
        return users.stream().map(this::user_To_DTO).collect(Collectors.toList());
    }

    public List<User> DTOs_To_Users(List<UserDTO> userDTOS)
    {
        return userDTOS.stream().map(this::DTO_To_User).collect(Collectors.toList());
    }
}

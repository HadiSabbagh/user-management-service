package org.toyota.services;

import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.toyota.mapper.MessageResponse;
import org.toyota.dao.UserRepository;
import org.toyota.domain.user.User;
import org.toyota.dto.UserDTO;
import org.toyota.mapper.UserDTOConverter;
import org.toyota.operationResult.DatabaseOpResult;
import org.toyota.validations.DatabaseValidator;
import org.toyota.validations.InputValidatorImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * This service is responsible for crud operations.
 */
@Service
@Transactional
public class UserManagementImpl implements UserManagement
{

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDTOConverter userConverter;

    @Autowired
    DatabaseValidator databaseValidator;


    PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    InputValidatorImpl inputValidator;

    private final Logger logger = LogManager.getLogger(UserManagementImpl.class);


    public UserManagementImpl(UserRepository userRepository, UserDTOConverter userConverter)
    {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }


    @Override
    public Optional<UserDTO> findByEmail(String email)
    {
        UserDTO userDto = userConverter.user_To_DTO(userRepository.findByEmail(email).get());
        return Optional.ofNullable(userDto);
    }

    @Override
    public Optional<UserDTO> findByUsernameOrEmail(String username, String email)
    {
        UserDTO userDto = userConverter.user_To_DTO(userRepository.findByUsernameOrEmail(username, email).get());
        return Optional.ofNullable(userDto);
    }

    @Override
    public List<UserDTO> findAll()
    {
        List<User> users = userRepository.findByActiveTrue();

        return userConverter.users_To_DTOs(users);
    }

    @Override
    public List<UserDTO> findByIdIn(List<Long> userIds)
    {
        return userConverter.users_To_DTOs(userRepository.findByIdIn(userIds));
    }

    @Override
    public Optional<UserDTO> findByUsername(String username)
    {
        UserDTO userDto = userConverter.user_To_DTO(userRepository.findByUsername(username).get());
        return Optional.ofNullable(userDto);
    }

    @Override
    public Boolean existsByUsername(String username)
    {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email)
    {
        return userRepository.existsByEmail(email);
    }


    @Override
    public DatabaseOpResult save(UserDTO userDTO)
    {
        List<String> inputViolationsMsg = inputValidator.validateUserInput(userDTO);
        List<String> databaseViolationsMsg = databaseValidator.validateUser(userConverter.DTO_To_User(userDTO));
        if (!inputViolationsMsg.isEmpty())
        {
            return new DatabaseOpResult(new MessageResponse("Input violations found when saving user: "), inputViolationsMsg.stream().toList());
        }
        if (!databaseViolationsMsg.isEmpty())
        {
            return new DatabaseOpResult(new MessageResponse("Violations found when saving user: "), databaseViolationsMsg.stream().toList());
        } else
        {
            userDTO.setPassword(encoder.encode(userDTO.getPassword()));
            User user = userConverter.DTO_To_User(userDTO);
            userRepository.save(user);
            userRepository.flush();
            logger.info("user with username " + user.getUsername() + " saved successfully");
            return new DatabaseOpResult(new MessageResponse("User registered."), null);
        }
    }


    @Override
    public DatabaseOpResult delete(Long userId)
    {
        List<String> errorMsg = new ArrayList<>();
        if (userRepository.existsById(userId))
        {
            User user = userRepository.findById(userId).get();
            if (!user.getActive())
            {
                logger.info("Deactivating a user that is already not active.");
                errorMsg.add("Deactivating a user that is already not active.");
                return new DatabaseOpResult(new MessageResponse("Error: "), errorMsg);

            }
            user.setActive(false);
            userRepository.flush();
            logger.info("User with username " + user.getUsername() + " deactivation successful");
            return new DatabaseOpResult(new MessageResponse("Deactivation successful"), null);

        } else
        {
            logger.warn("User deactivation failed. User does not exist");
            errorMsg.add("User does not exist");
            return new DatabaseOpResult(new MessageResponse("Error: "), errorMsg);
        }
    }

    @Override
    public DatabaseOpResult update(UserDTO userDTO)
    {
        List<String> inputViolationsMsg = inputValidator.validateUserInput(userDTO);
        List<String> databaseViolationsMsg = databaseValidator.validateUser(userConverter.DTO_To_User(userDTO));
        if (!inputViolationsMsg.isEmpty())
        {
            return new DatabaseOpResult(new MessageResponse("Input violations found when saving user: "), inputViolationsMsg.stream().toList());

        }
        if (!databaseViolationsMsg.isEmpty())
        {
            return new DatabaseOpResult(new MessageResponse("Violations found when saving user: "), databaseViolationsMsg.stream().toList());

        } else
        {
            userDTO.setPassword(encoder.encode(userDTO.getPassword()));
            User user = userConverter.DTO_To_User(userDTO);
            userRepository.save(user);
            userRepository.flush();
            logger.info("user " + user.getUsername() + " updated successfully");
            return new DatabaseOpResult(new MessageResponse("User updated."), null);
        }
    }
}

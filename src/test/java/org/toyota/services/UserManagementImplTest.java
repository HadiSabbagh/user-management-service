package org.toyota.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.toyota.dto.UserDTO;
import org.toyota.mapper.UserDTOConverter;
import org.toyota.validations.DatabaseValidatorImpl;
import org.toyota.validations.InputValidatorImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class is responsible for testing some of UserManagement Service methods.
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserManagementImplTest
{

    @Autowired
    UserManagementImpl userManagement;

    @Autowired
    UserDTOConverter userConverter;

    @Autowired
    DatabaseValidatorImpl databaseValidator;

    @Autowired
    InputValidatorImpl inputValidator;

    List<String> result;
    List<String> expectedResult;

    @BeforeEach
    void setUp()
    {
        result = new ArrayList<>();
        expectedResult = new ArrayList<>();
    }

    @AfterEach
    void tearDown()
    {
        result = null;
        expectedResult = null;
    }


    /**
     * Test a non-existing email.
     */
    @Test
    void findEmptyUserByEmail()
    {
        Optional<UserDTO> user = userManagement.findByEmail("email@outlook.com");
        assertThat(user).isEmpty();

    }

    /**
     * Test non-existing  username and email
     */
    @Test
    void findEmptyUserByUsernameOrEmail()
    {
        Optional<UserDTO> user = userManagement.findByUsernameOrEmail("username", "email@outlook.com");
        assertThat(user).isEmpty();
    }

    /**
     * Test empty input
     */
    @Test
    void findEmptyUserByUsername()
    {
        Optional<UserDTO> user = userManagement.findByUsername("");
        assertThat(user).isEmpty();
    }


    @Test
    void doesNotExistByUsername()
    {
        Boolean exists = userManagement.existsByUsername("DoesNotExist");
        assertThat(exists).isFalse();
    }

    /**
     * Test if a user exists by email
     */
    @Test
    void doesExistByEmail()
    {
        Boolean exists = userManagement.existsByEmail("hadi_sabbagh02@outlook.com");
        assertThat(exists).isTrue();
    }

    /**
     * Attempt to delete a non-existing user
     */
    @Test
    void deleteNonExistingUser()
    {
        List<String> result = userManagement.delete(100000L).getErrorMessages();
        assertThat(result).contains("User does not exist");
    }

    /**
     * Attempt to update a non-existing user
     */
    @Test
    void updateNonExistingUser()
    {
        result = userManagement.update(new UserDTO()).getErrorMessages();
        expectedResult.add("username: Cannot be empty.");
        expectedResult.add("name: Cannot be empty.");
        expectedResult.add("name: Cannot be null.");
        expectedResult.add("email: Cannot be empty.");
        expectedResult.add("username: Cannot be null.");
        expectedResult.add("email: Cannot be null.");
        expectedResult.add("password: Cannot be empty.");
        expectedResult.add("password: Cannot be null.");
        expectedResult.add("active: Cannot be null.");
        assertThat(result).hasSameElementsAs(expectedResult);
    }

}
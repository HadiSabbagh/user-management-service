package org.toyota.validations;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.toyota.domain.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class is responsible for testing whether DatabaseValidator is working by testing
 * whether a username or an email is already taken.
 * Thus, a database with the username and email to be tested must exist.
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
class DatabaseValidatorImplTest
{
    @Autowired
    DatabaseValidatorImpl databaseValidator;

    List<String> violationsMsgs;
    List<String> expectedMsgs;

    @BeforeEach
    void setUp()
    {
        violationsMsgs = new ArrayList<>();
        expectedMsgs = new ArrayList<>();
    }

    @AfterEach
    void tearDown()
    {
        violationsMsgs = null;
        expectedMsgs = null;
    }

    /**
     * @param user  Test each user
     * @param message Error Message that is expected from the validateUserInput method in DatabaseValidator class.
     */
    @ParameterizedTest
    @MethodSource("valuesGenerator")
    void testAlreadyTakenUsernameOrEmail(User user, String message)
    {
        violationsMsgs = databaseValidator.validateUser(user);
        expectedMsgs.add(message);
        assertThat(violationsMsgs).isEqualTo(expectedMsgs);
    }


    /**
     * An argument generator to test multiple parameters.
     * @return The User and the expected Error Message
     */
    private static Stream<Arguments> valuesGenerator()
    {
        User userWithUsername = new User();
        userWithUsername.setUsername("hadisab");

        User userWithEmail = new User();
        userWithEmail.setEmail("hadi_sabbagh02@outlook.com");

        return Stream.of(
                Arguments.of(userWithUsername, "Error: username is already taken"),
                Arguments.of(userWithEmail, "Error: email is already taken")
        );
    }
}
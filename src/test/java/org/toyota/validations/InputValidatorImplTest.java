package org.toyota.validations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.toyota.domain.user.ERoles;
import org.toyota.dto.UserDTO;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * This test checks whether  @NotNull and @NotEmpty annotations in User entity are working properly.
 * To understand how the expected message strings came to be, check User entity annotations and take a look at validateUserInput method.
 * {@link InputValidatorImpl#validateUserInput(UserDTO)}  validateUserInput}
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
class InputValidatorImplTest
{
    @Autowired
    InputValidatorImpl inputValidator;

    UserDTO userDTO;
    List<String> violationsMsgs;


    @BeforeEach
    void setUp()
    {
        violationsMsgs = new ArrayList<>();
    }

    @ParameterizedTest
    @MethodSource("valuesGenerator")
    void testValidInput(UserDTO userDTO, List<String> expectedMsgs)
    {
        violationsMsgs = inputValidator.validateUserInput(userDTO);
        assertThat(violationsMsgs).hasSameElementsAs(expectedMsgs);
    }

    /**
     * An argument generator to test multiple parameters.
     * @return The UserDTO and a list of expected messages.
     */
    private static Stream<Arguments> valuesGenerator()
    {
        // These messages are expected when a new UserDTO is created with no entries.
        List<String> nullOrEmptyExpectedMessages = new ArrayList<>();
        nullOrEmptyExpectedMessages.add("username: Cannot be empty.");
        nullOrEmptyExpectedMessages.add("name: Cannot be empty.");
        nullOrEmptyExpectedMessages.add("name: Cannot be null.");
        nullOrEmptyExpectedMessages.add("email: Cannot be empty.");
        nullOrEmptyExpectedMessages.add("username: Cannot be null.");
        nullOrEmptyExpectedMessages.add("email: Cannot be null.");
        nullOrEmptyExpectedMessages.add("password: Cannot be empty.");
        nullOrEmptyExpectedMessages.add("password: Cannot be null.");
        nullOrEmptyExpectedMessages.add("active: Cannot be null.");

        Set<ERoles> roles = new HashSet<>();
        roles.add(ERoles.ROLE_ADMIN);
        //Valid UserDTO that should not contain any violation messages.
        UserDTO correctUserDTO = new UserDTO(100L, "username",
                "name",
                "email@outlook.com",
                "password",
                true,
                roles);
        //invalid UserDTO because email property is not in the correct format.
        UserDTO badEmailUserDTO = new UserDTO(100L, "username",
                "name",
                "email",
                "password",
                true,
                roles);
        return Stream.of(
                Arguments.of(correctUserDTO, Collections.emptyList()), // perfect UserDTO
                Arguments.of(new UserDTO(),nullOrEmptyExpectedMessages), // Null or Empty UserDTO
                Arguments.of(badEmailUserDTO, Collections.singletonList("email: must be a well-formed email address")) // Bad Email UserDTO
        );

    }
}
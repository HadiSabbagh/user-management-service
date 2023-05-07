package org.toyota.validations;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.toyota.domain.user.ERoles;
import org.toyota.domain.user.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class is responsible for testing whether UserRoleValidator is working correctly by assigning an OPERATOR role.
 * Then asserting that the user has the OPERATOR role.
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserRoleValidatorImplTest
{
    @Autowired
    UserRoleValidatorImpl userRoleValidator;

    User user;
    List<String> violationsMsgs;
    List<String> expectedMsgs;


    @BeforeEach
    void setUp()
    {
        violationsMsgs = new ArrayList<>();
        expectedMsgs = new ArrayList<>();
        user = new User();
    }

    @AfterEach
    void tearDown()
    {
        violationsMsgs = null;
        expectedMsgs = null;
        user = null;
    }


    @Test
    void autoFillRolesIfEmpty()
    {
        user.setRoles(userRoleValidator.autoFillRolesIfEmpty(user)); //Set the roles from the method. This should give the user an OPERATOR role.

        //Create a set of roles and add the OPERATOR role.
        Set<ERoles> roles = new HashSet<>();
        roles.add(ERoles.ROLE_OPERATOR);

        assertThat(roles).isEqualTo(user.getRoles());
    }
}
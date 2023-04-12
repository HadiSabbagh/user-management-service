package org.toyota.resources;

import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.toyota.dto.UserDTO;
import org.toyota.mapper.ResponseMapper;
import org.toyota.services.UserManagement;


/**
 * This controller is responsible for the UserManagementService Access.
 * Only a User with an Admin role can access the methods.
 */
@Validated
@RestController
@RequestMapping("/user-management")
@PreAuthorize("hasRole('ADMIN')")
public class UserManagementController
{

    @Autowired
    UserManagement userManagementService;


    private final Logger logger = LogManager.getLogger(UserManagementController.class);


    @GetMapping("/list-users")
    public ResponseEntity<?> listUsers(@RequestHeader("Authorization") String token)
    {
        return new ResponseMapper().ResponseList(userManagementService.findAll());
    }

    @DeleteMapping("/deactivate-user/{id}")
    public ResponseEntity<?> deactivateUser(@PathVariable("id") Long userId)
    {
        return new ResponseMapper().MapResponse(userManagementService.delete(userId));
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long userId, @Valid @RequestBody UserDTO userDTO)
    {
        userDTO.setId(userId);
        return new ResponseMapper().MapResponse(userManagementService.update(userDTO));

    }
}

package org.toyota.services;

import org.springframework.stereotype.Repository;
import org.toyota.dto.UserDTO;
import org.toyota.operationResult.DatabaseOpResult;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserManagement
{
    Optional<UserDTO> findByEmail(String email);
    Optional<UserDTO> findByUsernameOrEmail(String username, String email);
    List<UserDTO> findByIdIn(List<Long> userIds);
    Optional<UserDTO> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    List<UserDTO> findAll();
    DatabaseOpResult save(UserDTO userDTO);
    DatabaseOpResult delete(Long userId);
    DatabaseOpResult update(UserDTO userDTO);


}

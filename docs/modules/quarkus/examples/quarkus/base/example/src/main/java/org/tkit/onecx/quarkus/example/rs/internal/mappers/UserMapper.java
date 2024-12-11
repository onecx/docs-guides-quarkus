package org.tkit.onecx.quarkus.example.rs.internal.mappers;

import jakarta.enterprise.context.ApplicationScoped;

import org.tkit.onecx.quarkus.example.domain.models.User;
import org.tkit.onecx.quarkus.example.rs.internal.models.CreateUserDTO;
import org.tkit.onecx.quarkus.example.rs.internal.models.UserDTO;

/**
 * Maps DTO to internal object (entities) vica versa
 */
@ApplicationScoped
public class UserMapper {

    public User toUser(final UserDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setId(dto.getId());
        return user;
    }

    public User createUser(final CreateUserDTO dto) {
        User user = new User();
        if (dto.getId() != null) {
            user.setId(dto.getId());
        }
        user.setName(dto.getName());
        return user;
    }

    public UserDTO toUserDTO(final User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        return dto;
    }
}

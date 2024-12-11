package org.tkit.onecx.quarkus.example.domain.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.tkit.onecx.quarkus.example.domain.daos.UserDAO;
import org.tkit.onecx.quarkus.example.domain.models.User;

/**
 * Complex business logic
 */
@ApplicationScoped
public class UserValidationService {

    @Inject
    UserDAO dao;

    public boolean isValid(User user) {
        var tmp = dao.getUserById(user.getId());
        return user.getName().equals(tmp.getName());
    }
}

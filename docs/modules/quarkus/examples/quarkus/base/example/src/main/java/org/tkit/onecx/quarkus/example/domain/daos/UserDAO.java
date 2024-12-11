package org.tkit.onecx.quarkus.example.domain.daos;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import jakarta.transaction.Transactional;
import org.tkit.onecx.quarkus.example.domain.models.User;

/**
 * Read and write data to data-source (database)
 */
@ApplicationScoped
public class UserDAO {

    @Inject
    EntityManager em;

    public User getUserById(String id) {
        return em.find(User.class, id);
    }

    @Transactional
    public User createUser(User user) {
        em.persist(user);
        return user;
    }

}

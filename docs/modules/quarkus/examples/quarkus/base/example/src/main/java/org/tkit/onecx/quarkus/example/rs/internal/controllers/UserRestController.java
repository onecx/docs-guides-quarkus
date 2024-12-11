package org.tkit.onecx.quarkus.example.rs.internal.controllers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import org.tkit.onecx.quarkus.example.domain.daos.UserDAO;
import org.tkit.onecx.quarkus.example.domain.services.UserValidationService;
import org.tkit.onecx.quarkus.example.rs.internal.mappers.UserMapper;
import org.tkit.onecx.quarkus.example.rs.internal.models.CreateUserDTO;
import org.tkit.onecx.quarkus.example.rs.internal.models.UserDTO;

/**
 * Rest-endpoint for user API
 */
@ApplicationScoped
@Transactional(value = Transactional.TxType.NOT_SUPPORTED)
@Path("internal/user")
public class UserRestController {

    @Inject
    UserDAO userDAO;

    @Inject
    UserMapper userMapper;

    @Inject
    UserValidationService userValidationService;

    @POST
    public Response createUser(CreateUserDTO dto) {
        var user = userMapper.createUser(dto);
        user = userDAO.createUser(user);
        return Response.ok(userMapper.toUserDTO(user)).build();
    }

    @GET
    @Path("{id}")
    public Response getUser(@PathParam("id") String id) {
        var user = userDAO.getUserById(id);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(userMapper.toUserDTO(user)).build();
    }

    @POST
    @Path("validate")
    public Response validateUser(UserDTO dto) {
        var user = userMapper.toUser(dto);
        var result = userValidationService.isValid(user);
        if (result) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}

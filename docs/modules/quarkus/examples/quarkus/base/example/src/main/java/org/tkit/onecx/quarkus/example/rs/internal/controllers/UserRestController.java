package quarkus.base.example.src.main.java.org.tkit.onecx.quarkus.example.rs.internal.controllers;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/**
 * Rest-endpoint for user API
 */
@ApplicationScoped
@Transactional(value = Transactional.TxType.NOT_SUPPORTED)
@Path("internal/user")
public class UserRestController {

    @GET
    public Response getUser() {
        return Response.ok().build();
    }
}

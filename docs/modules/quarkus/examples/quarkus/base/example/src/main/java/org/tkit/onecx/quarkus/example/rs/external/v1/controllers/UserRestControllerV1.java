package quarkus.base.example.src.main.java.org.tkit.onecx.quarkus.example.rs.external.v1.controllers;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.tkit.quarkus.log.cdi.LogService;

/**
 * Rest-endpoint for user external V1 API
 */
@ApplicationScoped
@Transactional(value = Transactional.TxType.NOT_SUPPORTED)
@LogService
@Path("v1/user")
public class UserRestControllerV1 {

    @GET
    public Response getUser() {
        return Response.ok().build();
    }
}

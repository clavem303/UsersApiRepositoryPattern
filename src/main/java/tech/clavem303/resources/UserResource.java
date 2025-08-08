package tech.clavem303.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import tech.clavem303.DTOs.UserDTO;
import tech.clavem303.services.UserService;

import java.util.List;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    @GET
    public List<UserDTO> getUsers() {
        return userService.getAllUsers();
    }

    @GET
    @Path("/{id}")
    public UserDTO getUserById(@PathParam("id") Long id) {
        return userService.getUserById(id);
    }

    @GET
    @Path("/email")
    public UserDTO getUserByEmail(@QueryParam("email") String email) {
        return userService.getUserByEmail(email);
    }

    @POST
    public UserDTO createUser(UserDTO userDTO) {
        return userService.createUser(userDTO);
    }
}

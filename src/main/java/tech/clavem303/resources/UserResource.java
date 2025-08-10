package tech.clavem303.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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
    public Response getUserById(@PathParam("id") Long id) {
        UserDTO userById = userService.getUserById(id);

        if (userById == null) return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(userById).build();
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

    @PATCH
    @Path("/{id}")
    public Response partiallyUpdateUser(@PathParam("id") Long id, UserDTO userDTO) {
        UserDTO updatedUser = userService.patchUser(id, userDTO);

        if (updatedUser == null) return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(updatedUser).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        boolean deleted = userService.deleteUser(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }


}

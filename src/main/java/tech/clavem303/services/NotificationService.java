package tech.clavem303.services;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import tech.clavem303.DTOs.NotificationDTO;

// Sem URI use a linha quarkus.rest-client."tech.clavem303.services.NotificationService".url=http://localhost:9999
@RegisterRestClient(baseUri = "http://localhost:9999")
@Path("/notifications")
public interface NotificationService {
    @POST
    void sendNotification(NotificationDTO notification);
}

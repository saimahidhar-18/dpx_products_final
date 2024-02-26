package com.example.Filters;



import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.example.resources.CredentialResource;
import com.example.services.CredentialServices;

import java.io.IOException;
import java.util.List;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationFilter implements ContainerRequestFilter {

    CredentialServices credentialService = CredentialResource.credentialServices; 

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();
        String method = requestContext.getMethod();

        String[] segments = path.split("/");

        if (segments.length > 0 && "login".equals(segments[segments.length - 1])) {
            return;
        }
        
        // String username = requestContext.getHeaders().get("Username ").get(0); 
        List<String> usernameHeader = requestContext.getHeaders().get("Username");
        String username = ( usernameHeader != null && !usernameHeader.isEmpty() ) ? usernameHeader.get(0) : null;

        
        if (username==null || !credentialService.isUserAuthorized(username)) {

            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity("{\"error\": \"User is not authorized\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build());
        }


        String userRole = (username != null) ? credentialService.getUserRole(username) : null;

        

        if ( userRole == null || ("consumer".equals(userRole) && credentialService.isRestrictedMethod(method))) {
    
            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity("{\"error\": \"Consumer is not authorized to perform this action\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build());
            return;
        }
        

        return;

    }
}

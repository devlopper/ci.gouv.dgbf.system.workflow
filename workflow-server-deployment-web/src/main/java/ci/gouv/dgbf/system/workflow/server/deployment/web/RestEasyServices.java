package ci.gouv.dgbf.system.workflow.server.deployment.web;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationPath("/")
@Path("/")
@ApplicationScoped
public class RestEasyServices extends Application {

	public RestEasyServices() {
	
	}
	
    @GET
    @Path("/")
    @Produces({ MediaType.TEXT_PLAIN })
    public Response get() {
    	return Response.status(Response.Status.OK).entity("Hello!!! "+System.currentTimeMillis()).build();
    }
    
}
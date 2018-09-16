package ci.gouv.dgbf.system.workflow.client.deployment.standalone;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@ApplicationPath("/rest")
@Path("/rest")
@ApplicationScoped
public class Application extends javax.ws.rs.core.Application {

	@GET
	@Path("/")
	@Produces(javax.ws.rs.core.MediaType.TEXT_PLAIN)
	public String get() {
		//TODO this message could come from database
		return "Your Swarm Client API is running. Time is "+new java.util.Date();
	}
	
	
}
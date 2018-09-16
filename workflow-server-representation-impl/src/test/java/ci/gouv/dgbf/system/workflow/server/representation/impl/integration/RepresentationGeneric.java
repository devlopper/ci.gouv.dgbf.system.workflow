package ci.gouv.dgbf.system.workflow.server.representation.impl.integration;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public interface RepresentationGeneric<T> {

	@GET
	@Path("/time")
	@Produces({ MediaType.TEXT_PLAIN })
	Response getTime(); 
	
	@GET
	@Path("/many")
	@Produces({ MediaType.APPLICATION_XML })
	Response getCollection(); 
	
	@GET
	@Path("/one")
	@Produces({ MediaType.APPLICATION_XML })
	Response getOne(); 
	
}

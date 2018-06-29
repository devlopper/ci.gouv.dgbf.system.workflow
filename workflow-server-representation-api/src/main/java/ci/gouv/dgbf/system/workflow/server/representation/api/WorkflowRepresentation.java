package ci.gouv.dgbf.system.workflow.server.representation.api;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowDto;

@Path(WorkflowRepresentation.PATH)
public interface WorkflowRepresentation extends PersistenceEntityRepresentation<WorkflowDto> {

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	Response createOne(WorkflowDto dto);

	@GET
	@Path("/get/one")
	@Produces({ MediaType.APPLICATION_XML })
	WorkflowDto getByCode(@QueryParam("code") String code);

	@GET
	@Path("/get/all")
	@Produces({ MediaType.APPLICATION_XML })
	Collection<WorkflowDto> getAll();

	@GET
	@Path("/count/all")
	@Produces({ MediaType.TEXT_PLAIN })
	Long countAll();
	
	/**/

	String PATH = "/workflow";
}
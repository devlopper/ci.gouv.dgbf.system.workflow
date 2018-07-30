package ci.gouv.dgbf.system.workflow.server.representation.api;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowDto;

@Path(WorkflowRepresentation.PATH)
public interface WorkflowRepresentation extends PersistenceEntityRepresentation<WorkflowDto> {

	@GET
	@Path(PATH+"get/code/{code}")
	@Produces({ MediaType.APPLICATION_XML })
	WorkflowDto getByCode(@PathParam("code") String code);

	@GET
	@Path(PATH+"delete/code/{code}")
	@Produces({ MediaType.APPLICATION_XML })
	Response deleteByCode(@PathParam("code") String code);
	
	/*
	 * Redeclarations
	 */
	
	@GET
	@Path(PATH+"get/identifier/{identifier}")
	@Produces({ MediaType.APPLICATION_XML })
	WorkflowDto getByIdentifier(@PathParam("identifier") Long identifier);
	
	@GET
	@Path(PATH+"get/all")
	@Produces({ MediaType.APPLICATION_XML })
	Collection<WorkflowDto> getAll();
	
	/**/

	String PATH = "/workflow/";
}
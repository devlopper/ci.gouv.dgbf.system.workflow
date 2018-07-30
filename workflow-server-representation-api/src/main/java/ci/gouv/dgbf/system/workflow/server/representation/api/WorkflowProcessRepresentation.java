package ci.gouv.dgbf.system.workflow.server.representation.api;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowProcessDto;

@Path(WorkflowRepresentation.PATH)
public interface WorkflowProcessRepresentation extends PersistenceEntityRepresentation<WorkflowProcessDto> {
	
	@GET
	@Path(PATH+"get/code/{workflowCode}/{code}")
	@Produces({ MediaType.APPLICATION_XML })
	WorkflowProcessDto getByWorkflowCodeByCode(@PathParam("workflowCode") String workflowCode,@PathParam("code") String code);

	@GET
	@Path(PATH+"delete/code/{workflowCode}/{code}")
	@Produces({ MediaType.APPLICATION_XML })
	Response deleteByWorkflowCodeByCode(@PathParam("workflowCode") String workflowCode,@PathParam("code") String code);
	
	/*
	 * Redeclarations
	 */
	
	@POST
	@Path(PATH+"create")
	@Consumes(MediaType.APPLICATION_XML)
	Response createOne(WorkflowProcessDto dto);
	
	@GET
	@Path(PATH+"get/identifier/{identifier}")
	@Produces({ MediaType.APPLICATION_XML })
	WorkflowProcessDto getByIdentifier(@PathParam("identifier") Long identifier);
	
	@GET
	@Path(PATH+"get/all")
	@Produces({ MediaType.APPLICATION_XML })
	Collection<WorkflowProcessDto> getAll();
	
	@GET
	@Path(PATH+"count")
	@Produces({ MediaType.TEXT_PLAIN })
	Long countAll();
	
	/**/

	String PATH = "/workflow/process/";
}
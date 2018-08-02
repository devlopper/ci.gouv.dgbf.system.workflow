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

import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowProcessTaskDto;

@Path(WorkflowRepresentation.PATH)
public interface WorkflowProcessTaskRepresentation extends PersistenceEntityRepresentation<WorkflowProcessTaskDto> {
	
	@GET
	@Path(PATH+"execute/{workflowCode}/{workflowProcessCode}/{userIdentifier}")
	@Produces({ MediaType.APPLICATION_XML })
	Response execute(@PathParam("workflowCode")String workflowCode,@PathParam("workflowProcessCode")String workflowProcessCode,@PathParam("userIdentifier")String userIdentifier);
	
	/*
	 * Redeclarations
	 */
	
	@POST
	@Path(PATH+"create")
	@Consumes(MediaType.APPLICATION_XML)
	Response createOne(WorkflowProcessTaskDto dto);
	/*
	@GET
	@Path(PATH+"get/identifier/{identifier}")
	@Produces({ MediaType.APPLICATION_XML })
	WorkflowProcessTaskDto getByIdentifier(@PathParam("identifier") Long identifier);
	*/
	@GET
	@Path(PATH+"get/all")
	@Produces({ MediaType.APPLICATION_XML })
	Collection<WorkflowProcessTaskDto> getAll();
	
	@GET
	@Path(PATH+"count")
	@Produces({ MediaType.TEXT_PLAIN })
	Long countAll();
	
	@GET
	@Path(PATH+"getByWorkflowCode/{workflowCode}")
	@Produces({ MediaType.APPLICATION_XML })
	Collection<WorkflowProcessTaskDto> getByWorkflowCode(@PathParam("workflowCode") String workflowCode);
	
	@GET
	@Path(PATH+"count/workflow/{workflowCode}")
	@Produces({ MediaType.TEXT_PLAIN })
	Long countByWorkflowCode(@PathParam("workflowCode") String workflowCode);
	/*
	@GET
	@Path(PATH+"get/workflow/process/{workflowCode}")
	@Produces({ MediaType.APPLICATION_XML })
	Collection<WorkflowProcessTaskDto> getByWorkflowCodeByProcessCode();
	
	@GET
	@Path(PATH+"count")
	@Produces({ MediaType.TEXT_PLAIN })
	Long countByWorkflowCodeByProcessCode();
	
	@GET
	@Path(PATH+"get/all")
	@Produces({ MediaType.APPLICATION_XML })
	Collection<WorkflowProcessTaskDto> getByWorkflowCodeByUserIdentifier();
	
	@GET
	@Path(PATH+"count")
	@Produces({ MediaType.TEXT_PLAIN })
	Long countByWorkflowCodeByUserIdentifier();
	*/
	@GET
	@Path(PATH+"getByWorkflowCodeByProcessCodeByUserIdentifier/{workflowCode}/{processCode}/{userIdentifier}")
	@Produces({ MediaType.APPLICATION_XML })
	Collection<WorkflowProcessTaskDto> getByWorkflowCodeByProcessCodeByUserIdentifier(@PathParam("workflowCode")String workflowCode
			,@PathParam("processCode")String processCode,@PathParam("userIdentifier")String userIdentifier);
	
	@GET
	@Path(PATH+"countByWorkflowCodeByProcessCodeByUserIdentifier/{workflowCode}/{processCode}/{userIdentifier}")
	@Produces({ MediaType.TEXT_PLAIN })
	Long countByWorkflowCodeByProcessCodeByUserIdentifier(@PathParam("workflowCode")String workflowCode
			,@PathParam("processCode")String processCode,@PathParam("userIdentifier")String userIdentifier);
	
	/**/

	String PATH = "/workflowprocesstask/";
}
package ci.gouv.dgbf.system.workflow.server.representation.api;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cyk.utility.server.representation.RepresentationEntity;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTask;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowProcessTaskDto;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowProcessTaskDtoCollection;

//@Path(WorkflowRepresentation.PATH)
public interface WorkflowProcessTaskRepresentation extends RepresentationEntity<WorkflowProcessTask,WorkflowProcessTaskDto,WorkflowProcessTaskDtoCollection> {
	
	@GET
	@Path(PATH+"execute/{workflowCode}/{workflowProcessCode}/{userIdentifier}")
	@Produces({ MediaType.APPLICATION_XML })
	Response execute(@PathParam("workflowCode")String workflowCode,@PathParam("workflowProcessCode")String workflowProcessCode,@PathParam("userIdentifier")String userIdentifier);
	
	/*
	 * Redeclarations
	 */
	
	@GET
	@Path(PATH+"getByWorkflowCode/{workflowCode}")
	@Produces({ MediaType.APPLICATION_XML })
	Collection<WorkflowProcessTaskDto> getByWorkflowCode(@PathParam("workflowCode") String workflowCode);
	
	@GET
	@Path(PATH+"count/workflow/{workflowCode}")
	@Produces({ MediaType.TEXT_PLAIN })
	Long countByWorkflowCode(@PathParam("workflowCode") String workflowCode);
	
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
package ci.gouv.dgbf.system.workflow.server.representation.api;

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

@Path(WorkflowProcessTaskRepresentation.PATH)
public interface WorkflowProcessTaskRepresentation extends RepresentationEntity<WorkflowProcessTask,WorkflowProcessTaskDto,WorkflowProcessTaskDtoCollection> {
	
	@GET
	@Path(PATH_EXECUTE)
	@Produces({ MediaType.APPLICATION_XML })
	Response execute(@PathParam("workflowCode")String workflowCode,@PathParam("processCode")String workflowProcessCode,@PathParam("userIdentifier")String userIdentifier);
	
	@GET
	@Path(PATH_GET_BY_WORKFLOW_CODE)
	@Produces({ MediaType.APPLICATION_XML })
	Response getByWorkflowCode(@PathParam("code") String workflowCode);
	
	@GET
	@Path(PATH_COUNT_BY_WORKFLOW_CODE)
	@Produces({ MediaType.TEXT_PLAIN })
	Response countByWorkflowCode(@PathParam("code") String workflowCode);
	
	@GET
	@Path(PATH_GET_BY_WORKFLOW_CODE_BY_PROCESS_CODE_BY_USER_CODE)
	@Produces({ MediaType.APPLICATION_XML })
	Response getByWorkflowCodeByProcessCodeByUserIdentifier(@PathParam("workflowCode")String workflowCode
			,@PathParam("processCode")String processCode,@PathParam("userIdentifier")String userIdentifier);
	
	@GET
	@Path(PATH_COUNT_BY_WORKFLOW_CODE_BY_PROCESS_CODE_BY_USER_CODE)
	@Produces({ MediaType.TEXT_PLAIN })
	Response countByWorkflowCodeByProcessCodeByUserIdentifier(@PathParam("workflowCode")String workflowCode
			,@PathParam("processCode")String processCode,@PathParam("userIdentifier")String userIdentifier);
	
	/**/

	String PATH = "/workflowprocesstask/";
	
	String PATH_EXECUTE = "/execute/{workflowCode}/{processCode}/{userIdentifier}";
	
	String PATH_GET_BY_WORKFLOW_CODE = "/getbyworkflow/{code}";
	String PATH_COUNT_BY_WORKFLOW_CODE = "/countbyworkflow/{code}";
	
	String PATH_GET_BY_WORKFLOW_CODE_BY_PROCESS_CODE_BY_USER_CODE = "/getbyworkflowbyprocessbyuser/{workflowCode}/{processCode}/{userIdentifier}";
	String PATH_COUNT_BY_WORKFLOW_CODE_BY_PROCESS_CODE_BY_USER_CODE = "/countbyworkflowbyprocessbyuser/{workflowCode}/{processCode}/{userIdentifier}";
	
}
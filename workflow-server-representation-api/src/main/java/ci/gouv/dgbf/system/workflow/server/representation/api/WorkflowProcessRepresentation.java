package ci.gouv.dgbf.system.workflow.server.representation.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cyk.utility.server.representation.RepresentationEntity;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowProcessDto;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowProcessDtoCollection;

@Path(WorkflowProcessRepresentation.PATH)
public interface WorkflowProcessRepresentation extends RepresentationEntity<WorkflowProcess,WorkflowProcessDto,WorkflowProcessDtoCollection> {
	
	@GET
	@Path(PATH+"get/code/{workflowCode}/{code}")
	@Produces({ MediaType.APPLICATION_XML })
	Response getByWorkflowCodeByCode(@PathParam("workflowCode") String workflowCode,@PathParam("code") String code);

	@GET
	@Path(PATH+"delete/code/{workflowCode}/{code}")
	@Produces({ MediaType.APPLICATION_XML })
	Response deleteByWorkflowCodeByCode(@PathParam("workflowCode") String workflowCode,@PathParam("code") String code);
	
	/**/

	String PATH = "/workflowprocess/";
}
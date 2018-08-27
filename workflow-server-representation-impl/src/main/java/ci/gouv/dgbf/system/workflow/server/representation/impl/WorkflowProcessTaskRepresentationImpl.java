package ci.gouv.dgbf.system.workflow.server.representation.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

import ci.gouv.dgbf.system.workflow.server.business.api.WorkflowProcessTaskBusiness;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTask;
import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowProcessTaskRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowProcessTaskDto;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowProcessTaskDtoCollection;

public class WorkflowProcessTaskRepresentationImpl extends AbstractRepresentationEntityImpl<WorkflowProcessTask, WorkflowProcessTaskBusiness, WorkflowProcessTaskDto,WorkflowProcessTaskDtoCollection> implements WorkflowProcessTaskRepresentation,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Response execute(String workflowCode, String workflowProcessCode, String userIdentifier) {
		getBusiness().execute(workflowCode,workflowProcessCode, userIdentifier);
		return Response.ok().status(Response.Status.OK).build();
	}
	
	@Override
	public Collection<WorkflowProcessTaskDto> getByWorkflowCode(String workflowCode) {
		return null;//getRepresentationFromPersistence(getBusiness().findByWorkflowCode(workflowCode));
	}
	
	@Override
	public Long countByWorkflowCode(String workflowCode) {
		return getBusiness().countByWorkflowCode(workflowCode);
	}
	
	@Override
	public Collection<WorkflowProcessTaskDto> getByWorkflowCodeByProcessCodeByUserIdentifier(String workflowCode,String processCode, String userIdentifier) {
		return null;//getRepresentationFromPersistence(getBusiness().findByWorkflowCodeByProcessCodeByUserIdentifier(workflowCode,processCode,userIdentifier));
	}
	
	@Override
	public Long countByWorkflowCodeByProcessCodeByUserIdentifier(String workflowCode, String processCode,String userIdentifier) {
		return getBusiness().countByWorkflowCodeByProcessCodeByUserIdentifier(workflowCode, processCode, userIdentifier);
	}
	
}

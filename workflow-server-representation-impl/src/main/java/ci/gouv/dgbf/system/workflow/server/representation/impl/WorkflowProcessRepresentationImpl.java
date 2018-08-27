package ci.gouv.dgbf.system.workflow.server.representation.impl;

import java.io.Serializable;

import javax.ws.rs.core.Response;

import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

import ci.gouv.dgbf.system.workflow.server.business.api.WorkflowProcessBusiness;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowProcessRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowProcessDto;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowProcessDtoCollection;

public class WorkflowProcessRepresentationImpl extends AbstractRepresentationEntityImpl<WorkflowProcess, WorkflowProcessBusiness, WorkflowProcessDto,WorkflowProcessDtoCollection> implements WorkflowProcessRepresentation,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public WorkflowProcessDto getByWorkflowCodeByCode(String workflowCode,String code) {
		return null;//getRepresentationFromPersistence(getBusiness().findByWorkflowCodeByCode(workflowCode,code));
	}
	
	@Override
	public Response deleteByWorkflowCodeByCode(String workflowCode,String code) {
		getBusiness().deleteByWorkflowCodeByCode(workflowCode,code);
		return Response.ok().status(Response.Status.OK).build();
	}
	
	/**/
	
}

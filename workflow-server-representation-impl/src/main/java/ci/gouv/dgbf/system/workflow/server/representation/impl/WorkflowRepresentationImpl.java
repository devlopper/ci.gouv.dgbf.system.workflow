package ci.gouv.dgbf.system.workflow.server.representation.impl;

import java.io.Serializable;
import java.util.Arrays;

import javax.ws.rs.core.Response;

import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

import ci.gouv.dgbf.system.workflow.server.business.api.WorkflowBusiness;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.bpmn.Bpmn;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.bpmn.UserTask;
import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowDto;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowDtoCollection;

public class WorkflowRepresentationImpl extends AbstractRepresentationEntityImpl<Workflow, WorkflowBusiness, WorkflowDto,WorkflowDtoCollection> implements WorkflowRepresentation,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Response synchroniseWithJbpmMavenRepository() {
		//TODO handle error
		getBusiness().synchroniseWithJbpmMavenRepository();
		return Response.status(Response.Status.OK).build();
	}
	
	@Override
	public Response getOne(String identifier, String type) {
		Response response = super.getOne(identifier, type);
		
		WorkflowDto workflowDto = (WorkflowDto)response.getEntity();
		if(workflowDto!=null && workflowDto.getModel()!=null && !workflowDto.getModel().isEmpty()) {
			Bpmn bpmn = Bpmn.__executeWithContent__(workflowDto.getModel());
			workflowDto.setModel(null);
			if(bpmn!=null && bpmn.getProcess()!=null && bpmn.getProcess().getUserTasks()!=null)
				for(UserTask index : bpmn.getProcess().getUserTasks())
					workflowDto.getTasks(Boolean.TRUE).add(index.getName(),Arrays.asList(index.getPotentialOwnerResourceAssignmentExpressionFormalExpression()));
		}
		return response;
	}
	
}

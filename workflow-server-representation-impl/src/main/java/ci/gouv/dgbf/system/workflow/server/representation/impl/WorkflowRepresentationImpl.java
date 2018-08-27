package ci.gouv.dgbf.system.workflow.server.representation.impl;

import java.io.Serializable;

import javax.ws.rs.core.Response;

import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

import ci.gouv.dgbf.system.workflow.server.business.api.WorkflowBusiness;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowDto;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowDtoCollection;

public class WorkflowRepresentationImpl extends AbstractRepresentationEntityImpl<Workflow, WorkflowBusiness, WorkflowDto,WorkflowDtoCollection> implements WorkflowRepresentation,Serializable {
	private static final long serialVersionUID = 1L;


	@Override
	public Response getOne(String identifier, String type) {
		System.out.println("WorkflowRepresentationImpl.getOne() "+identifier+" , "+type);
		return super.getOne(identifier, type);
	}
	
}

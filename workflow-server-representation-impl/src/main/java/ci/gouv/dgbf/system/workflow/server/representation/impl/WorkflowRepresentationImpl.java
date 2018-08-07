package ci.gouv.dgbf.system.workflow.server.representation.impl;

import java.io.Serializable;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import ci.gouv.dgbf.system.workflow.business.api.WorkflowBusiness;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowDto;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowEntityMapper;

public class WorkflowRepresentationImpl extends AbstractPersistenceEntityRepresentationImpl<Workflow,WorkflowDto> implements WorkflowRepresentation,Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private WorkflowBusiness workflowBusiness;
	
	@Override
	public WorkflowDto getByIdentifier(Long identifier) {
		return super.getByIdentifier(identifier);
	}
	
	@Override
	public WorkflowDto getByCode(String code) {
		return getEntityMapper().getRepresentationFromPersistence(getBusiness().findByCode(code));
	}
	
	@Override
	public Response deleteByCode(String code) {
		getBusiness().deleteByCode(code);
		return Response.ok().status(Response.Status.OK).build();
	}
	
	/**/
	
	@Override
	protected WorkflowBusiness getBusiness() {
		return workflowBusiness;
	}
	
	@Override
	protected WorkflowEntityMapper getEntityMapper() {
		return WorkflowEntityMapper.INSTANCE;
	}
	
	
	
	
}

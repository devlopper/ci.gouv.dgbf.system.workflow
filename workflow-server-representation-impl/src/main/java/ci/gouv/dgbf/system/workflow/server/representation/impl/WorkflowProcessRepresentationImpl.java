package ci.gouv.dgbf.system.workflow.server.representation.impl;

import java.io.Serializable;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import ci.gouv.dgbf.system.workflow.business.api.WorkflowProcessBusiness;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowProcessRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowProcessDto;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowProcessEntityMapper;

public class WorkflowProcessRepresentationImpl extends AbstractPersistenceEntityRepresentationImpl<WorkflowProcess,WorkflowProcessDto> implements WorkflowProcessRepresentation,Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private WorkflowProcessBusiness business;
	@Inject private WorkflowPersistence workflowPersistence;
	
	@Override
	protected WorkflowProcess getPersistenceFromRepresentation(WorkflowProcessDto dto) {
		return super.getPersistenceFromRepresentation(dto).setWorkflow(workflowPersistence.readByCode(dto.getWorkflowCode()));
	}
	
	@Override
	protected WorkflowProcessDto getRepresentationFromPersistence(WorkflowProcess entity) {
		if(entity == null)
			return null;
		return super.getRepresentationFromPersistence(entity).setWorkflowCode(entity.getWorkflow() == null ? null : entity.getWorkflow().getCode());
	}
	
	@Override
	public WorkflowProcessDto getByIdentifier(Long identifier) {
		return super.getByIdentifier(identifier);
	}
	
	@Override
	public WorkflowProcessDto getByWorkflowCodeByCode(String workflowCode,String code) {
		return getRepresentationFromPersistence(getBusiness().findByWorkflowCodeByCode(workflowCode,code));
	}
	
	@Override
	public Response deleteByWorkflowCodeByCode(String workflowCode,String code) {
		getBusiness().deleteByWorkflowCodeByCode(workflowCode,code);
		return Response.ok().status(Response.Status.OK).build();
	}
	
	/**/
	
	@Override
	protected WorkflowProcessBusiness getBusiness() {
		return business;
	}
	
	@Override
	protected WorkflowProcessEntityMapper getEntityMapper() {
		return WorkflowProcessEntityMapper.INSTANCE;
	}
	
	
	
	
}

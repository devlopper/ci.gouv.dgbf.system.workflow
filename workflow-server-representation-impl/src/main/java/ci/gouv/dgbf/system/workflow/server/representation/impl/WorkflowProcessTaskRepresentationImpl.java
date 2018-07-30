package ci.gouv.dgbf.system.workflow.server.representation.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import ci.gouv.dgbf.system.workflow.business.api.WorkflowProcessBusiness;
import ci.gouv.dgbf.system.workflow.business.api.WorkflowProcessTaskBusiness;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTask;
import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowProcessTaskRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowProcessTaskDto;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowProcessTaskEntityMapper;

public class WorkflowProcessTaskRepresentationImpl extends AbstractPersistenceEntityRepresentationImpl<WorkflowProcessTask,WorkflowProcessTaskDto> implements WorkflowProcessTaskRepresentation,Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private WorkflowProcessTaskBusiness business;
	@Inject private WorkflowProcessBusiness workflowProcessBusiness;
	
	@Override
	public Response execute(String workflowCode, String workflowProcessCode, String userIdentifier) {
		getBusiness().execute(workflowCode,workflowProcessCode, userIdentifier);
		return Response.ok().status(Response.Status.OK).build();
	}
	
	@Override
	protected WorkflowProcessTask getPersistenceFromRepresentation(WorkflowProcessTaskDto dto) {
		return super.getPersistenceFromRepresentation(dto).setWorkflowProcess(workflowProcessBusiness.find(dto.getWorkflowProcessIdentifier()));
	}
	
	@Override
	protected WorkflowProcessTaskDto getRepresentationFromPersistence(WorkflowProcessTask entity) {
		if(entity == null)
			return null;
		return super.getRepresentationFromPersistence(entity).setWorkflowProcessIdentifier(entity.getWorkflowProcess() == null ? null : entity.getWorkflowProcess().getIdentifier());
	}
	
	@Override
	public WorkflowProcessTaskDto getByIdentifier(Long identifier) {
		return super.getByIdentifier(identifier);
	}
	
	@Override
	public Collection<WorkflowProcessTaskDto> getByWorkflowCode(String workflowCode) {
		return getRepresentationFromPersistence(getBusiness().findByWorkflowCode(workflowCode));
	}
	
	@Override
	public Long countByWorkflowCode(String workflowCode) {
		return getBusiness().countByWorkflowCode(workflowCode);
	}
	
	/*@Override
	public WorkflowProcessTaskDto getByWorkflowCodeByCode(String workflowCode,String code) {
		return getRepresentationFromPersistence(getBusiness().findByWorkflowCodeByCode(workflowCode,code));
	}
	
	@Override
	public Response deleteByWorkflowCodeByCode(String workflowCode,String code) {
		getBusiness().deleteByWorkflowCodeByCode(workflowCode,code);
		return Response.ok().status(Response.Status.OK).build();
	}*/
	
	/**/
	
	@Override
	protected WorkflowProcessTaskBusiness getBusiness() {
		return business;
	}
	
	@Override
	protected WorkflowProcessTaskEntityMapper getEntityMapper() {
		return WorkflowProcessTaskEntityMapper.INSTANCE;
	}
	
	
	
	
}

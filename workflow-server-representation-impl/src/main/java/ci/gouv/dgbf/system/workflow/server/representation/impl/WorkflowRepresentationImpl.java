package ci.gouv.dgbf.system.workflow.server.representation.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import ci.gouv.dgbf.system.workflow.business.api.EntityBusiness;
import ci.gouv.dgbf.system.workflow.business.api.WorkflowBusiness;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowDto;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowEntityMapper;

public class WorkflowRepresentationImpl extends AbstractPersistenceEntityRepresentationImpl<Workflow,WorkflowDto> implements WorkflowRepresentation,Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private WorkflowBusiness workflowBusiness;
	
	@Override
	protected EntityBusiness<Workflow> getBusiness() {
		return workflowBusiness;
	}
	
	@Override
	public Response createOne(WorkflowDto dto) {
		workflowBusiness.create(WorkflowEntityMapper.INSTANCE.getPersistenceFromRepresentation(dto));
		return Response.ok().status(Response.Status.CREATED).build();
	}
	
	@Override
	public WorkflowDto getByCode(String code) {
		return WorkflowEntityMapper.INSTANCE.getRepresentationFromPersistence(workflowBusiness.findByCode(code));
	}

	@Override
	public Collection<WorkflowDto> getAll() {
		Collection<WorkflowDto> collection = new ArrayList<>();
		for(Workflow index : getBusiness().findAll())
			collection.add(WorkflowEntityMapper.INSTANCE.getRepresentationFromPersistence(index));
		return collection;
	}

	@Override
	public Long countAll() {
		return workflowBusiness.countAll();
	}
}

package ci.gouv.dgbf.system.workflow.server.persistence.impl;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.jbpm.services.api.DefinitionService;

import ci.gouv.dgbf.system.workflow.server.persistence.api.EntityPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;

public class WorkflowPersistenceImpl extends AbstractEntityPersistenceImpl<Workflow> implements WorkflowPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private DefinitionService definitionService;
	
	@Override
	public WorkflowPersistence create(Workflow workflow) {
		super.create(workflow);
		buildProcessDefinition(workflow);
		return this;
	}
	
	@Override
	public Workflow readByCode(String code) {
		try {
			return entityManager.createQuery("SELECT workflow FROM Workflow workflow WHERE workflow.code = :workflowCode",Workflow.class)
					.setParameter("workflowCode", code).getSingleResult();
		} catch (NoResultException exception) {
			return null;
		}
	}
	
	@Override
	public EntityPersistence<Workflow> update(Workflow workflow) {
		super.update(workflow);
		buildProcessDefinition(workflow);
		return this;
	}

	@Override
	public WorkflowPersistence deleteByCode(String code) {
		delete(readByCode(code));
		return this;
	}
	
	/**/
	
	private void buildProcessDefinition(Workflow workflow){
		definitionService.buildProcessDefinition(persistenceHelper.getDeploymentIdentifier(), workflow.getModelAsBpmn(), null, Boolean.TRUE);
	}
}

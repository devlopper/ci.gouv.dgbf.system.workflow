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
		if(workflow.getJbpmProcessDefinition() == null)
			buildProcessDefinition(workflow);
		workflow.setCode(workflow.getJbpmProcessDefinition().getId());
		workflow.setName(workflow.getJbpmProcessDefinition().getName());
		super.create(workflow);
		persistenceHelper.addProcessDefinitionsFromWorkflow(workflow);
		return this;
	}
	
	@Override
	public Workflow readByCode(String code) {
		try {
			Workflow workflow = entityManager.createQuery("SELECT workflow FROM Workflow workflow WHERE workflow.code = :workflowCode",Workflow.class)
					.setParameter("workflowCode", code).getSingleResult();
			if(workflow!=null){
				if(workflow.getJbpmProcessDefinition() == null)
					buildProcessDefinition(workflow);
			}
			return workflow;
		} catch (NoResultException exception) {
			return null;
		}
	}
	
	@Override
	public EntityPersistence<Workflow> update(Workflow workflow) {
		if(workflow.getJbpmProcessDefinition() == null)
			buildProcessDefinition(workflow);
		workflow.setCode(workflow.getJbpmProcessDefinition().getId());
		workflow.setName(workflow.getJbpmProcessDefinition().getName());
		super.update(workflow);
		//TODO we should update the bytes
		//persistenceHelper.addProcessDefinitionsFromWorkflow(workflow);
		return this;
	}

	@Override
	public WorkflowPersistence deleteByCode(String code) {
		delete(readByCode(code));
		return this;
	}
	
	/**/
	
	private void buildProcessDefinition(Workflow workflow){
		workflow.setJbpmProcessDefinition(definitionService.buildProcessDefinition("MYDEP", workflow.getModel(), null, Boolean.FALSE));
	}

}

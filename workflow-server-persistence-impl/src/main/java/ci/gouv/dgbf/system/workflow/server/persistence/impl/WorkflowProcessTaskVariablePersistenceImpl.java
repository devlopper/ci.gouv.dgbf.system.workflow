package ci.gouv.dgbf.system.workflow.server.persistence.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessTaskVariablePersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTaskVariable;

@Singleton
public class WorkflowProcessTaskVariablePersistenceImpl extends AbstractPersistenceEntityImpl<WorkflowProcessTaskVariable> implements WorkflowProcessTaskVariablePersistence,Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private EntityManager entityManager;
	
	@Override
	public Collection<WorkflowProcessTaskVariable> readByWorkflowProcessIdentifier(Long workflowProcessIdentifier) {
		return entityManager.createNamedQuery("WorkflowProcessTaskVariable.readByProcessInstanceId", WorkflowProcessTaskVariable.class).setParameter("processInstanceId", workflowProcessIdentifier).getResultList();	
	}
	
	@Override
	public Long countByWorkflowProcessIdentifier(Long workflowProcessIdentifier) {
		return entityManager.createNamedQuery("WorkflowProcessTaskVariable.countByProcessInstanceId", Long.class).setParameter("processInstanceId", workflowProcessIdentifier).getSingleResult();
	}

	@Override
	public Collection<WorkflowProcessTaskVariable> readByWorkflowCodeByCode(String workflowCode, String code) {
		WorkflowProcess workflowProcess = __inject__(WorkflowProcessPersistence.class).readByWorkflowCodeByCode(workflowCode, code);
		return readByWorkflowProcessIdentifier(workflowProcess.getIdentifier());
	}

	@Override
	public Long countByWorkflowCodeByCode(String workflowCode, String code) {
		WorkflowProcess workflowProcess = __inject__(WorkflowProcessPersistence.class).readByWorkflowCodeByCode(workflowCode, code);
		return countByWorkflowProcessIdentifier(workflowProcess.getIdentifier());
	}
	
}

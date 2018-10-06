package ci.gouv.dgbf.system.workflow.server.persistence.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessNodeLogPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessNodeLog;

@Singleton
public class WorkflowProcessNodeLogPersistenceImpl extends AbstractPersistenceEntityImpl<WorkflowProcessNodeLog> implements WorkflowProcessNodeLogPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private EntityManager entityManager;
	
	@Override
	public Collection<WorkflowProcessNodeLog> readByWorkflowProcessIdentifier(Long workflowProcessIdentifier) {
		return entityManager.createNamedQuery("WorkflowProcessNodeLog.readByProcessInstanceId", WorkflowProcessNodeLog.class).setParameter("processInstanceId", workflowProcessIdentifier).getResultList();	
	}
	
	@Override
	public Long countByWorkflowProcessIdentifier(Long workflowProcessIdentifier) {
		return entityManager.createNamedQuery("WorkflowProcessNodeLog.countByProcessInstanceId", Long.class).setParameter("processInstanceId", workflowProcessIdentifier).getSingleResult();
	}

	@Override
	public Collection<WorkflowProcessNodeLog> readByWorkflowCodeByProcessCode(String workflowCode, String code) {
		WorkflowProcess workflowProcess = __inject__(WorkflowProcessPersistence.class).readByWorkflowCodeByCode(workflowCode, code);
		return readByWorkflowProcessIdentifier(workflowProcess.getIdentifier());
	}

	@Override
	public Long countByWorkflowCodeByProcessCode(String workflowCode, String code) {
		WorkflowProcess workflowProcess = __inject__(WorkflowProcessPersistence.class).readByWorkflowCodeByCode(workflowCode, code);
		return countByWorkflowProcessIdentifier(workflowProcess.getIdentifier());
	}

	@Override
	public Collection<WorkflowProcessNodeLog> readByWorkflowCodeByProcessCodeByCode(String workflowCode,String processCode, String code) {
		WorkflowProcess workflowProcess = __inject__(WorkflowProcessPersistence.class).readByWorkflowCodeByCode(workflowCode, processCode);
		return entityManager.createNamedQuery("WorkflowProcessNodeLog.readByProcessInstanceIdByNodeId", WorkflowProcessNodeLog.class)
				.setParameter("processInstanceId", workflowProcess.getIdentifier()).setParameter("nodeId", code).getResultList();
	}

	@Override
	public Long countByWorkflowCodeByProcessCodeByCode(String workflowCode, String processCode, String code) {
		WorkflowProcess workflowProcess = __inject__(WorkflowProcessPersistence.class).readByWorkflowCodeByCode(workflowCode, processCode);
		return entityManager.createNamedQuery("WorkflowProcessNodeLog.countByProcessInstanceIdByNodeId", Long.class)
				.setParameter("processInstanceId", workflowProcess.getIdentifier()).setParameter("nodeId", code).getSingleResult();
	}

	@Override
	public Collection<WorkflowProcessNodeLog> readByWorkflowCodeByProcessCodeByWorkItemIdentifier(String workflowCode,String processCode, Long workItemIdentifier) {
		WorkflowProcess workflowProcess = __inject__(WorkflowProcessPersistence.class).readByWorkflowCodeByCode(workflowCode, processCode);
		return entityManager.createNamedQuery("WorkflowProcessNodeLog.readByProcessInstanceIdByWorkItemId", WorkflowProcessNodeLog.class)
				.setParameter("processInstanceId", workflowProcess.getIdentifier()).setParameter("workItemId", workItemIdentifier).getResultList();
	}

	@Override
	public Long countByWorkflowCodeByProcessCodeByWorkItemIdentifier(String workflowCode, String processCode,Long workItemIdentifier) {
		WorkflowProcess workflowProcess = __inject__(WorkflowProcessPersistence.class).readByWorkflowCodeByCode(workflowCode, processCode);
		return entityManager.createNamedQuery("WorkflowProcessNodeLog.countByProcessInstanceIdByWorkItemId", Long.class)
				.setParameter("processInstanceId", workflowProcess.getIdentifier()).setParameter("workItemId", workItemIdentifier).getSingleResult();
	}
	
	@Override
	public Collection<WorkflowProcessNodeLog> readByWorkflowCodeByProcessIdentifierByWorkItemIdentifier(String workflowCode,Long processIdentifier, Long workItemIdentifier) {
		return entityManager.createNamedQuery("WorkflowProcessNodeLog.readByProcessInstanceIdByWorkItemId", WorkflowProcessNodeLog.class)
				.setParameter("processInstanceId", processIdentifier).setParameter("workItemId", workItemIdentifier).getResultList();
	}

	@Override
	public Long countByWorkflowCodeByProcessIdentifierByWorkItemIdentifier(String workflowCode, Long processIdentifier,Long workItemIdentifier) {
		return entityManager.createNamedQuery("WorkflowProcessNodeLog.countByProcessInstanceIdByWorkItemId", Long.class)
				.setParameter("processInstanceId", processIdentifier).setParameter("workItemId", workItemIdentifier).getSingleResult();
	}
	
}

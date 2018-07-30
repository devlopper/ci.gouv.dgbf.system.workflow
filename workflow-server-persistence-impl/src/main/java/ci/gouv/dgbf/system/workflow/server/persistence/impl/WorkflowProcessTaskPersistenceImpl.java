package ci.gouv.dgbf.system.workflow.server.persistence.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessTaskPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTask;

public class WorkflowProcessTaskPersistenceImpl extends AbstractEntityPersistenceImpl<WorkflowProcessTask> implements WorkflowProcessTaskPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private WorkflowProcessPersistence workflowProcessPersistence;
	
	@Override
	public Collection<WorkflowProcessTask> readByWorkflowCode(String workflowCode) {
		return entityManager.createNamedQuery("WorkflowProcessTask.readByProcessId", WorkflowProcessTask.class).setParameter("processId", workflowCode).getResultList();
	}
	
	@Override
	public Long countByWorkflowCode(String workflowCode) {
		return entityManager.createNamedQuery("WorkflowProcessTask.countByProcessId", Long.class).setParameter("processId", workflowCode).getSingleResult();
	}
	
	@Override
	public Collection<WorkflowProcessTask> readByWorkflowProcess(WorkflowProcess workflowProcess) {
		return entityManager.createNamedQuery("WorkflowProcessTask.readByProcessInstanceId", WorkflowProcessTask.class).setParameter("processInstanceId"
				, workflowProcess.getIdentifier()).getResultList();
	}
	
	@Override
	public Long countByWorkflowProcess(WorkflowProcess workflowProcess) {
		return entityManager.createNamedQuery("WorkflowProcessTask.countByProcessInstanceId", Long.class).setParameter("processInstanceId"
				, workflowProcess.getIdentifier()).getSingleResult();
	}
	
	@Override
	public Collection<WorkflowProcessTask> readByWorkflowCodeByProcessCode(String workflowCode,String processCode) {
		return readByWorkflowProcess(workflowProcessPersistence.readByWorkflowCodeByCode(workflowCode, processCode));
	}
	
	@Override
	public Long countByWorkflowCodeByProcessCode(String workflowCode, String processCode) {
		return countByWorkflowProcess(workflowProcessPersistence.readByWorkflowCodeByCode(workflowCode, processCode));
	}
	
	@Override
	public Collection<WorkflowProcessTask> readByWorkflowCodeByUserIdentifier(String workflowCode, String userIdentifier) {
		return entityManager.createNamedQuery("WorkflowProcessTask.readByProcessIdByActualOwnerId", WorkflowProcessTask.class).setParameter("processId"
				, workflowCode).setParameter("actualOwnerId", userIdentifier).getResultList();
	}
	
	@Override
	public Long countByWorkflowCodeByUserIdentifier(String workflowCode,String userIdentifier) {
		return entityManager.createNamedQuery("WorkflowProcessTask.countByProcessIdByActualOwnerId", Long.class).setParameter("processId"
				, workflowCode).setParameter("actualOwnerId", userIdentifier).getSingleResult();
	}
	
	@Override
	public Collection<WorkflowProcessTask> readByWorkflowProcessByUserIdentifier(WorkflowProcess workflowProcess,String userIdentifier) {
		return entityManager.createNamedQuery("WorkflowProcessTask.readByProcessInstanceIdByActualOwnerId", WorkflowProcessTask.class).setParameter("processInstanceId"
				, workflowProcess.getIdentifier()).setParameter("actualOwnerId", userIdentifier).getResultList();
	}
	
	@Override
	public Long countByWorkflowProcessByUserIdentifier(WorkflowProcess workflowProcess, String userIdentifier) {
		return entityManager.createNamedQuery("WorkflowProcessTask.countByProcessInstanceIdByActualOwnerId", Long.class).setParameter("processInstanceId"
				, workflowProcess.getIdentifier()).setParameter("actualOwnerId", userIdentifier).getSingleResult();
	}
	
	@Override
	public Collection<WorkflowProcessTask> readByWorkflowCodeByProcessCodeByUserIdentifier(String workflowCode,String processCode, String userIdentifier) {
		return readByWorkflowProcessByUserIdentifier(workflowProcessPersistence.readByWorkflowCodeByCode(workflowCode, processCode), userIdentifier);
	}
	
	@Override
	public Long countByWorkflowCodeByProcessCodeByUserIdentifier(String workflowCode, String processCode,String userIdentifier) {
		return countByWorkflowProcessByUserIdentifier(workflowProcessPersistence.readByWorkflowCodeByCode(workflowCode, processCode), userIdentifier);
	}

	@Override
	public Collection<WorkflowProcessTask> readAll() {
		return entityManager.createNamedQuery("WorkflowProcessTask.readAll", WorkflowProcessTask.class).getResultList();
	}
	
	@Override
	public Long countAll() {
		return entityManager.createNamedQuery("WorkflowProcessTask.countAll", Long.class).getSingleResult();
	}
}

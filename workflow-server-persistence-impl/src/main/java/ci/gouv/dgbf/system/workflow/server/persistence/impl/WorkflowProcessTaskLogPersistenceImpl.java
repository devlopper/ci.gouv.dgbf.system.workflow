package ci.gouv.dgbf.system.workflow.server.persistence.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.jbpm.services.api.model.UserTaskInstanceDesc;

import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessTaskLogPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTaskLog;

public class WorkflowProcessTaskLogPersistenceImpl extends AbstractEntityPersistenceImpl<WorkflowProcessTaskLog> implements WorkflowProcessTaskLogPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private WorkflowProcessPersistence workflowProcessPersistence;
	
	@Override
	public Collection<WorkflowProcessTaskLog> readByWorkflowCode(String workflowCode) {
		return entityManager.createNamedQuery("WorkflowProcessTaskLog.readByProcessId", WorkflowProcessTaskLog.class).setParameter("processId", workflowCode).getResultList();	
	}
	
	@Override
	public Long countByWorkflowCode(String workflowCode) {
		return entityManager.createNamedQuery("WorkflowProcessTaskLog.countByProcessId", Long.class).setParameter("processId", workflowCode).getSingleResult();
	}
	
	@Override
	public Collection<WorkflowProcessTaskLog> readByWorkflowProcess(WorkflowProcess workflowProcess) {
		Collection<WorkflowProcessTaskLog> workflowProcessTaskLogs =  null;
		Collection<Long> identifiers = runtimeDataService.getTasksByProcessInstanceId(workflowProcess.getIdentifier());
		if(identifiers!=null){
			workflowProcessTaskLogs = new ArrayList<>();
			for(Long index : identifiers){
				UserTaskInstanceDesc userTaskInstanceDesc = runtimeDataService.getTaskById(index);
				WorkflowProcessTaskLog workflowProcessTaskLog = new WorkflowProcessTaskLog();
				workflowProcessTaskLogs.add(workflowProcessTaskLog);
			}
		}
		return workflowProcessTaskLogs;
	}
	
	@Override
	public Long countByWorkflowProcess(WorkflowProcess workflowProcess) {
		Collection<WorkflowProcessTaskLog> workflowProcessTaskLogs = readByWorkflowProcess(workflowProcess);
		return workflowProcessTaskLogs == null ? null : new Long(workflowProcessTaskLogs.size());
	}
	
	@Override
	public Collection<WorkflowProcessTaskLog> readByWorkflowCodeByProcessCode(String workflowCode,String processCode) {
		WorkflowProcess workflowProcess = workflowProcessPersistence.readByWorkflowCodeByCode(workflowCode, processCode);
		return entityManager.createNamedQuery("WorkflowProcessTaskLog.readByProcessInstanceId", WorkflowProcessTaskLog.class).setParameter("processInstanceId", workflowProcess.getIdentifier())
			.getResultList();	
	}
	
	@Override
	public Long countByWorkflowCodeByProcessCode(String workflowCode, String processCode) {
		WorkflowProcess workflowProcess = workflowProcessPersistence.readByWorkflowCodeByCode(workflowCode, processCode);
		return entityManager.createNamedQuery("WorkflowProcessTaskLog.countByProcessInstanceId", Long.class).setParameter("processInstanceId", workflowProcess.getIdentifier())
			.getSingleResult();
	}
	
	@Override
	public Collection<WorkflowProcessTaskLog> readByWorkflowProcessByUserIdentifier(WorkflowProcess workflowProcess,String userIdentifier) {
		return null;
	}
	
	@Override
	public Long countByWorkflowProcessByUserIdentifier(WorkflowProcess workflowProcess, String userIdentifier) {
		return null;
	}
	
	@Override
	public Collection<WorkflowProcessTaskLog> readByWorkflowCodeByProcessCodeByUserIdentifier(String workflowCode,String processCode, String userIdentifier) {
		return readByWorkflowProcessByUserIdentifier(workflowProcessPersistence.readByWorkflowCodeByCode(workflowCode, processCode), userIdentifier);
	}
	
	@Override
	public Long countByWorkflowCodeByProcessCodeByUserIdentifier(String workflowCode, String processCode,String userIdentifier) {
		return null;
	}
}

package ci.gouv.dgbf.system.workflow.business.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import ci.gouv.dgbf.system.workflow.business.api.WorkflowProcessTaskBusiness;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessTaskPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTask;

public class WorkflowProcessTaskBusinessImpl extends AbstractEntityBusinessImpl<WorkflowProcessTask> implements WorkflowProcessTaskBusiness,Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private WorkflowProcessTaskPersistence persistence;
	
	@Override
	protected Class<WorkflowProcessTask> __getEntityClass__() {
		return WorkflowProcessTask.class;
	}
	
	@Override
	protected WorkflowProcessTaskPersistence getPersistence() {
		return persistence;
	}

	@Override
	public WorkflowProcessTaskBusiness start(WorkflowProcessTask workflowProcessTask, String userIdentifier) {
		persistenceHelper.getTaskService().start(workflowProcessTask.getIdentifier(), userIdentifier);
		return this;
	}

	@Override
	public WorkflowProcessTaskBusiness complete(WorkflowProcessTask workflowProcessTask, String userIdentifier) {
		persistenceHelper.getTaskService().complete(workflowProcessTask.getIdentifier(), userIdentifier,null);
		return this;
	}

	@Override
	public WorkflowProcessTaskBusiness execute(WorkflowProcessTask workflowProcessTask, String userIdentifier) {
		start(workflowProcessTask, userIdentifier);
		complete(workflowProcessTask, userIdentifier);
		return this;
	}

	@Override
	public Collection<WorkflowProcessTask> findByWorkflowCode(String workflowCode) {
		return getPersistence().readByWorkflowCode(workflowCode);
	}

	@Override
	public Long countByWorkflowCode(String workflowCode) {
		return getPersistence().countByWorkflowCode(workflowCode);
	}

	@Override
	public Collection<WorkflowProcessTask> findByWorkflowCodeByProcessCode(String workflowCode, String processCode) {
		return getPersistence().readByWorkflowCodeByProcessCode(workflowCode, processCode);
	}

	@Override
	public Long countByWorkflowCodeByProcessCode(String workflowCode, String processCode) {
		return getPersistence().countByWorkflowCodeByProcessCode(workflowCode, processCode);
	}

	@Override
	public Collection<WorkflowProcessTask> findByWorkflowProcess(WorkflowProcess workflowProcess) {
		return getPersistence().readByWorkflowProcess(workflowProcess);
	}

	@Override
	public Long countByWorkflowProcess(WorkflowProcess workflowProcess) {
		return getPersistence().countByWorkflowProcess(workflowProcess);
	}

	@Override
	public Collection<WorkflowProcessTask> findByWorkflowCodeByUserIdentifier(String workflowCode,String userIdentifier) {
		return getPersistence().readByWorkflowCodeByUserIdentifier(workflowCode, userIdentifier);
	}

	@Override
	public Long countByWorkflowCodeByUserIdentifier(String workflowCode, String userIdentifier) {
		return getPersistence().countByWorkflowCodeByUserIdentifier(workflowCode, userIdentifier);
	}

	@Override
	public Collection<WorkflowProcessTask> findByWorkflowCodeByProcessCodeByUserIdentifier(String workflowCode,String processCode, String userIdentifier) {
		return getPersistence().readByWorkflowCodeByProcessCodeByUserIdentifier(workflowCode, processCode, userIdentifier);
	}

	@Override
	public Long countByWorkflowCodeByProcessCodeByUserIdentifier(String workflowCode, String processCode,String userIdentifier) {
		return getPersistence().countByWorkflowCodeByProcessCodeByUserIdentifier(workflowCode, processCode, userIdentifier);
	}

	@Override
	public Collection<WorkflowProcessTask> findByWorkflowProcessByUserIdentifier(WorkflowProcess workflowProcess,String userIdentifier) {
		return getPersistence().readByWorkflowProcessByUserIdentifier(workflowProcess, userIdentifier);
	}

	@Override
	public Long countByWorkflowProcessByUserIdentifier(WorkflowProcess workflowProcess, String userIdentifier) {
		return getPersistence().countByWorkflowProcessByUserIdentifier(workflowProcess, userIdentifier);
	}

	
}

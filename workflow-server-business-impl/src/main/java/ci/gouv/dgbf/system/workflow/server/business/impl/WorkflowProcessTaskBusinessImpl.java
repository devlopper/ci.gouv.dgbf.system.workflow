package ci.gouv.dgbf.system.workflow.server.business.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;

import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

import ci.gouv.dgbf.system.workflow.server.business.api.WorkflowProcessTaskBusiness;
import ci.gouv.dgbf.system.workflow.server.persistence.api.PersistenceHelper;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessTaskPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTask;

@Singleton
public class WorkflowProcessTaskBusinessImpl extends AbstractBusinessEntityImpl<WorkflowProcessTask,WorkflowProcessTaskPersistence> implements WorkflowProcessTaskBusiness,Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private PersistenceHelper persistenceHelper;
	
	@Override @Transactional
	public WorkflowProcessTaskBusiness start(WorkflowProcessTask workflowProcessTask, String userIdentifier) {
		persistenceHelper.getTaskService().start(workflowProcessTask.getIdentifier(), userIdentifier);
		return this;
	}

	@Override @Transactional
	public WorkflowProcessTaskBusiness complete(WorkflowProcessTask workflowProcessTask, String userIdentifier) {
		persistenceHelper.getTaskService().complete(workflowProcessTask.getIdentifier(), userIdentifier,null);
		return this;
	}

	@Override @Transactional
	public WorkflowProcessTaskBusiness execute(WorkflowProcessTask workflowProcessTask, String userIdentifier) {
		start(workflowProcessTask, userIdentifier);
		complete(workflowProcessTask, userIdentifier);
		return this;
	}
	
	@Override @Transactional
	public WorkflowProcessTaskBusiness execute(String workflowCode, String workflowProcessCode, String userIdentifier) {
		Collection<WorkflowProcessTask> workflowProcessTasks = getPersistence().readByWorkflowCodeByProcessCodeByUserIdentifier(workflowCode, workflowProcessCode, userIdentifier);
		if(workflowProcessTasks!=null)
			for(WorkflowProcessTask index : workflowProcessTasks)
				execute(index, userIdentifier);
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

	@Override
	protected Class<WorkflowProcessTask> __getEntityClass__() {
		return WorkflowProcessTask.class;
	}
}

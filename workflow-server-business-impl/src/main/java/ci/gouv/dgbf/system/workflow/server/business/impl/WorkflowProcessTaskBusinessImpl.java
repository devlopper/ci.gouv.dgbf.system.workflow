package ci.gouv.dgbf.system.workflow.server.business.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;

import org.cyk.utility.server.business.AbstractBusinessEntityImpl;
import org.kie.api.task.model.Status;

import ci.gouv.dgbf.system.workflow.server.business.api.WorkflowProcessTaskBusiness;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessTaskPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTask;
import ci.gouv.dgbf.system.workflow.server.persistence.impl.JbpmHelper;

@Singleton
public class WorkflowProcessTaskBusinessImpl extends AbstractBusinessEntityImpl<WorkflowProcessTask,WorkflowProcessTaskPersistence> implements WorkflowProcessTaskBusiness,Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private JbpmHelper jbpmHelper;
	
	@Override @Transactional
	public WorkflowProcessTaskBusiness start(WorkflowProcessTask workflowProcessTask, String userCode) {
		jbpmHelper.getRuntimeEngine().getTaskService().start(workflowProcessTask.getIdentifier(), userCode);
		return this;
	}

	@Override @Transactional
	public WorkflowProcessTaskBusiness complete(WorkflowProcessTask workflowProcessTask, String userCode) {
		jbpmHelper.getRuntimeEngine().getTaskService().complete(workflowProcessTask.getIdentifier(), userCode,null);
		return this;
	}

	@Override @Transactional
	public WorkflowProcessTaskBusiness execute(WorkflowProcessTask workflowProcessTask, String userCode) {
		start(workflowProcessTask, userCode);
		complete(workflowProcessTask, userCode);
		return this;
	}
	
	@Override @Transactional
	public WorkflowProcessTaskBusiness execute(String workflowCode, String workflowProcessCode, String userCode) {
		//TODO we need to handle other status like ready or created
		Collection<WorkflowProcessTask> workflowProcessTasks = getPersistence().readByWorkflowCodeByProcessCodeByUserCodeByStatusCode(workflowCode, workflowProcessCode, userCode,Status.Reserved.name());
		if(workflowProcessTasks!=null)
			for(WorkflowProcessTask index : workflowProcessTasks)
				execute(index, userCode);
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
	public Collection<WorkflowProcessTask> findByWorkflowCodeByUserIdentifier(String workflowCode,String userCode) {
		return getPersistence().readByWorkflowCodeByUserIdentifier(workflowCode, userCode);
	}

	@Override
	public Long countByWorkflowCodeByUserIdentifier(String workflowCode, String userCode) {
		return getPersistence().countByWorkflowCodeByUserIdentifier(workflowCode, userCode);
	}

	@Override
	public Collection<WorkflowProcessTask> findByWorkflowCodeByProcessCodeByUserIdentifier(String workflowCode,String processCode, String userCode) {
		return getPersistence().readByWorkflowCodeByProcessCodeByUserIdentifier(workflowCode, processCode, userCode);
	}

	@Override
	public Long countByWorkflowCodeByProcessCodeByUserIdentifier(String workflowCode, String processCode,String userCode) {
		return getPersistence().countByWorkflowCodeByProcessCodeByUserIdentifier(workflowCode, processCode, userCode);
	}

	@Override
	public Collection<WorkflowProcessTask> findByWorkflowProcessByUserIdentifier(WorkflowProcess workflowProcess,String userCode) {
		return getPersistence().readByWorkflowProcessByUserIdentifier(workflowProcess, userCode);
	}

	@Override
	public Long countByWorkflowProcessByUserIdentifier(WorkflowProcess workflowProcess, String userCode) {
		return getPersistence().countByWorkflowProcessByUserIdentifier(workflowProcess, userCode);
	}

	@Override
	protected Class<WorkflowProcessTask> __getEntityClass__() {
		return WorkflowProcessTask.class;
	}
}

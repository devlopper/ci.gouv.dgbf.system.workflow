package ci.gouv.dgbf.system.workflow.server.persistence.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.jbpm.services.api.model.UserTaskInstanceDesc;
import org.kie.api.task.model.TaskSummary;

import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessTaskPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTask;

public class WorkflowProcessTaskPersistenceImpl extends AbstractEntityPersistenceImpl<WorkflowProcessTask> implements WorkflowProcessTaskPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private WorkflowProcessPersistence workflowProcessPersistence;
	
	@Override
	public Collection<WorkflowProcessTask> readByWorkflowProcess(WorkflowProcess workflowProcess) {
		Collection<WorkflowProcessTask> workflowProcessTasks =  null;
		Collection<Long> identifiers = runtimeDataService.getTasksByProcessInstanceId(workflowProcess.getIdentifier());
		if(identifiers!=null){
			workflowProcessTasks = new ArrayList<>();
			for(Long index : identifiers){
				UserTaskInstanceDesc userTaskInstanceDesc = runtimeDataService.getTaskById(index);
				WorkflowProcessTask workflowProcessTask = new WorkflowProcessTask().setJbpmUserTaskInstanceDesc(userTaskInstanceDesc);
				workflowProcessTasks.add(workflowProcessTask);
			}
		}
		return workflowProcessTasks;
	}
	
	@Override
	public Long countByWorkflowProcess(WorkflowProcess workflowProcess) {
		Collection<WorkflowProcessTask> workflowProcessTasks = readByWorkflowProcess(workflowProcess);
		return workflowProcessTasks == null ? null : new Long(workflowProcessTasks.size());
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
	public Collection<WorkflowProcessTask> readByWorkflowProcessByUserIdentifier(WorkflowProcess workflowProcess,String userIdentifier) {
		List<TaskSummary> taskSummaries = getTaskSummaries(workflowProcess, userIdentifier);
		Collection<WorkflowProcessTask> workflowProcessTasks = new ArrayList<>();
		for(TaskSummary index : taskSummaries)
			workflowProcessTasks.add(new WorkflowProcessTask().setJbpmUserTaskInstanceDesc(null));
		return workflowProcessTasks;
	}
	
	@Override
	public Long countByWorkflowProcessByUserIdentifier(WorkflowProcess workflowProcess, String userIdentifier) {
		List<TaskSummary> taskSummaries = getTaskSummaries(workflowProcess, userIdentifier);
		return taskSummaries == null ? null : new Long(taskSummaries.size());
	}
	
	@Override
	public Collection<WorkflowProcessTask> readByWorkflowCodeByProcessCodeByUserIdentifier(String workflowCode,String processCode, String userIdentifier) {
		return readByWorkflowProcessByUserIdentifier(workflowProcessPersistence.readByWorkflowCodeByCode(workflowCode, processCode), userIdentifier);
	}
	
	@Override
	public Long countByWorkflowCodeByProcessCodeByUserIdentifier(String workflowCode, String processCode,String userIdentifier) {
		Long count = null;
		WorkflowProcess workflowProcess = workflowProcessPersistence.readByWorkflowCodeByCode(workflowCode, processCode);
		if(workflowProcess != null){
			List<TaskSummary> taskSummaries = getTaskSummaries(workflowProcess, userIdentifier);
			count = taskSummaries == null ? null : new Long(taskSummaries.size());	
		}
		return count;
	}
	
	/**/
	
	private List<TaskSummary> getTaskSummaries(Workflow workflow,String userIdentifier){
		String workflowIdentifier = businessProcessModelNotationHelper.getIdentifier(workflow.getModel());
		List<TaskSummary> taskSummaries = persistenceHelper.getRuntimeEngine().getTaskService().getTasksAssignedAsPotentialOwnerByProcessId(userIdentifier, workflowIdentifier);
		/*if(taskSummaries!=null){
			//workflowProcess.setProcessInstanceLog(persistenceHelper.getRuntimeEngine().getAuditService().findp);
			for(Integer index = 0; index < taskSummaries.size();){
				if(taskSummaries.get(index).getProcessInstanceId().equals(workflowProcess.getProcessInstance() == null ?
						workflowProcess.getProcessInstanceLog().getProcessInstanceId() : workflowProcess.getProcessInstance().getId()))
					index = index + 1;
				else
					taskSummaries.remove(index.intValue());
			}
		}*/
		return taskSummaries;
	}
	
	private List<TaskSummary> getTaskSummaries(WorkflowProcess workflowProcess,String userIdentifier){
		String workflowIdentifier = businessProcessModelNotationHelper.getIdentifier(workflowProcess.getWorkflow().getModel());
		List<TaskSummary> taskSummaries = persistenceHelper.getRuntimeEngine().getTaskService().getTasksAssignedAsPotentialOwnerByProcessId(userIdentifier, workflowIdentifier);
		/*if(taskSummaries!=null){
			//workflowProcess.setProcessInstanceLog(persistenceHelper.getRuntimeEngine().getAuditService().findp);
			for(Integer index = 0; index < taskSummaries.size();){
				if(taskSummaries.get(index).getProcessInstanceId().equals(workflowProcess.getProcessInstance() == null ?
						workflowProcess.getProcessInstanceLog().getProcessInstanceId() : workflowProcess.getProcessInstance().getId()))
					index = index + 1;
				else
					taskSummaries.remove(index.intValue());
			}
		}*/
		return taskSummaries;
	}
}

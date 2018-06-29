package ci.gouv.dgbf.system.workflow.business.api;

import java.util.Collection;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTask;

public interface WorkflowProcessTaskBusiness extends EntityBusiness<WorkflowProcessTask> {

	WorkflowProcessTaskBusiness start(WorkflowProcessTask workflowProcessTask,String userIdentifier);
	WorkflowProcessTaskBusiness complete(WorkflowProcessTask workflowProcessTask,String userIdentifier);
	
	WorkflowProcessTaskBusiness execute(WorkflowProcessTask workflowProcessTask,String userIdentifier);
	
	Collection<WorkflowProcessTask> findByWorkflowCode(String workflowCode);
	Long countByWorkflowCode(String workflowCode);
	
	Collection<WorkflowProcessTask> findByWorkflowCodeByProcessCode(String workflowCode,String processCode);
	Long countByWorkflowCodeByProcessCode(String workflowCode,String processCode);
	
	Collection<WorkflowProcessTask> findByWorkflowProcess(WorkflowProcess workflowProcess);
	Long countByWorkflowProcess(WorkflowProcess workflowProcess);
	
	Collection<WorkflowProcessTask> findByWorkflowCodeByUserIdentifier(String workflowCode,String userIdentifier);
	Long countByWorkflowCodeByUserIdentifier(String workflowCode,String userIdentifier);
	
	Collection<WorkflowProcessTask> findByWorkflowCodeByProcessCodeByUserIdentifier(String workflowCode,String processCode,String userIdentifier);
	Long countByWorkflowCodeByProcessCodeByUserIdentifier(String workflowCode,String processCode,String userIdentifier);
	
	Collection<WorkflowProcessTask> findByWorkflowProcessByUserIdentifier(WorkflowProcess workflowProcess,String userIdentifier);
	Long countByWorkflowProcessByUserIdentifier(WorkflowProcess workflowProcess,String userIdentifier);
}

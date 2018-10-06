package ci.gouv.dgbf.system.workflow.server.business.api;

import java.util.Collection;

import org.cyk.utility.server.business.BusinessEntity;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTask;

public interface WorkflowProcessTaskBusiness extends BusinessEntity<WorkflowProcessTask> {

	WorkflowProcessTaskBusiness start(WorkflowProcessTask workflowProcessTask,String userCode);
	WorkflowProcessTaskBusiness complete(WorkflowProcessTask workflowProcessTask,String userCode);
	
	WorkflowProcessTaskBusiness execute(WorkflowProcessTask workflowProcessTask,String userCode);
	WorkflowProcessTaskBusiness execute(String workflowCode,String workflowProcessCode,String userCode);
	
	Collection<WorkflowProcessTask> findByWorkflowCode(String workflowCode);
	Long countByWorkflowCode(String workflowCode);
	
	Collection<WorkflowProcessTask> findByWorkflowCodeByProcessCode(String workflowCode,String processCode);
	Long countByWorkflowCodeByProcessCode(String workflowCode,String processCode);
	
	Collection<WorkflowProcessTask> findByWorkflowProcess(WorkflowProcess workflowProcess);
	Long countByWorkflowProcess(WorkflowProcess workflowProcess);
	
	Collection<WorkflowProcessTask> findByWorkflowCodeByUserIdentifier(String workflowCode,String userCode);
	Long countByWorkflowCodeByUserIdentifier(String workflowCode,String userCode);
	
	Collection<WorkflowProcessTask> findByWorkflowCodeByProcessCodeByUserIdentifier(String workflowCode,String processCode,String userCode);
	Long countByWorkflowCodeByProcessCodeByUserIdentifier(String workflowCode,String processCode,String userCode);
	
	Collection<WorkflowProcessTask> findByWorkflowProcessByUserIdentifier(WorkflowProcess workflowProcess,String userCode);
	Long countByWorkflowProcessByUserIdentifier(WorkflowProcess workflowProcess,String userCode);
}

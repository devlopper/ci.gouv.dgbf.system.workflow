package ci.gouv.dgbf.system.workflow.server.persistence.api;

import java.util.Collection;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTask;

public interface WorkflowProcessTaskPersistence extends EntityPersistence<WorkflowProcessTask> {

	Collection<WorkflowProcessTask> readByWorkflowProcess(WorkflowProcess workflowProcess);
	Long countByWorkflowProcess(WorkflowProcess workflowProcess);
	
	Collection<WorkflowProcessTask> readByWorkflowCodeByProcessCode(String workflowCode,String processCode);
	Long countByWorkflowCodeByProcessCode(String workflowCode,String processCode);
	
	Collection<WorkflowProcessTask> readByWorkflowProcessByUserIdentifier(WorkflowProcess workflowProcess,String userIdentifier);
	Long countByWorkflowProcessByUserIdentifier(WorkflowProcess workflowProcess,String userIdentifier);
	
	Collection<WorkflowProcessTask> readByWorkflowCodeByProcessCodeByUserIdentifier(String workflowCode,String processCode,String userIdentifier);
	Long countByWorkflowCodeByProcessCodeByUserIdentifier(String workflowCode,String processCode,String userIdentifier);
	
}

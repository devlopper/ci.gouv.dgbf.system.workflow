package ci.gouv.dgbf.system.workflow.server.persistence.api;

import java.util.Collection;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTaskLog;

public interface WorkflowProcessTaskLogPersistence extends EntityPersistence<WorkflowProcessTaskLog> {

	Collection<WorkflowProcessTaskLog> readByWorkflowCode(String workflowCode);
	Long countByWorkflowCode(String workflowCode);
	
	Collection<WorkflowProcessTaskLog> readByWorkflowProcess(WorkflowProcess workflowProcess);
	Long countByWorkflowProcess(WorkflowProcess workflowProcess);
	
	Collection<WorkflowProcessTaskLog> readByWorkflowCodeByProcessCode(String workflowCode,String processCode);
	Long countByWorkflowCodeByProcessCode(String workflowCode,String processCode);
	
	Collection<WorkflowProcessTaskLog> readByWorkflowProcessByUserIdentifier(WorkflowProcess workflowProcess,String userIdentifier);
	Long countByWorkflowProcessByUserIdentifier(WorkflowProcess workflowProcess,String userIdentifier);
	
	Collection<WorkflowProcessTaskLog> readByWorkflowCodeByProcessCodeByUserIdentifier(String workflowCode,String processCode,String userIdentifier);
	Long countByWorkflowCodeByProcessCodeByUserIdentifier(String workflowCode,String processCode,String userIdentifier);
	
}

package ci.gouv.dgbf.system.workflow.server.persistence.api;

import java.util.Collection;

import org.cyk.utility.server.persistence.PersistenceEntity;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTask;

public interface WorkflowProcessTaskPersistence extends PersistenceEntity<WorkflowProcessTask> {

	Collection<WorkflowProcessTask> readByWorkflowCode(String workflowCode);
	Long countByWorkflowCode(String workflowCode);
	
	Collection<WorkflowProcessTask> readByWorkflowCodeByProcessCode(String workflowCode,String processCode);
	Long countByWorkflowCodeByProcessCode(String workflowCode,String processCode);
	
	Collection<WorkflowProcessTask> readByWorkflowProcess(WorkflowProcess workflowProcess);
	Long countByWorkflowProcess(WorkflowProcess workflowProcess);
	
	Collection<WorkflowProcessTask> readByWorkflowCodeByUserIdentifier(String workflowCode,String userIdentifier);
	Long countByWorkflowCodeByUserIdentifier(String workflowCode,String userIdentifier);
	
	Collection<WorkflowProcessTask> readByWorkflowCodeByProcessCodeByUserIdentifier(String workflowCode,String processCode,String userIdentifier);
	Long countByWorkflowCodeByProcessCodeByUserIdentifier(String workflowCode,String processCode,String userIdentifier);
	
	Collection<WorkflowProcessTask> readByWorkflowCodeByProcessCodeByUserCodeByStatusCode(String workflowCode,String processCode,String userCode,String statusCode);
	Long countByWorkflowCodeByProcessCodeByUserCodeByStatusCode(String workflowCode,String processCode,String userCode,String statusCode);
	
	Collection<WorkflowProcessTask> readByWorkflowProcessByUserIdentifier(WorkflowProcess workflowProcess,String userIdentifier);
	Long countByWorkflowProcessByUserIdentifier(WorkflowProcess workflowProcess,String userIdentifier);
	
	Collection<WorkflowProcessTask> readByUserCodeByStatusCodes(String userCode,Collection<String> statusCodes);
	Long countByUserCodeByStatusCodes(String userCode,Collection<String> statusCodes);
	
	Collection<WorkflowProcessTask> readByUserCodeByStatusCodes(String userCode,String...statusCodes);
	Long countByUserCodeByStatusCodes(String userCode,String...statusCodes);
}

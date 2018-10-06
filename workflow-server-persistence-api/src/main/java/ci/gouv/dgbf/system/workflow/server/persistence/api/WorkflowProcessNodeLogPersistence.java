package ci.gouv.dgbf.system.workflow.server.persistence.api;

import java.util.Collection;

import org.cyk.utility.server.persistence.PersistenceEntity;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessNodeLog;

public interface WorkflowProcessNodeLogPersistence extends PersistenceEntity<WorkflowProcessNodeLog> {

	Collection<WorkflowProcessNodeLog> readByWorkflowProcessIdentifier(Long workflowProcessIdentifier);
	Long countByWorkflowProcessIdentifier(Long workflowProcessIdentifier);
	
	Collection<WorkflowProcessNodeLog> readByWorkflowCodeByProcessCode(String workflowCode,String processCode);
	Long countByWorkflowCodeByProcessCode(String workflowCode,String processCode);
	
	Collection<WorkflowProcessNodeLog> readByWorkflowCodeByProcessCodeByCode(String workflowCode,String processCode,String code);
	Long countByWorkflowCodeByProcessCodeByCode(String workflowCode,String processCode,String code);
	
	Collection<WorkflowProcessNodeLog> readByWorkflowCodeByProcessCodeByWorkItemIdentifier(String workflowCode,String processCode,Long workItemIdentifier);
	Long countByWorkflowCodeByProcessCodeByWorkItemIdentifier(String workflowCode,String processCode,Long workItemIdentifier);
}

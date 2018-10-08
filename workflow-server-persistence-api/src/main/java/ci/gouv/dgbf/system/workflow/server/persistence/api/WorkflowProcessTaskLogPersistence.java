package ci.gouv.dgbf.system.workflow.server.persistence.api;

import java.util.Collection;

import org.cyk.utility.server.persistence.PersistenceEntity;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTaskLog;

public interface WorkflowProcessTaskLogPersistence extends PersistenceEntity<WorkflowProcessTaskLog> {

	Collection<WorkflowProcessTaskLog> readByWorkflowCode(String workflowCode);
	Long countByWorkflowCode(String workflowCode);
	
	Collection<WorkflowProcessTaskLog> readByWorkflowProcessIdentifier(Long workflowProcessIdentifier);
	Long countByWorkflowProcessIdentifier(Long workflowProcessIdentifier);
	
	Collection<WorkflowProcessTaskLog> readByWorkflowProcess(WorkflowProcess workflowProcess);
	Long countByWorkflowProcess(WorkflowProcess workflowProcess);
	
	Collection<WorkflowProcessTaskLog> readByWorkflowCodeByProcessCode(String workflowCode,String processCode);
	Long countByWorkflowCodeByProcessCode(String workflowCode,String processCode);
	
	Collection<WorkflowProcessTaskLog> readByWorkflowProcessByUserCode(WorkflowProcess workflowProcess,String userCode);
	Long countByWorkflowProcessByUserCode(WorkflowProcess workflowProcess,String userCode);
	
	Collection<WorkflowProcessTaskLog> readByWorkflowProcessIdentifierByUserCode(Long workflowProcessIdentifier,String userCode);
	Long countByWorkflowProcessIdentifierByUserCode(Long workflowProcessIdentifier,String userCode);
	
	Collection<WorkflowProcessTaskLog> readByWorkflowCodeByProcessCodeByUserCode(String workflowCode,String processCode,String userCode);
	Long countByWorkflowCodeByProcessCodeByUserCode(String workflowCode,String processCode,String userCode);
	
	Collection<WorkflowProcessTaskLog> readByWorkflowProcessIdentifierByUserCodeByStatusCodes(Long workflowProcessIdentifier,String userCode,Collection<String> statusCodes);
	Collection<WorkflowProcessTaskLog> readByWorkflowProcessIdentifierByUserCodeByStatusCodes(Long workflowProcessIdentifier,String userCode,String...statusCodes);
	Long countByWorkflowProcessIdentifierByUserCodeByStatusCodes(Long workflowProcessIdentifier,String userCode,Collection<String> statusCodes);
	Long countByWorkflowProcessIdentifierByUserCodeByStatusCodes(Long workflowProcessIdentifier,String userCode,String...statusCodes);
	
	Collection<WorkflowProcessTaskLog> readByWorkflowCodeByProcessCodeByUserCodeByStatusCodes(String workflowCode,String processCode,String userCode,Collection<String> statusCodes);
	Collection<WorkflowProcessTaskLog> readByWorkflowCodeByProcessCodeByUserCodeByStatusCodes(String workflowCode,String processCode,String userCode,String...statusCodes);
	Long countByWorkflowCodeByProcessCodeByUserCodeByStatusCodes(String workflowCode,String processCode,String userCode,Collection<String> statusCodes);
	Long countByWorkflowCodeByProcessCodeByUserCodeByStatusCodes(String workflowCode,String processCode,String userCode,String...statusCodes);

}

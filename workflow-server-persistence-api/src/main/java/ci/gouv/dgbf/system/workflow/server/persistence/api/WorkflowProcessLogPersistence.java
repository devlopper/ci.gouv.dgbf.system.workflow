package ci.gouv.dgbf.system.workflow.server.persistence.api;

import java.util.Collection;

import org.cyk.utility.server.persistence.PersistenceEntity;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessLog;

public interface WorkflowProcessLogPersistence extends PersistenceEntity<WorkflowProcessLog> {

	Collection<WorkflowProcessLog> readByWorkflow(Workflow workflow);
	Long countByWorkflow(Workflow workflow);
	
	Collection<WorkflowProcessLog> readByWorkflowCode(String workflowCode);
	Long countByWorkflowCode(String workflowCode);
	
	WorkflowProcessLog readByWorkflowByProcessCode(Workflow workflow,String processCode);
	WorkflowProcessLog readByWorkflowCodeByProcessCode(String workflowCode,String processCode);
	
}

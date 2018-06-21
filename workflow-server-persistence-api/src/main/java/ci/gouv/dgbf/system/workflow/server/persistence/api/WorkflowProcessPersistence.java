package ci.gouv.dgbf.system.workflow.server.persistence.api;

import java.util.Collection;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;

public interface WorkflowProcessPersistence extends EntityPersistence<WorkflowProcess> {

	Collection<WorkflowProcess> readByWorkflow(Workflow workflow);
	Long countByWorkflow(Workflow workflow);
	
	Collection<WorkflowProcess> readByWorkflowCode(String workflowCode);
	Long countByWorkflowCode(String workflowCode);
	
	WorkflowProcess readByWorkflowByCode(Workflow workflow,String code);
	
}

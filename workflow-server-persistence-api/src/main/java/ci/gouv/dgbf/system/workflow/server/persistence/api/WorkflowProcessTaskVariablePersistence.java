package ci.gouv.dgbf.system.workflow.server.persistence.api;

import java.util.Collection;

import org.cyk.utility.server.persistence.PersistenceEntity;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTaskVariable;

public interface WorkflowProcessTaskVariablePersistence extends PersistenceEntity<WorkflowProcessTaskVariable> {

	Collection<WorkflowProcessTaskVariable> readByWorkflowProcessIdentifier(Long workflowProcessIdentifier);
	Long countByWorkflowProcessIdentifier(Long workflowProcessIdentifier);
	
	Collection<WorkflowProcessTaskVariable> readByWorkflowCodeByCode(String workflowCode,String code);
	Long countByWorkflowCodeByCode(String workflowCode,String code);
}

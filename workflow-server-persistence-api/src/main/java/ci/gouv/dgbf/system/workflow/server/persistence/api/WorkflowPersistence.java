package ci.gouv.dgbf.system.workflow.server.persistence.api;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;

public interface WorkflowPersistence extends EntityPersistence<Workflow> {

	Workflow readByCode(String code);
	WorkflowPersistence deleteByCode(String code);
}

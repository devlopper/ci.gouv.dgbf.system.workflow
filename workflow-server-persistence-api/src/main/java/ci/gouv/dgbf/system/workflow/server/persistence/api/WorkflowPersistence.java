package ci.gouv.dgbf.system.workflow.server.persistence.api;

import org.cyk.utility.server.persistence.PersistenceEntity;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;

public interface WorkflowPersistence extends PersistenceEntity<Workflow> {

	Workflow readByCode(String code);
	WorkflowPersistence deleteByCode(String code);
}

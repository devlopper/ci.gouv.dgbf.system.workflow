package ci.gouv.dgbf.system.workflow.business.api;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;

public interface WorkflowBusiness extends EntityBusiness<Workflow> {

	Workflow findByCode(String code);
	WorkflowBusiness deleteByCode(String code);
	
}

package ci.gouv.dgbf.system.workflow.server.business.api;

import org.cyk.utility.server.business.BusinessEntity;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;

public interface WorkflowBusiness extends BusinessEntity<Workflow> {

	Workflow findByCode(String code);
	//TODO should be removed and use delete(identifier,valueusagetype)
	WorkflowBusiness deleteByCode(String code);
	
}

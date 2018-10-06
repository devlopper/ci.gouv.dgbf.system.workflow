package ci.gouv.dgbf.system.workflow.server.business.api;

import org.cyk.utility.server.business.BusinessEntity;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;

public interface WorkflowBusiness extends BusinessEntity<Workflow> {

	WorkflowBusiness synchroniseWithJbpmMavenRepository();
}

package ci.gouv.dgbf.system.workflow.server.business.api;

import org.cyk.utility.server.business.BusinessEntity;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;

public interface WorkflowProcessBusiness extends BusinessEntity<WorkflowProcess> {

	WorkflowProcess findByWorkflowCodeByCode(String workflowCode, String code);

	WorkflowProcessBusiness deleteByWorkflowCodeByCode(String workflowCode, String code);

}

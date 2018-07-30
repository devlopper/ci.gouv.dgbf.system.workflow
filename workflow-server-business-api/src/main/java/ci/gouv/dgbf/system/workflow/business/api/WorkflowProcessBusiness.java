package ci.gouv.dgbf.system.workflow.business.api;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;

public interface WorkflowProcessBusiness extends EntityBusiness<WorkflowProcess> {

	WorkflowProcess findByWorkflowCodeByCode(String workflowCode, String code);

	WorkflowProcessBusiness deleteByWorkflowCodeByCode(String workflowCode, String code);

}

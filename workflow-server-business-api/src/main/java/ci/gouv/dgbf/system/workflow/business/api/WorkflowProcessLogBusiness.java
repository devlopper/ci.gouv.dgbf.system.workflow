package ci.gouv.dgbf.system.workflow.business.api;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessLog;

public interface WorkflowProcessLogBusiness extends EntityBusiness<WorkflowProcessLog> {

	WorkflowProcess findByWorkflowCodeByCode(String workflowCode, String code);

	WorkflowProcessBusiness deleteByWorkflowCodeByCode(String workflowCode, String code);

}

package ci.gouv.dgbf.system.workflow.server.business.api;

import org.cyk.utility.server.business.BusinessEntity;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessLog;

public interface WorkflowProcessLogBusiness extends BusinessEntity<WorkflowProcessLog> {

	WorkflowProcess findByWorkflowCodeByCode(String workflowCode, String code);

	WorkflowProcessBusiness deleteByWorkflowCodeByCode(String workflowCode, String code);

}

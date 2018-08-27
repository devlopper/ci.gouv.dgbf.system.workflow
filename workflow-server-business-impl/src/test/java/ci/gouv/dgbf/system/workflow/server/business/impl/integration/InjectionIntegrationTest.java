package ci.gouv.dgbf.system.workflow.server.business.impl.integration;

import org.cyk.utility.server.business.BusinessLayer;
import org.cyk.utility.server.business.test.arquillian.AbstractBusinessArquillianIntegrationTestWithDefaultDeploymentAsSwram;
import org.junit.Test;

import ci.gouv.dgbf.system.workflow.server.business.api.WorkflowBusiness;
import ci.gouv.dgbf.system.workflow.server.business.api.WorkflowProcessBusiness;
import ci.gouv.dgbf.system.workflow.server.business.api.WorkflowProcessTaskBusiness;
import ci.gouv.dgbf.system.workflow.server.business.api.WorkflowProcessTaskLogBusiness;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTask;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTaskLog;

public class InjectionIntegrationTest extends AbstractBusinessArquillianIntegrationTestWithDefaultDeploymentAsSwram {
	private static final long serialVersionUID = 1L;

	@Test
	public void workflow_persistence_is_WorkflowBusinessImpl() {
		assertionHelper.assertTrue(__inject__(BusinessLayer.class).injectInterfaceClassFromPersistenceEntityClass(Workflow.class) instanceof WorkflowBusiness);
	}
	
	@Test
	public void workflowProcess_persistence_is_WorkflowProcessBusinessImpl() {
		assertionHelper.assertTrue(__inject__(BusinessLayer.class).injectInterfaceClassFromPersistenceEntityClass(WorkflowProcess.class) instanceof WorkflowProcessBusiness);
	}
	
	@Test
	public void workflowProcessTask_persistence_is_WorkflowProcessTaskBusinessImpl() {
		assertionHelper.assertTrue(__inject__(BusinessLayer.class).injectInterfaceClassFromPersistenceEntityClass(WorkflowProcessTask.class) instanceof WorkflowProcessTaskBusiness);
	}
	
	@Test
	public void workflowProcessTaskLog_persistence_is_WorkflowProcessTaskLogBusinessImpl() {
		assertionHelper.assertTrue(__inject__(BusinessLayer.class).injectInterfaceClassFromPersistenceEntityClass(WorkflowProcessTaskLog.class) instanceof WorkflowProcessTaskLogBusiness);
	}
	
}

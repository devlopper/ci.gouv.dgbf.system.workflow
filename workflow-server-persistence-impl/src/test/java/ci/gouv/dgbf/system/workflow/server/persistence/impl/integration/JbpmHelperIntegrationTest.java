package ci.gouv.dgbf.system.workflow.server.persistence.impl.integration;

import javax.inject.Inject;

import org.cyk.utility.server.persistence.test.arquillian.AbstractPersistenceArquillianIntegrationTestWithDefaultDeploymentAsSwram;
import org.junit.Test;

import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.persistence.impl.JbpmHelper;

public class JbpmHelperIntegrationTest extends AbstractPersistenceArquillianIntegrationTestWithDefaultDeploymentAsSwram {
	private static final long serialVersionUID = 1L;
	
	@Inject private JbpmHelper jbpmHelper;
	
	@Test
	public void createProcessInstanceWithOneProcessIdFound() throws Exception{
		Workflow workflow = new Workflow().setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2");
		userTransaction.begin();
		__inject__(WorkflowPersistence.class).create(workflow);
		userTransaction.commit();
		jbpmHelper.destroyRuntimeEnvironment();
		jbpmHelper.getRuntimeEngine().getKieSession().startProcess("ci.gouv.dgbf.workflow.validation.pap");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void createProcessInstanceWithNoProcessIdFound() throws Exception{
		jbpmHelper.getRuntimeEngine().getKieSession().startProcess("myprocessid");
	}
	
	
	
}

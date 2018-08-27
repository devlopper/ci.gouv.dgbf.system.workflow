package ci.gouv.dgbf.system.workflow.server.business.impl.integration;

import org.cyk.utility.server.business.test.arquillian.AbstractBusinessEntityIntegrationTestWithDefaultDeploymentAsSwram;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;

public class WorkflowBusinessImplIntegrationTest extends AbstractBusinessEntityIntegrationTestWithDefaultDeploymentAsSwram<Workflow> {
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T __instanciate__(Class<T> aClass, Object action) throws Exception {
		return (T) new Workflow().setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2");
	}
	
	@Override public void createMany() throws Exception {}
	
	@Override public void updateOne() throws Exception {}
	
}

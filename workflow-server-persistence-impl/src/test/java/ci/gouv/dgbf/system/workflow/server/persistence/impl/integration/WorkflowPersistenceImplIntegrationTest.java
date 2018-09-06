package ci.gouv.dgbf.system.workflow.server.persistence.impl.integration;

import javax.inject.Inject;

import org.cyk.utility.server.persistence.test.arquillian.AbstractPersistenceEntityIntegrationTestWithDefaultDeploymentAsSwram;
import org.junit.Assert;
import org.junit.Test;

import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;

public class WorkflowPersistenceImplIntegrationTest extends AbstractPersistenceEntityIntegrationTestWithDefaultDeploymentAsSwram<Workflow> {
	private static final long serialVersionUID = 1L;
	
	@Inject private WorkflowPersistence workflowPersistence;
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T __instanciate__(Class<T> aClass, Object action) throws Exception {
		Workflow workflow = (Workflow) super.__instanciate__(aClass,action);
		workflow.setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2");
		return (T) workflow;
	}
	
	@Override public void createMany() throws Exception {}
	
	@Test
	public void createWorkflow() throws Exception{
		Workflow workflow = new Workflow().setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2");
		Assert.assertNotNull(workflow.getModel());
		Assert.assertNull(workflow.getIdentifier());
		Assert.assertEquals(new Long(0), workflowPersistence.count());
		userTransaction.begin();
		workflowPersistence.create(workflow);
		userTransaction.commit();
		Assert.assertNotNull(workflow.getIdentifier());
		Assert.assertEquals("ci.gouv.dgbf.workflow.validation.pap", workflow.getCode());
		Assert.assertEquals("Validation du PAP", workflow.getName());
		Assert.assertEquals(new Long(1), workflowPersistence.count());
		
		/* cleaning */
		userTransaction.begin();
		workflowPersistence.deleteByCode("ci.gouv.dgbf.workflow.validation.pap");
		userTransaction.commit();
		
	}
	
	@Test
	public void readWorkflowByCode() throws Exception{
		userTransaction.begin();
		workflowPersistence.create(new Workflow().setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2"));
		userTransaction.commit();
		Workflow workflow = workflowPersistence.readOneByBusinessIdentifier("ci.gouv.dgbf.workflow.validation.pap");
		Assert.assertNotNull(workflow);
		Assert.assertNotNull(workflow.getIdentifier());
		//Assert.assertNotNull(workflow.getJbpmProcessDefinition());
		Assert.assertEquals("ci.gouv.dgbf.workflow.validation.pap", workflow.getCode());
		Assert.assertNotNull(workflow.getModel());
		
		/* cleaning */
		userTransaction.begin();
		workflowPersistence.deleteByCode("ci.gouv.dgbf.workflow.validation.pap");
		userTransaction.commit();
	}
	
	//@Test TODO how update should work ???
	public void updateWorkflow() throws Exception{
		userTransaction.begin();
		workflowPersistence.create(new Workflow().setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2"));
		userTransaction.commit();
		Workflow workflow = workflowPersistence.readOneByBusinessIdentifier("ci.gouv.dgbf.workflow.validation.pap");
		Assert.assertEquals("Validation du PAP", workflow.getName());
		
		workflow.setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP V01.bpmn2");
		userTransaction.begin();
		workflowPersistence.update(workflow);
		userTransaction.commit();
		
		workflow = workflowPersistence.readOneByBusinessIdentifier("ci.gouv.dgbf.workflow.validation.pap");
		Assert.assertEquals("Validation du PAP V01", workflow.getName());
		
		/* cleaning */
		userTransaction.begin();
		workflowPersistence.deleteByCode("ci.gouv.dgbf.workflow.validation.pap");
		userTransaction.commit();
	}
	
	@Test
	public void deleteWorkflowByCode() throws Exception{
		Assert.assertEquals(new Long(0), workflowPersistence.count());
		userTransaction.begin();
		workflowPersistence.create(new Workflow().setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2"));
		userTransaction.commit();
		
		Assert.assertEquals(new Long(1), workflowPersistence.count());
		
		/* cleaning */
		userTransaction.begin();
		workflowPersistence.deleteByCode("ci.gouv.dgbf.workflow.validation.pap");
		userTransaction.commit();
		
		Assert.assertEquals(new Long(0), workflowPersistence.count());
	}
	
	@Test
	public void createWorkflowWithCodeDifferentToModelId() throws Exception{
		userTransaction.begin();
		workflowPersistence.create(new Workflow().setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2").setCode("mycode"));
		userTransaction.commit();
		
		/* cleaning */
		userTransaction.begin();
		workflowPersistence.deleteByCode("mycode");
		userTransaction.commit();
	}
	
}

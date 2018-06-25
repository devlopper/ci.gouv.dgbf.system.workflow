package ci.gouv.dgbf.system.workflow.server.persistence.impl.integration;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;

public class WorkflowPersistenceImplIntegrationTest extends AbstractIntegrationTest {

	@Inject private WorkflowPersistence workflowPersistence;
	
	@Test
	public void createWorkflow() throws Exception{
		Workflow workflow = new Workflow().setCode("VPAP").setName("Validation PAP").setModelAsBpmnFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2");
		Assert.assertNotNull(workflow.getModelAsBpmn());
		Assert.assertNull(workflow.getIdentifier());
		Assert.assertEquals(new Long(0), workflowPersistence.countAll());
		userTransaction.begin();
		workflowPersistence.create(workflow);
		userTransaction.commit();
		Assert.assertNotNull(workflow.getIdentifier());
		Assert.assertEquals(new Long(1), workflowPersistence.countAll());
		
		/* cleaning */
		userTransaction.begin();
		workflowPersistence.deleteByCode("VPAP");
		userTransaction.commit();
		
	}
	
	@Test
	public void readWorkflowByCode() throws Exception{
		userTransaction.begin();
		workflowPersistence.create(new Workflow().setCode("VPAP").setName("Validation PAP").setModelAsBpmnFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2"));
		userTransaction.commit();
		Workflow workflow = workflowPersistence.readByCode("VPAP");
		Assert.assertNotNull(workflow);
		Assert.assertNotNull(workflow.getIdentifier());
		Assert.assertEquals("VPAP", workflow.getCode());
		Assert.assertEquals("Validation PAP", workflow.getName());
		Assert.assertNotNull(workflow.getModelAsBpmn());
		
		/* cleaning */
		userTransaction.begin();
		workflowPersistence.deleteByCode("VPAP");
		userTransaction.commit();
	}
	
	@Test
	public void updateWorkflow() throws Exception{
		userTransaction.begin();
		workflowPersistence.create(new Workflow().setCode("VPAP").setName("Validation PAP").setModelAsBpmnFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2"));
		userTransaction.commit();
		Workflow workflow = workflowPersistence.readByCode("VPAP");
		Assert.assertNotNull(workflow);
		Assert.assertNotNull(workflow.getIdentifier());
		Assert.assertEquals("VPAP", workflow.getCode());
		Assert.assertEquals("Validation PAP", workflow.getName());
		Assert.assertNotNull(workflow.getModelAsBpmn());
		
		workflow.setName("NewName");
		userTransaction.begin();
		workflowPersistence.update(workflow);
		userTransaction.commit();
		
		workflow = workflowPersistence.readByCode("VPAP");
		Assert.assertNotNull(workflow);
		Assert.assertNotNull(workflow.getIdentifier());
		Assert.assertEquals("VPAP", workflow.getCode());
		Assert.assertEquals("NewName", workflow.getName());
		Assert.assertNotNull(workflow.getModelAsBpmn());
		
		/* cleaning */
		userTransaction.begin();
		workflowPersistence.deleteByCode("VPAP");
		userTransaction.commit();
	}
	
	@Test
	public void deleteWorkflowByCode() throws Exception{
		Assert.assertEquals(new Long(0), workflowPersistence.countAll());
		userTransaction.begin();
		workflowPersistence.create(new Workflow().setCode("VPAP").setName("Validation PAP").setModelAsBpmnFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2"));
		userTransaction.commit();
		
		Assert.assertEquals(new Long(1), workflowPersistence.countAll());
		
		/* cleaning */
		userTransaction.begin();
		workflowPersistence.deleteByCode("VPAP");
		userTransaction.commit();
		
		Assert.assertEquals(new Long(0), workflowPersistence.countAll());
	}
	
}

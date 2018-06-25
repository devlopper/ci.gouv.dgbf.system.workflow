package ci.gouv.dgbf.system.workflow.server.persistence.impl.integration;

import javax.inject.Inject;

import org.jboss.arquillian.junit.InSequence;
import org.junit.Assert;
import org.junit.Test;

import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;

public class WorkflowProcessPersistenceImplIntegrationTest extends AbstractIntegrationTest {

	@Inject private WorkflowPersistence workflowPersistence;
	@Inject private WorkflowProcessPersistence workflowProcessPersistence;
	
	@Test @InSequence(1)
	public void createWorkflowProcess() throws Exception{
		userTransaction.begin();
		workflowPersistence.create(new Workflow().setCode("VPAP").setName("Validation PAP").setModelAsBpmnFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2"));
		workflowPersistence.create(new Workflow().setCode("VPAP01").setName("Validation PAP").setModelAsBpmnFromResourceAsStream("/bpmn/withhuman/Validation du PAP V01.bpmn2"));
		userTransaction.commit();
		
		Assert.assertNull(workflowProcessPersistence.readByWorkflowCodeByCode("VPAP", "PAP001"));
		Assert.assertNull(workflowProcessPersistence.readByWorkflowCodeByCode("VPAP", "PAP002"));
		Assert.assertNull(workflowProcessPersistence.readByWorkflowCodeByCode("VPAP01", "PAP001"));
		Assert.assertEquals(new Long(0), workflowProcessPersistence.countByWorkflowCode("VPAP"));
		Assert.assertEquals(new Long(0), workflowProcessPersistence.countByWorkflowCode("VPAP01"));
		
		workflowProcessPersistence.create(new WorkflowProcess().setWorkflow(workflowPersistence.readByCode("VPAP")).setCode("PAP001"));
		
		Assert.assertNotNull(workflowProcessPersistence.readByWorkflowCodeByCode("VPAP", "PAP001"));
		Assert.assertNull(workflowProcessPersistence.readByWorkflowCodeByCode("VPAP", "PAP002"));
		Assert.assertNull(workflowProcessPersistence.readByWorkflowCodeByCode("VPAP01", "PAP001"));
		Assert.assertEquals(new Long(1), workflowProcessPersistence.countByWorkflowCode("VPAP"));
		Assert.assertEquals(new Long(0), workflowProcessPersistence.countByWorkflowCode("VPAP01"));
		
		workflowProcessPersistence.create(new WorkflowProcess().setWorkflow(workflowPersistence.readByCode("VPAP")).setCode("PAP002"));
		
		Assert.assertNotNull(workflowProcessPersistence.readByWorkflowCodeByCode("VPAP", "PAP001"));
		Assert.assertNotNull(workflowProcessPersistence.readByWorkflowCodeByCode("VPAP", "PAP002"));
		Assert.assertNull(workflowProcessPersistence.readByWorkflowCodeByCode("VPAP01", "PAP001"));
		Assert.assertEquals(new Long(2), workflowProcessPersistence.countByWorkflowCode("VPAP"));
		Assert.assertEquals(new Long(0), workflowProcessPersistence.countByWorkflowCode("VPAP01"));
		
		workflowProcessPersistence.create(new WorkflowProcess().setWorkflow(workflowPersistence.readByCode("VPAP01")).setCode("PAP001"));
		
		Assert.assertNotNull(workflowProcessPersistence.readByWorkflowCodeByCode("VPAP", "PAP001"));
		Assert.assertNotNull(workflowProcessPersistence.readByWorkflowCodeByCode("VPAP", "PAP002"));
		Assert.assertNotNull(workflowProcessPersistence.readByWorkflowCodeByCode("VPAP01", "PAP001"));
		Assert.assertEquals(new Long(2), workflowProcessPersistence.countByWorkflowCode("VPAP"));
		Assert.assertEquals(new Long(1), workflowProcessPersistence.countByWorkflowCode("VPAP01"));
		
	}
	
}

package ci.gouv.dgbf.system.workflow.server.persistence.impl.integration;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessTaskPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;

public class WorkflowProcessTaskPersistenceImplIntegrationTest extends AbstractIntegrationTest {

	@Inject private WorkflowPersistence workflowPersistence;
	@Inject private WorkflowProcessPersistence workflowProcessPersistence;
	@Inject private WorkflowProcessTaskPersistence workflowProcessTaskPersistence;
		
	@Test
	public void readByWorkflowByUserIdentifier() throws Exception{
		userTransaction.begin();
		workflowPersistence.create(new Workflow().setCode("VPAP").setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2"));
		workflowPersistence.create(new Workflow().setCode("VPAP01").setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP V01.bpmn2"));
		userTransaction.commit();
		
		workflowProcessPersistence.create(new WorkflowProcess().setCode("PAP001").setWorkflow(workflowPersistence.readByCode("VPAP")));
		
		Assert.assertNotNull(workflowProcessPersistence.readByWorkflowCodeByCode("VPAP","PAP001"));
		Assert.assertEquals(new Long(0), workflowProcessTaskPersistence.countByWorkflowCodeByProcessCode("VPAP","PAP001"));
		
		/*
		Assert.assertEquals(new Long(1), workflowProcessTaskPersistence.countByWorkflowCodeByProcessCodeByUserIdentifier("VPAP","PAP001", "charge_etude"));
		Assert.assertEquals(null, workflowProcessTaskPersistence.countByWorkflowCodeByProcessCodeByUserIdentifier("VPAP01","PAP001", "charge_etude"));
		
		workflowProcessPersistence.create(new WorkflowProcess().setCode("PAP002").setWorkflow(workflowPersistence.readByCode("VPAP")));
		
		Assert.assertNotNull(workflowProcessPersistence.readByWorkflowCodeByCode("VPAP","PAP002"));
		Assert.assertEquals(new Long(1), workflowProcessTaskPersistence.countByWorkflowCodeByProcessCodeByUserIdentifier("VPAP","PAP002", "charge_etude"));
		Assert.assertEquals(null, workflowProcessTaskPersistence.countByWorkflowCodeByProcessCodeByUserIdentifier("VPAP01","PAP001", "charge_etude"));
		
		workflowProcessPersistence.create(new WorkflowProcess().setCode("PAP003").setWorkflow(workflowPersistence.readByCode("VPAP")));
		
		Assert.assertNotNull(workflowProcessPersistence.readByWorkflowCodeByCode("VPAP","PAP003"));
		Assert.assertEquals(new Long(1), workflowProcessTaskPersistence.countByWorkflowCodeByProcessCodeByUserIdentifier("VPAP","PAP003", "charge_etude"));
		Assert.assertEquals(null, workflowProcessTaskPersistence.countByWorkflowCodeByProcessCodeByUserIdentifier("VPAP01","PAP001", "charge_etude"));
		
		workflowProcessPersistence.create(new WorkflowProcess().setCode("PAP001").setWorkflow(workflowPersistence.readByCode("VPAP01")));
		
		Assert.assertNotNull(workflowProcessPersistence.readByWorkflowCodeByCode("VPAP","PAP003"));
		Assert.assertEquals(new Long(1), workflowProcessTaskPersistence.countByWorkflowCodeByProcessCodeByUserIdentifier("VPAP01","PAP001", "charge_etude"));
		*/
	}
	
}

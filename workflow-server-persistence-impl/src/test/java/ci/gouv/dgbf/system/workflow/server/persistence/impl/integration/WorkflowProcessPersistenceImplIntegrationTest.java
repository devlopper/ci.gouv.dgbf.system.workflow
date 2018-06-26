package ci.gouv.dgbf.system.workflow.server.persistence.impl.integration;

import java.util.ArrayList;
import java.util.List;

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
		workflowPersistence.create(new Workflow().setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2"));
		workflowPersistence.create(new Workflow().setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP V01.bpmn2"));
		userTransaction.commit();
		
		Assert.assertNull(workflowProcessPersistence.readByWorkflowCodeByCode("ci.gouv.dgbf.workflow.validation.pap", "PAP001"));
		Assert.assertNull(workflowProcessPersistence.readByWorkflowCodeByCode("ci.gouv.dgbf.workflow.validation.pap", "PAP002"));
		Assert.assertNull(workflowProcessPersistence.readByWorkflowCodeByCode("ci.gouv.dgbf.workflow.validation.pap.v01", "PAP001"));
		Assert.assertEquals(new Long(0), workflowProcessPersistence.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap"));
		Assert.assertEquals(new Long(0), workflowProcessPersistence.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap.v01"));
		
		workflowProcessPersistence.create(new WorkflowProcess().setWorkflow(workflowPersistence.readByCode("ci.gouv.dgbf.workflow.validation.pap")).setCode("PAP001"));
		
		Assert.assertNotNull(workflowProcessPersistence.readByWorkflowCodeByCode("ci.gouv.dgbf.workflow.validation.pap", "PAP001"));
		Assert.assertNotNull(workflowProcessPersistence.readByWorkflowCodeByCode("ci.gouv.dgbf.workflow.validation.pap", "PAP001").getWorkflow());
		Assert.assertEquals("ci.gouv.dgbf.workflow.validation.pap", workflowProcessPersistence.readByWorkflowCodeByCode("ci.gouv.dgbf.workflow.validation.pap", "PAP001").getWorkflow().getCode());
		Assert.assertEquals("PAP001", workflowProcessPersistence.readByWorkflowCodeByCode("ci.gouv.dgbf.workflow.validation.pap", "PAP001").getCode());
		Assert.assertNull(workflowProcessPersistence.readByWorkflowCodeByCode("ci.gouv.dgbf.workflow.validation.pap", "PAP002"));
		Assert.assertNull(workflowProcessPersistence.readByWorkflowCodeByCode("ci.gouv.dgbf.workflow.validation.pap.v01", "PAP001"));
		Assert.assertEquals(new Long(1), workflowProcessPersistence.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap"));
		Assert.assertEquals(new Long(0), workflowProcessPersistence.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap.v01"));
		
		workflowProcessPersistence.create(new WorkflowProcess().setWorkflow(workflowPersistence.readByCode("ci.gouv.dgbf.workflow.validation.pap")).setCode("PAP002"));
		
		Assert.assertNotNull(workflowProcessPersistence.readByWorkflowCodeByCode("ci.gouv.dgbf.workflow.validation.pap", "PAP001"));
		Assert.assertNotNull(workflowProcessPersistence.readByWorkflowCodeByCode("ci.gouv.dgbf.workflow.validation.pap", "PAP002"));
		Assert.assertNull(workflowProcessPersistence.readByWorkflowCodeByCode("ci.gouv.dgbf.workflow.validation.pap.v01", "PAP001"));
		Assert.assertEquals(new Long(2), workflowProcessPersistence.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap"));
		Assert.assertEquals(new Long(0), workflowProcessPersistence.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap.v01"));
		
		workflowProcessPersistence.create(new WorkflowProcess().setWorkflow(workflowPersistence.readByCode("ci.gouv.dgbf.workflow.validation.pap.v01")).setCode("PAP001"));
		
		Assert.assertNotNull(workflowProcessPersistence.readByWorkflowCodeByCode("ci.gouv.dgbf.workflow.validation.pap", "PAP001"));
		Assert.assertNotNull(workflowProcessPersistence.readByWorkflowCodeByCode("ci.gouv.dgbf.workflow.validation.pap", "PAP002"));
		Assert.assertNotNull(workflowProcessPersistence.readByWorkflowCodeByCode("ci.gouv.dgbf.workflow.validation.pap.v01", "PAP001"));
		Assert.assertEquals(new Long(2), workflowProcessPersistence.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap"));
		Assert.assertEquals(new Long(1), workflowProcessPersistence.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap.v01"));
		
		List<WorkflowProcess> workflowProcesses = new ArrayList<>(workflowProcessPersistence.readByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap"));
		Assert.assertEquals(2, workflowProcesses.size());
		Assert.assertNotNull(workflowProcesses.get(0).getWorkflow());
		Assert.assertEquals("ci.gouv.dgbf.workflow.validation.pap", workflowProcesses.get(0).getWorkflow().getCode());
		Assert.assertEquals("PAP001", workflowProcesses.get(0).getCode());
	}
	
}

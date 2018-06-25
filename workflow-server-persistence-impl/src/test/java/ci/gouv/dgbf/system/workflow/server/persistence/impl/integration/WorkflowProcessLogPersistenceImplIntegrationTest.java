package ci.gouv.dgbf.system.workflow.server.persistence.impl.integration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessLogPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessLog;

public class WorkflowProcessLogPersistenceImplIntegrationTest extends AbstractIntegrationTest {

	@Inject private WorkflowPersistence workflowPersistence;
	@Inject private WorkflowProcessPersistence workflowProcessPersistence;
	@Inject private WorkflowProcessLogPersistence workflowProcessLogPersistence;
	
	@Test
	public void createWorkflowProcess() throws Exception{
		userTransaction.begin();
		workflowPersistence.create(new Workflow().setCode("VPAP").setModelAsBpmnFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2"));
		workflowPersistence.create(new Workflow().setCode("VPAP01").setModelAsBpmnFromResourceAsStream("/bpmn/withhuman/Validation du PAP V01.bpmn2"));
		userTransaction.commit();
		
		workflowProcessPersistence.create(new WorkflowProcess().setWorkflow(workflowPersistence.readByCode("VPAP")).setCode("PAP001"));
		
		WorkflowProcessLog workflowProcessLog = workflowProcessLogPersistence.readByWorkflowCodeByProcessCode("VPAP", "PAP001");
		
		Assert.assertNotNull(workflowProcessLog);
		Assert.assertNotNull(workflowProcessLog.getWorkflowProcess());
		Assert.assertNotNull(workflowProcessLog.getJbpmProcessInstanceLog());
		
		workflowProcessPersistence.create(new WorkflowProcess().setWorkflow(workflowPersistence.readByCode("VPAP")).setCode("PAP002"));
		
		workflowProcessLog = workflowProcessLogPersistence.readByWorkflowCodeByProcessCode("VPAP", "PAP002");
		
		Assert.assertNotNull(workflowProcessLog);
		Assert.assertNotNull(workflowProcessLog.getWorkflowProcess());
		Assert.assertNotNull(workflowProcessLog.getJbpmProcessInstanceLog());
		
		Assert.assertEquals(new Long(2), workflowProcessLogPersistence.countByWorkflowCode("VPAP"));
		
		List<WorkflowProcessLog> workflowProcessLogs = new ArrayList<>(workflowProcessLogPersistence.readByWorkflowCode("VPAP"));
		Assert.assertNotNull(workflowProcessLogs.get(0).getWorkflowProcess());
		Assert.assertEquals("PAP001",workflowProcessLogs.get(0).getWorkflowProcess().getCode());
		Assert.assertNotNull(workflowProcessLogs.get(0).getJbpmProcessInstanceLog());
		Assert.assertNotNull(workflowProcessLogs.get(0).getWorkflowProcess().getProcessInstanceDesc());
		
		Assert.assertEquals("PAP002",workflowProcessLogs.get(1).getWorkflowProcess().getCode());
	}
	
}

package ci.gouv.dgbf.system.workflow.server.persistence.impl.integration;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessTaskLogPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessTaskPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTask;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTaskLog;

public class WorkflowProcessTaskLogPersistenceImplIntegrationTest extends AbstractIntegrationTest {

	@Inject private WorkflowPersistence workflowPersistence;
	@Inject private WorkflowProcessPersistence workflowProcessPersistence;
	@Inject private WorkflowProcessTaskPersistence workflowProcessTaskPersistence;
	@Inject private WorkflowProcessTaskLogPersistence workflowProcessTaskLogPersistence;	
	
	@Test
	public void readByWorkflowByUserIdentifier() throws Exception{
		userTransaction.begin();
		workflowPersistence.create(new Workflow().setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2"));
		workflowPersistence.create(new Workflow().setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP V01.bpmn2"));
		userTransaction.commit();
		
		workflowProcessPersistence.create(new WorkflowProcess().setCode("PAP001").setWorkflow(workflowPersistence.readByCode("ci.gouv.dgbf.workflow.validation.pap")));
		/*
		System.out.println("TASKS");
		for(WorkflowProcessTask index : workflowProcessTaskPersistence.readAll())
			System.out.println(index);
		System.out.println("LOGS");
		for(WorkflowProcessTaskLog index : workflowProcessTaskLogPersistence.readAll())
			System.out.println(index);
		*/
		
		Assert.assertEquals(new Long(1), workflowProcessTaskLogPersistence.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap"));
		Assert.assertEquals(new Long(1), workflowProcessTaskLogPersistence.countByWorkflowCodeByProcessCode("ci.gouv.dgbf.workflow.validation.pap","PAP001"));
		
		workflowProcessPersistence.create(new WorkflowProcess().setCode("PAP002").setWorkflow(workflowPersistence.readByCode("ci.gouv.dgbf.workflow.validation.pap")));
		
		Assert.assertEquals(new Long(2), workflowProcessTaskLogPersistence.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap"));
		Assert.assertEquals(new Long(1), workflowProcessTaskLogPersistence.countByWorkflowCodeByProcessCode("ci.gouv.dgbf.workflow.validation.pap","PAP002"));
		
		workflowProcessPersistence.create(new WorkflowProcess().setCode("PAP001").setWorkflow(workflowPersistence.readByCode("ci.gouv.dgbf.workflow.validation.pap.v01")));
		
		Assert.assertEquals(new Long(1), workflowProcessTaskLogPersistence.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap.v01"));
		Assert.assertEquals(new Long(1), workflowProcessTaskLogPersistence.countByWorkflowCodeByProcessCode("ci.gouv.dgbf.workflow.validation.pap.v01","PAP001"));
	}
	
}

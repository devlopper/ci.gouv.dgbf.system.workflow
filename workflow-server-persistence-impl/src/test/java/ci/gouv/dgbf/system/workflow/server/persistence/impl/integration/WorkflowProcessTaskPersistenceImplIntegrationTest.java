package ci.gouv.dgbf.system.workflow.server.persistence.impl.integration;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessTaskPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTask;

public class WorkflowProcessTaskPersistenceImplIntegrationTest extends AbstractIntegrationTest {

	@Inject private WorkflowPersistence workflowPersistence;
	@Inject private WorkflowProcessPersistence workflowProcessPersistence;
	@Inject private WorkflowProcessTaskPersistence workflowProcessTaskPersistence;
		
	@Test
	public void readByWorkflowByUserIdentifier() throws Exception{
		userTransaction.begin();
		workflowPersistence.create(new Workflow().setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2"));
		workflowPersistence.create(new Workflow().setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP V01.bpmn2"));
		userTransaction.commit();
		
		workflowProcessPersistence.create(new WorkflowProcess().setCode("PAP001").setWorkflow(workflowPersistence.readByCode("ci.gouv.dgbf.workflow.validation.pap")));
		/*
		for(WorkflowProcessTask task : workflowProcessTaskPersistence.readAll())
			System.out.println(task);
		*/
		Assert.assertEquals(new Long(1), workflowProcessPersistence.countAll());
		Assert.assertEquals(new Long(1), workflowProcessTaskPersistence.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap"));
		Assert.assertEquals(new Long(1), workflowProcessTaskPersistence.countByWorkflowCodeByProcessCode("ci.gouv.dgbf.workflow.validation.pap","PAP001"));
		Assert.assertEquals(new Long(1), workflowProcessTaskPersistence.countByWorkflowCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","charge_etude"));
		Assert.assertEquals(new Long(0), workflowProcessTaskPersistence.countByWorkflowCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","pas_charge_etude"));
		Assert.assertEquals(new Long(1), workflowProcessTaskPersistence.countByWorkflowCodeByProcessCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","PAP001","charge_etude"));
		Assert.assertEquals(new Long(0), workflowProcessTaskPersistence.countByWorkflowCodeByProcessCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","PAP001","pas_charge_etude"));
		
		workflowProcessPersistence.create(new WorkflowProcess().setCode("PAP002").setWorkflow(workflowPersistence.readByCode("ci.gouv.dgbf.workflow.validation.pap")));
		
		Assert.assertEquals(new Long(2), workflowProcessPersistence.countAll());
		Assert.assertEquals(new Long(2), workflowProcessTaskPersistence.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap"));
		Assert.assertEquals(new Long(1), workflowProcessTaskPersistence.countByWorkflowCodeByProcessCode("ci.gouv.dgbf.workflow.validation.pap","PAP002"));
		Assert.assertEquals(new Long(2), workflowProcessTaskPersistence.countByWorkflowCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","charge_etude"));
		Assert.assertEquals(new Long(0), workflowProcessTaskPersistence.countByWorkflowCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","pas_charge_etude"));
		Assert.assertEquals(new Long(1), workflowProcessTaskPersistence.countByWorkflowCodeByProcessCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","PAP002","charge_etude"));
		Assert.assertEquals(new Long(0), workflowProcessTaskPersistence.countByWorkflowCodeByProcessCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","PAP002","pas_charge_etude"));
		
		workflowProcessPersistence.create(new WorkflowProcess().setCode("PAP001").setWorkflow(workflowPersistence.readByCode("ci.gouv.dgbf.workflow.validation.pap.v01")));
		
		Assert.assertEquals(new Long(3), workflowProcessPersistence.countAll());
		Assert.assertEquals(new Long(1), workflowProcessTaskPersistence.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap.v01"));
		Assert.assertEquals(new Long(1), workflowProcessTaskPersistence.countByWorkflowCodeByProcessCode("ci.gouv.dgbf.workflow.validation.pap.v01","PAP001"));
		Assert.assertEquals(new Long(1), workflowProcessTaskPersistence.countByWorkflowCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap.v01","charge_etude"));
		Assert.assertEquals(new Long(0), workflowProcessTaskPersistence.countByWorkflowCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap.v01","pas_charge_etude"));
		Assert.assertEquals(new Long(1), workflowProcessTaskPersistence.countByWorkflowCodeByProcessCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap.v01","PAP001","charge_etude"));
		Assert.assertEquals(new Long(0), workflowProcessTaskPersistence.countByWorkflowCodeByProcessCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap.v01","PAP001","pas_charge_etude"));
		
		WorkflowProcessTask workflowProcessTask = workflowProcessTaskPersistence.readByWorkflowCodeByProcessCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","PAP001","charge_etude").iterator().next();
		
		userTransaction.begin();
		persistenceHelper.getRuntimeEngine().getTaskService().start(workflowProcessTask.getIdentifier(), "charge_etude");
		persistenceHelper.getRuntimeEngine().getTaskService().complete(workflowProcessTask.getIdentifier(), "charge_etude", null);
		userTransaction.commit();
		
		Assert.assertEquals(new Long(3), workflowProcessPersistence.countAll());
		Assert.assertEquals(new Long(3), workflowProcessTaskPersistence.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap"));
		Assert.assertEquals(new Long(2), workflowProcessTaskPersistence.countByWorkflowCodeByProcessCode("ci.gouv.dgbf.workflow.validation.pap","PAP001"));
		Assert.assertEquals(new Long(2), workflowProcessTaskPersistence.countByWorkflowCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","charge_etude"));
		Assert.assertEquals(new Long(0), workflowProcessTaskPersistence.countByWorkflowCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","pas_charge_etude"));
		Assert.assertEquals(new Long(1), workflowProcessTaskPersistence.countByWorkflowCodeByProcessCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","PAP001","charge_etude"));
		Assert.assertEquals(new Long(0), workflowProcessTaskPersistence.countByWorkflowCodeByProcessCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","PAP001","pas_charge_etude"));
		
		workflowProcessTask = workflowProcessTaskPersistence.readByWorkflowCodeByProcessCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","PAP001","sous_directeur").iterator().next();
		userTransaction.begin();
		persistenceHelper.getRuntimeEngine().getTaskService().start(workflowProcessTask.getIdentifier(), "sous_directeur");
		persistenceHelper.getRuntimeEngine().getTaskService().complete(workflowProcessTask.getIdentifier(), "sous_directeur", null);
		userTransaction.commit();
		
		workflowProcessTask = workflowProcessTaskPersistence.readByWorkflowCodeByProcessCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","PAP001","directeur").iterator().next();
		userTransaction.begin();
		persistenceHelper.getRuntimeEngine().getTaskService().start(workflowProcessTask.getIdentifier(), "directeur");
		persistenceHelper.getRuntimeEngine().getTaskService().complete(workflowProcessTask.getIdentifier(), "directeur", null);
		userTransaction.commit();
		
		Assert.assertEquals(new Long(3), workflowProcessPersistence.countAll());
		
	}
	
}

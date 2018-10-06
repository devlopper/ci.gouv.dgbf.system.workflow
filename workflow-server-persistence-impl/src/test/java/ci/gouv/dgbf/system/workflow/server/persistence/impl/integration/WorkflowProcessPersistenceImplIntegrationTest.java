package ci.gouv.dgbf.system.workflow.server.persistence.impl.integration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.cyk.utility.random.RandomHelper;
import org.cyk.utility.server.persistence.PersistenceEntity;
import org.cyk.utility.server.persistence.test.arquillian.AbstractPersistenceEntityIntegrationTestWithDefaultDeploymentAsSwram;
import org.junit.Assert;
import org.junit.Test;

import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessNodeLogPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessTaskPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessNodeLog;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTask;

public class WorkflowProcessPersistenceImplIntegrationTest extends AbstractPersistenceEntityIntegrationTestWithDefaultDeploymentAsSwram<WorkflowProcess> {

	private static final long serialVersionUID = 1L;
	
	@Inject private WorkflowPersistence workflowPersistence;
	@Inject private WorkflowProcessPersistence workflowProcessPersistence;
	
	@Override
	protected void __listenBeforeCallCountIsZero__() throws Exception {
		super.__listenBeforeCallCountIsZero__();
		userTransaction.begin();
		workflowPersistence.create(new Workflow().setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2"));
		workflowPersistence.create(new Workflow().setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP V01.bpmn2"));
		userTransaction.commit();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T __instanciate__(Class<T> aClass, Object action) throws Exception {
		WorkflowProcess workflowProcess = (WorkflowProcess) super.__instanciate__(aClass, action);
		workflowProcess.setWorkflow(workflowPersistence.readByCode("ci.gouv.dgbf.workflow.validation.pap"));
		workflowProcess.setCode(__inject__(RandomHelper.class).getAlphabetic(5));
		return (T) workflowProcess;
	}
	
	@Override
	protected <ENTITY> void ____assertThatEntityHasBeenPersisted____(ENTITY entity,@SuppressWarnings("rawtypes") PersistenceEntity layerEntityInterface) {
		WorkflowProcess workflowProcess = (WorkflowProcess) entity;
		Assert.assertNotNull(workflowProcessPersistence.readByWorkflowCodeByCode(workflowProcess.getWorkflow().getCode(), workflowProcess.getCode()));
	}
	
	@Override public void createOne() throws Exception {}
	
	@Override public void createMany() throws Exception {}
	
	@Override public void readOneByBusinessIdentifier() throws Exception {}
	
	@Override public void readOneBySystemIdentifier() throws Exception {}
	
	@Override public void updateOne() throws Exception {}
	
	@Override public void deleteOne() throws Exception {}
	
	@Test //@InSequence(1)
	public void createWorkflowProcess() throws Exception{
		Assert.assertNull(workflowProcessPersistence.readByWorkflowCodeByCode("ci.gouv.dgbf.workflow.validation.pap", "PAP001"));
		Assert.assertNull(workflowProcessPersistence.readByWorkflowCodeByCode("ci.gouv.dgbf.workflow.validation.pap", "PAP002"));
		Assert.assertNull(workflowProcessPersistence.readByWorkflowCodeByCode("ci.gouv.dgbf.workflow.validation.pap.v01", "PAP001"));
		Assert.assertEquals(new Long(0), workflowProcessPersistence.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap"));
		Assert.assertEquals(new Long(0), workflowProcessPersistence.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap.v01"));
		
		workflowProcessPersistence.create(new WorkflowProcess().setWorkflow(workflowPersistence.readByCode("ci.gouv.dgbf.workflow.validation.pap")).setCode("PAP001"));
		/*
		for(WorkflowProcessTask i : __inject__(WorkflowProcessTaskPersistence.class).readByWorkflowCodeByProcessCode("ci.gouv.dgbf.workflow.validation.pap", "PAP001")) {
			i.setCode(__inject__(WorkflowProcessNodeLogPersistence.class).readByWorkflowCodeByProcessCodeByWorkItemIdentifier("ci.gouv.dgbf.workflow.validation.pap", "PAP001",i.getWorkItemIdentifier())
					.iterator().next().getCode());
			System.out.println("TASK : "+i);
		}
		*/
		/*for(WorkflowProcessNodeLog i : __inject__(WorkflowProcessNodeLogPersistence.class).readByWorkflowCodeByProcessCode("ci.gouv.dgbf.workflow.validation.pap", "PAP001")) {
			System.out.println("NODE : "+i);
		}*/
		//assertThat(workflowProcessNodeLogs).isNotEmpty().hasSize(3);
		
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

package ci.gouv.dgbf.system.workflow.server.persistence.impl.integration;

import java.util.Properties;

import javax.inject.Inject;

import org.cyk.utility.server.persistence.test.arquillian.AbstractPersistenceEntityIntegrationTestWithDefaultDeploymentAsSwram;
import org.jboss.arquillian.junit.InSequence;
import org.jbpm.services.task.identity.JBossUserGroupCallbackImpl;
import org.junit.Assert;
import org.junit.Test;

import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessLogPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessTaskPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessLog;
import ci.gouv.dgbf.system.workflow.server.persistence.impl.JbpmHelper;

public class WorkflowProcessLogPersistenceImplIntegrationTest extends AbstractPersistenceEntityIntegrationTestWithDefaultDeploymentAsSwram<WorkflowProcessLog> {

	private static final long serialVersionUID = 1L;

	@Inject private WorkflowPersistence workflowPersistence;
	@Inject private WorkflowProcessPersistence workflowProcessPersistence;
	@Inject private WorkflowProcessTaskPersistence workflowProcessTaskPersistence;
	@Inject private WorkflowProcessLogPersistence workflowProcessLogPersistence;
	@Inject private JbpmHelper jbpmHelper;
	
	@Override
	protected void __listenBeforeCallCountIsZero__() throws Exception {
		super.__listenBeforeCallCountIsZero__();
		Properties properties = new Properties();
		properties.put("charge_etude", "");
		properties.put("sous_directeur", "");
		properties.put("directeur", "");	
		jbpmHelper.setUserGroupCallback(new JBossUserGroupCallbackImpl(properties));
	}
	
	@Override public void createOne() throws Exception {}
	
	@Override public void createMany() throws Exception {}
	
	@Override public void readOneByBusinessIdentifier() throws Exception {}
	
	@Override public void readOneBySystemIdentifier() throws Exception {}
	
	@Override public void updateOne() throws Exception {}
	
	@Override public void deleteOne() throws Exception {}
	@Test @InSequence(1)
	public void createWorkflowProcess() throws Exception{
		userTransaction.begin();
		workflowPersistence.create(new Workflow().setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2"));
		workflowPersistence.create(new Workflow().setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP V01.bpmn2"));
		
		workflowProcessPersistence.create(new WorkflowProcess().setWorkflow(workflowPersistence.readByCode("ci.gouv.dgbf.workflow.validation.pap")).setCode("PAP001"));
		workflowProcessPersistence.create(new WorkflowProcess().setWorkflow(workflowPersistence.readByCode("ci.gouv.dgbf.workflow.validation.pap")).setCode("PAP002"));
		workflowProcessPersistence.create(new WorkflowProcess().setWorkflow(workflowPersistence.readByCode("ci.gouv.dgbf.workflow.validation.pap")).setCode("PAP003"));
		userTransaction.commit();
		
		Assert.assertEquals(new Long(3), workflowProcessPersistence.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap"));
		Assert.assertEquals(new Long(3), workflowProcessLogPersistence.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap"));
		
		String userIdentifier = "charge_etude";
		Long workflowProcessTaskIdentifier = workflowProcessTaskPersistence.readByWorkflowCodeByProcessCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","PAP001"
				,userIdentifier).iterator().next().getIdentifier();
		userTransaction.begin();
		jbpmHelper.getRuntimeEngine().getTaskService().start(workflowProcessTaskIdentifier, userIdentifier);
		jbpmHelper.getRuntimeEngine().getTaskService().complete(workflowProcessTaskIdentifier, userIdentifier, null);
		userTransaction.commit();
		Assert.assertEquals(new Long(3), workflowProcessPersistence.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap"));
		Assert.assertEquals(new Long(3), workflowProcessLogPersistence.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap"));
		
		userIdentifier = "sous_directeur";
		workflowProcessTaskIdentifier = workflowProcessTaskPersistence.readByWorkflowCodeByProcessCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","PAP001"
				,userIdentifier).iterator().next().getIdentifier();
		userTransaction.begin();
		jbpmHelper.getRuntimeEngine().getTaskService().start(workflowProcessTaskIdentifier, userIdentifier);
		jbpmHelper.getRuntimeEngine().getTaskService().complete(workflowProcessTaskIdentifier, userIdentifier, null);
		userTransaction.commit();
		Assert.assertEquals(new Long(3), workflowProcessPersistence.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap"));
		Assert.assertEquals(new Long(3), workflowProcessLogPersistence.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap"));
		
		userIdentifier = "directeur";
		workflowProcessTaskIdentifier = workflowProcessTaskPersistence.readByWorkflowCodeByProcessCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","PAP001"
				,userIdentifier).iterator().next().getIdentifier();
		userTransaction.begin();
		jbpmHelper.getRuntimeEngine().getTaskService().start(workflowProcessTaskIdentifier, userIdentifier);
		jbpmHelper.getRuntimeEngine().getTaskService().complete(workflowProcessTaskIdentifier, userIdentifier, null);
		userTransaction.commit();
		Assert.assertEquals(new Long(2), workflowProcessPersistence.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap"));
		Assert.assertEquals(new Long(3), workflowProcessLogPersistence.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap"));
		
		/*
		Assert.assertNull(workflowProcessLogPersistence.readByWorkflowCodeByCode("ci.gouv.dgbf.workflow.validation.pap", "PAP001"));
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
		*/
	}
	
}

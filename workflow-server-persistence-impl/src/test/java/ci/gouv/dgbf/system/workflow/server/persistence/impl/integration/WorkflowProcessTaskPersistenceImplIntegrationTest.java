package ci.gouv.dgbf.system.workflow.server.persistence.impl.integration;

import java.util.Properties;

import javax.inject.Inject;

import org.cyk.utility.server.persistence.test.arquillian.AbstractPersistenceEntityIntegrationTestWithDefaultDeploymentAsSwram;
import org.jbpm.services.task.identity.JBossUserGroupCallbackImpl;
import org.junit.Assert;
import org.junit.Test;

import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessTaskPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTask;
import ci.gouv.dgbf.system.workflow.server.persistence.impl.JbpmHelper;

public class WorkflowProcessTaskPersistenceImplIntegrationTest extends AbstractPersistenceEntityIntegrationTestWithDefaultDeploymentAsSwram<WorkflowProcessTask> {

	private static final long serialVersionUID = 1L;
	
	@Inject private WorkflowPersistence workflowPersistence;
	@Inject private WorkflowProcessPersistence workflowProcessPersistence;
	@Inject private WorkflowProcessTaskPersistence workflowProcessTaskPersistence;
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
		Assert.assertEquals(new Long(1), workflowProcessPersistence.count());
		Assert.assertEquals(new Long(1), workflowProcessTaskPersistence.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap"));
		Assert.assertEquals(new Long(1), workflowProcessTaskPersistence.countByWorkflowCodeByProcessCode("ci.gouv.dgbf.workflow.validation.pap","PAP001"));
		Assert.assertEquals(new Long(1), workflowProcessTaskPersistence.countByWorkflowCodeByUserCode("ci.gouv.dgbf.workflow.validation.pap","charge_etude"));
		Assert.assertEquals(new Long(0), workflowProcessTaskPersistence.countByWorkflowCodeByUserCode("ci.gouv.dgbf.workflow.validation.pap","pas_charge_etude"));
		Assert.assertEquals(new Long(1), workflowProcessTaskPersistence.countByWorkflowCodeByProcessCodeByUserCode("ci.gouv.dgbf.workflow.validation.pap","PAP001","charge_etude"));
		Assert.assertEquals(new Long(0), workflowProcessTaskPersistence.countByWorkflowCodeByProcessCodeByUserCode("ci.gouv.dgbf.workflow.validation.pap","PAP001","pas_charge_etude"));
		
		workflowProcessPersistence.create(new WorkflowProcess().setCode("PAP002").setWorkflow(workflowPersistence.readByCode("ci.gouv.dgbf.workflow.validation.pap")));
		
		Assert.assertEquals(new Long(2), workflowProcessPersistence.count());
		Assert.assertEquals(new Long(2), workflowProcessTaskPersistence.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap"));
		Assert.assertEquals(new Long(1), workflowProcessTaskPersistence.countByWorkflowCodeByProcessCode("ci.gouv.dgbf.workflow.validation.pap","PAP002"));
		Assert.assertEquals(new Long(2), workflowProcessTaskPersistence.countByWorkflowCodeByUserCode("ci.gouv.dgbf.workflow.validation.pap","charge_etude"));
		Assert.assertEquals(new Long(0), workflowProcessTaskPersistence.countByWorkflowCodeByUserCode("ci.gouv.dgbf.workflow.validation.pap","pas_charge_etude"));
		Assert.assertEquals(new Long(1), workflowProcessTaskPersistence.countByWorkflowCodeByProcessCodeByUserCode("ci.gouv.dgbf.workflow.validation.pap","PAP002","charge_etude"));
		Assert.assertEquals(new Long(0), workflowProcessTaskPersistence.countByWorkflowCodeByProcessCodeByUserCode("ci.gouv.dgbf.workflow.validation.pap","PAP002","pas_charge_etude"));
		
		workflowProcessPersistence.create(new WorkflowProcess().setCode("PAP001").setWorkflow(workflowPersistence.readByCode("ci.gouv.dgbf.workflow.validation.pap.v01")));
		
		Assert.assertEquals(new Long(3), workflowProcessPersistence.count());
		Assert.assertEquals(new Long(1), workflowProcessTaskPersistence.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap.v01"));
		Assert.assertEquals(new Long(1), workflowProcessTaskPersistence.countByWorkflowCodeByProcessCode("ci.gouv.dgbf.workflow.validation.pap.v01","PAP001"));
		Assert.assertEquals(new Long(1), workflowProcessTaskPersistence.countByWorkflowCodeByUserCode("ci.gouv.dgbf.workflow.validation.pap.v01","charge_etude"));
		Assert.assertEquals(new Long(0), workflowProcessTaskPersistence.countByWorkflowCodeByUserCode("ci.gouv.dgbf.workflow.validation.pap.v01","pas_charge_etude"));
		Assert.assertEquals(new Long(1), workflowProcessTaskPersistence.countByWorkflowCodeByProcessCodeByUserCode("ci.gouv.dgbf.workflow.validation.pap.v01","PAP001","charge_etude"));
		Assert.assertEquals(new Long(0), workflowProcessTaskPersistence.countByWorkflowCodeByProcessCodeByUserCode("ci.gouv.dgbf.workflow.validation.pap.v01","PAP001","pas_charge_etude"));
		
		WorkflowProcessTask workflowProcessTask = workflowProcessTaskPersistence.readByWorkflowCodeByProcessCodeByUserCode("ci.gouv.dgbf.workflow.validation.pap","PAP001","charge_etude").iterator().next();
		
		userTransaction.begin();
		jbpmHelper.getRuntimeEngine().getTaskService().start(workflowProcessTask.getIdentifier(), "charge_etude");
		jbpmHelper.getRuntimeEngine().getTaskService().complete(workflowProcessTask.getIdentifier(), "charge_etude", null);
		userTransaction.commit();
		
		Assert.assertEquals(new Long(3), workflowProcessPersistence.count());
		Assert.assertEquals(new Long(3), workflowProcessTaskPersistence.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap"));
		Assert.assertEquals(new Long(2), workflowProcessTaskPersistence.countByWorkflowCodeByProcessCode("ci.gouv.dgbf.workflow.validation.pap","PAP001"));
		Assert.assertEquals(new Long(2), workflowProcessTaskPersistence.countByWorkflowCodeByUserCode("ci.gouv.dgbf.workflow.validation.pap","charge_etude"));
		Assert.assertEquals(new Long(0), workflowProcessTaskPersistence.countByWorkflowCodeByUserCode("ci.gouv.dgbf.workflow.validation.pap","pas_charge_etude"));
		Assert.assertEquals(new Long(1), workflowProcessTaskPersistence.countByWorkflowCodeByProcessCodeByUserCode("ci.gouv.dgbf.workflow.validation.pap","PAP001","charge_etude"));
		Assert.assertEquals(new Long(0), workflowProcessTaskPersistence.countByWorkflowCodeByProcessCodeByUserCode("ci.gouv.dgbf.workflow.validation.pap","PAP001","pas_charge_etude"));
		
		workflowProcessTask = workflowProcessTaskPersistence.readByWorkflowCodeByProcessCodeByUserCode("ci.gouv.dgbf.workflow.validation.pap","PAP001","sous_directeur").iterator().next();
		userTransaction.begin();
		jbpmHelper.getRuntimeEngine().getTaskService().start(workflowProcessTask.getIdentifier(), "sous_directeur");
		jbpmHelper.getRuntimeEngine().getTaskService().complete(workflowProcessTask.getIdentifier(), "sous_directeur", null);
		userTransaction.commit();
		
		workflowProcessTask = workflowProcessTaskPersistence.readByWorkflowCodeByProcessCodeByUserCode("ci.gouv.dgbf.workflow.validation.pap","PAP001","directeur").iterator().next();
		userTransaction.begin();
		jbpmHelper.getRuntimeEngine().getTaskService().start(workflowProcessTask.getIdentifier(), "directeur");
		jbpmHelper.getRuntimeEngine().getTaskService().complete(workflowProcessTask.getIdentifier(), "directeur", null);
		userTransaction.commit();
		
		Assert.assertEquals(new Long(2), workflowProcessPersistence.count());
		
	}
	
}

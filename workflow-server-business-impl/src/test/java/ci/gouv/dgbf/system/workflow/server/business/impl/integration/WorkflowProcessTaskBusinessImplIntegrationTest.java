package ci.gouv.dgbf.system.workflow.server.business.impl.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.Properties;

import javax.inject.Inject;

import org.cyk.utility.server.business.test.arquillian.AbstractBusinessEntityIntegrationTestWithDefaultDeploymentAsSwram;
import org.jbpm.services.task.identity.JBossUserGroupCallbackImpl;
import org.junit.Assert;
import org.junit.Test;
import org.kie.api.task.model.Status;

import ci.gouv.dgbf.system.workflow.server.business.api.WorkflowBusiness;
import ci.gouv.dgbf.system.workflow.server.business.api.WorkflowProcessBusiness;
import ci.gouv.dgbf.system.workflow.server.business.api.WorkflowProcessTaskBusiness;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTask;
import ci.gouv.dgbf.system.workflow.server.persistence.impl.JbpmHelper;

public class WorkflowProcessTaskBusinessImplIntegrationTest extends AbstractBusinessEntityIntegrationTestWithDefaultDeploymentAsSwram<WorkflowProcessTask> {

	private static final long serialVersionUID = 1L;
	
	@Inject private WorkflowBusiness workflowBusiness;
	@Inject private WorkflowProcessBusiness workflowProcessBusiness;
	@Inject private WorkflowProcessTaskBusiness workflowProcessTaskBusiness;
	
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
		Collection<WorkflowProcessTask> workflowProcessTasks = null;
		WorkflowProcessTask workflowProcessTask = null;
		workflowBusiness.create(new Workflow().setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2"));
		workflowBusiness.create(new Workflow().setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP V01.bpmn2"));
		
		//persistenceHelper.buildKieBase();
		//persistenceHelper.buildRuntimeEnvironment();
		
		workflowProcessBusiness.create(new WorkflowProcess().setCode("PAP001").setWorkflow(workflowBusiness.findByCode("ci.gouv.dgbf.workflow.validation.pap")));
		
		Assert.assertEquals(new Long(1), workflowProcessTaskBusiness.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap"));
		Assert.assertEquals(new Long(1), workflowProcessTaskBusiness.countByWorkflowCodeByProcessCode("ci.gouv.dgbf.workflow.validation.pap","PAP001"));
		Assert.assertEquals(new Long(1), workflowProcessTaskBusiness.countByWorkflowCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","charge_etude"));
		Assert.assertEquals(new Long(0), workflowProcessTaskBusiness.countByWorkflowCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","pas_charge_etude"));
		Assert.assertEquals(new Long(1), workflowProcessTaskBusiness.countByWorkflowCodeByProcessCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","PAP001","charge_etude"));
		Assert.assertEquals(new Long(0), workflowProcessTaskBusiness.countByWorkflowCodeByProcessCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","PAP001","pas_charge_etude"));
		
		workflowProcessTasks = workflowProcessTaskBusiness.findByWorkflowCodeByProcessCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","PAP001","charge_etude");
		assertThat(workflowProcessTasks).hasSize(1);
		
		workflowProcessTask = workflowProcessTasks.iterator().next();
		assertThat(workflowProcessTask.getStatus()).isEqualTo(Status.Reserved);
		assertThat(workflowProcessTask.getName()).isEqualTo("Première Validation");
		assertThat(workflowProcessTask.getOwner()).isEqualTo("charge_etude");
		
		workflowProcessBusiness.create(new WorkflowProcess().setCode("PAP002").setWorkflow(workflowBusiness.findByCode("ci.gouv.dgbf.workflow.validation.pap")));
		
		Assert.assertEquals(new Long(2), workflowProcessTaskBusiness.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap"));
		Assert.assertEquals(new Long(1), workflowProcessTaskBusiness.countByWorkflowCodeByProcessCode("ci.gouv.dgbf.workflow.validation.pap","PAP002"));
		Assert.assertEquals(new Long(2), workflowProcessTaskBusiness.countByWorkflowCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","charge_etude"));
		Assert.assertEquals(new Long(0), workflowProcessTaskBusiness.countByWorkflowCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","pas_charge_etude"));
		Assert.assertEquals(new Long(1), workflowProcessTaskBusiness.countByWorkflowCodeByProcessCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","PAP002","charge_etude"));
		Assert.assertEquals(new Long(0), workflowProcessTaskBusiness.countByWorkflowCodeByProcessCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","PAP002","pas_charge_etude"));
		
		workflowProcessBusiness.create(new WorkflowProcess().setCode("PAP001").setWorkflow(workflowBusiness.findByCode("ci.gouv.dgbf.workflow.validation.pap.v01")));
		
		Assert.assertEquals(new Long(1), workflowProcessTaskBusiness.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap.v01"));
		Assert.assertEquals(new Long(1), workflowProcessTaskBusiness.countByWorkflowCodeByProcessCode("ci.gouv.dgbf.workflow.validation.pap.v01","PAP001"));
		Assert.assertEquals(new Long(1), workflowProcessTaskBusiness.countByWorkflowCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap.v01","charge_etude"));
		Assert.assertEquals(new Long(0), workflowProcessTaskBusiness.countByWorkflowCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap.v01","pas_charge_etude"));
		Assert.assertEquals(new Long(1), workflowProcessTaskBusiness.countByWorkflowCodeByProcessCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap.v01","PAP001","charge_etude"));
		Assert.assertEquals(new Long(0), workflowProcessTaskBusiness.countByWorkflowCodeByProcessCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap.v01","PAP001","pas_charge_etude"));
		
		workflowProcessTask = workflowProcessTaskBusiness.findByWorkflowCodeByProcessCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","PAP001","charge_etude").iterator().next();
		
		workflowProcessTaskBusiness.execute(workflowProcessTask, "charge_etude");
		
		Assert.assertEquals(new Long(3), workflowProcessTaskBusiness.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap"));
		Assert.assertEquals(new Long(2), workflowProcessTaskBusiness.countByWorkflowCodeByProcessCode("ci.gouv.dgbf.workflow.validation.pap","PAP001"));
		Assert.assertEquals(new Long(2), workflowProcessTaskBusiness.countByWorkflowCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","charge_etude"));
		Assert.assertEquals(new Long(0), workflowProcessTaskBusiness.countByWorkflowCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","pas_charge_etude"));
		Assert.assertEquals(new Long(1), workflowProcessTaskBusiness.countByWorkflowCodeByProcessCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","PAP001","charge_etude"));
		Assert.assertEquals(new Long(0), workflowProcessTaskBusiness.countByWorkflowCodeByProcessCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","PAP001","pas_charge_etude"));
		
	}
	
}

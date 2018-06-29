package ci.gouv.dgbf.system.workflow.business.impl.integration;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import ci.gouv.dgbf.system.workflow.business.api.WorkflowBusiness;
import ci.gouv.dgbf.system.workflow.business.api.WorkflowProcessBusiness;
import ci.gouv.dgbf.system.workflow.business.api.WorkflowProcessTaskBusiness;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTask;

public class WorkflowProcessTaskBusinessImplIntegrationTest extends AbstractIntegrationTest {

	@Inject private WorkflowBusiness workflowBusiness;
	@Inject private WorkflowProcessBusiness workflowProcessBusiness;
	@Inject private WorkflowProcessTaskBusiness workflowProcessTaskBusiness;
		
	@Test
	public void readByWorkflowByUserIdentifier() throws Exception{
		workflowBusiness.create(new Workflow().setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2"));
		workflowBusiness.create(new Workflow().setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP V01.bpmn2"));
		
		workflowProcessBusiness.create(new WorkflowProcess().setCode("PAP001").setWorkflow(workflowBusiness.findByCode("ci.gouv.dgbf.workflow.validation.pap")));
		
		Assert.assertEquals(new Long(1), workflowProcessTaskBusiness.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap"));
		Assert.assertEquals(new Long(1), workflowProcessTaskBusiness.countByWorkflowCodeByProcessCode("ci.gouv.dgbf.workflow.validation.pap","PAP001"));
		Assert.assertEquals(new Long(1), workflowProcessTaskBusiness.countByWorkflowCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","charge_etude"));
		Assert.assertEquals(new Long(0), workflowProcessTaskBusiness.countByWorkflowCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","pas_charge_etude"));
		Assert.assertEquals(new Long(1), workflowProcessTaskBusiness.countByWorkflowCodeByProcessCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","PAP001","charge_etude"));
		Assert.assertEquals(new Long(0), workflowProcessTaskBusiness.countByWorkflowCodeByProcessCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","PAP001","pas_charge_etude"));
		
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
		
		WorkflowProcessTask workflowProcessTask = workflowProcessTaskBusiness.findByWorkflowCodeByProcessCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","PAP001","charge_etude").iterator().next();
		
		workflowProcessTaskBusiness.execute(workflowProcessTask, "charge_etude");
		
		Assert.assertEquals(new Long(3), workflowProcessTaskBusiness.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap"));
		Assert.assertEquals(new Long(2), workflowProcessTaskBusiness.countByWorkflowCodeByProcessCode("ci.gouv.dgbf.workflow.validation.pap","PAP001"));
		Assert.assertEquals(new Long(2), workflowProcessTaskBusiness.countByWorkflowCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","charge_etude"));
		Assert.assertEquals(new Long(0), workflowProcessTaskBusiness.countByWorkflowCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","pas_charge_etude"));
		Assert.assertEquals(new Long(1), workflowProcessTaskBusiness.countByWorkflowCodeByProcessCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","PAP001","charge_etude"));
		Assert.assertEquals(new Long(0), workflowProcessTaskBusiness.countByWorkflowCodeByProcessCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","PAP001","pas_charge_etude"));
		
	}
	
}

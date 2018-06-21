package ci.gouv.dgbf.system.workflow.server.persistence.impl.integration;

import java.util.Properties;

import javax.inject.Inject;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jbpm.services.task.identity.JBossUserGroupCallbackImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ci.gouv.dgbf.system.workflow.server.persistence.api.PersistenceHelper;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;


@RunWith(Arquillian.class)
public class WorkflowProcessPersistenceImplIntegrationTest {

	@Inject private PersistenceHelper persistenceHelper;
	@Inject UserTransaction userTransaction;
	@Inject private WorkflowPersistence workflowPersistence;
	@Inject private WorkflowProcessPersistence workflowProcessPersistence;
	
	@Test @InSequence(1)
	public void createWorkflowProcess() throws Exception{
		userTransaction.begin();
		workflowPersistence.create(new Workflow().setCode("VPAP").setName("Validation PAP").setBytesFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2"));
		workflowPersistence.create(new Workflow().setCode("VPAP01").setName("Validation PAP").setBytesFromResourceAsStream("/bpmn/withhuman/Validation du PAP V01.bpmn2"));
		userTransaction.commit();
		
		Assert.assertEquals(new Long(0), workflowProcessPersistence.countByWorkflowCode("VPAP"));
		Assert.assertEquals(new Long(0), workflowProcessPersistence.countByWorkflowCode("VPAP01"));
		
		workflowProcessPersistence.create(new WorkflowProcess().setWorkflow(workflowPersistence.readByCode("VPAP")).setCode("PAP001"));
		
		Assert.assertEquals(new Long(1), workflowProcessPersistence.countByWorkflowCode("VPAP"));
		Assert.assertEquals(new Long(0), workflowProcessPersistence.countByWorkflowCode("VPAP01"));
		
		workflowProcessPersistence.create(new WorkflowProcess().setWorkflow(workflowPersistence.readByCode("VPAP")).setCode("PAP002"));
		
		Assert.assertEquals(new Long(2), workflowProcessPersistence.countByWorkflowCode("VPAP"));
		Assert.assertEquals(new Long(0), workflowProcessPersistence.countByWorkflowCode("VPAP01"));
		
		workflowProcessPersistence.create(new WorkflowProcess().setWorkflow(workflowPersistence.readByCode("VPAP01")).setCode("PAP001"));
		
		Assert.assertEquals(new Long(2), workflowProcessPersistence.countByWorkflowCode("VPAP"));
		Assert.assertEquals(new Long(1), workflowProcessPersistence.countByWorkflowCode("VPAP01"));
		
	}
	
	/* Configuration */
	
	@Before
	public void listenBefore(){
		Properties properties = new Properties();
		properties.put("charge_etude", "");
		properties.put("sous_directeur", "");
		properties.put("directeur", "");	
		persistenceHelper.setUserGroupCallback(new JBossUserGroupCallbackImpl(properties));
		persistenceHelper.addKieBaseResourceByClassPath("/bpmn/withhuman/Validation du PAP.bpmn2").buildKieBase();
		persistenceHelper.addKieBaseResourceByClassPath("/bpmn/withhuman/Validation du PAP V01.bpmn2").buildKieBase();
	}
	
	/* Deployment */
	
	@org.jboss.arquillian.container.test.api.Deployment
	public static WebArchive createArchive(){
		return ShrinkWrap.create(WebArchive.class)
				.addAsResource("project-defaults-test.yml", "project-defaults.yml")
				.addAsResource("bpmn/withhuman/Validation du PAP.bpmn2", "bpmn/withhuman/Validation du PAP.bpmn2")
				.addAsResource("bpmn/withhuman/Validation du PAP V01.bpmn2", "bpmn/withhuman/Validation du PAP V01.bpmn2")
				.addAsLibraries(Maven.resolver().loadPomFromFile("pom-test.xml").importRuntimeDependencies().resolve().withTransitivity().asFile())
		;
	}
}

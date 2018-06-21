package ci.gouv.dgbf.system.workflow.server.persistence.impl.integration;

import javax.inject.Inject;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;

@RunWith(Arquillian.class)
public class WorkflowPersistenceImplIntegrationTest {

	@Inject UserTransaction userTransaction;
	@Inject private WorkflowPersistence workflowPersistence;
	
	@Test
	public void createWorkflow() throws Exception{
		Workflow workflow = new Workflow().setCode("VPAP").setName("Validation PAP").setBytesFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2");
		Assert.assertNotNull(workflow.getBytes());
		Assert.assertNull(workflow.getIdentifier());
		Assert.assertEquals(new Long(0), workflowPersistence.countAll());
		userTransaction.begin();
		workflowPersistence.create(workflow);
		userTransaction.commit();
		Assert.assertNotNull(workflow.getIdentifier());
		Assert.assertEquals(new Long(1), workflowPersistence.countAll());
		
		/* cleaning */
		userTransaction.begin();
		workflowPersistence.deleteByCode("VPAP");
		userTransaction.commit();
	}
	
	@Test
	public void readWorkflowByCode() throws Exception{
		userTransaction.begin();
		workflowPersistence.create(new Workflow().setCode("VPAP").setName("Validation PAP").setBytesFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2"));
		userTransaction.commit();
		Workflow workflow = workflowPersistence.readByCode("VPAP");
		Assert.assertNotNull(workflow);
		Assert.assertNotNull(workflow.getIdentifier());
		Assert.assertEquals("VPAP", workflow.getCode());
		Assert.assertEquals("Validation PAP", workflow.getName());
		Assert.assertNotNull(workflow.getBytes());
		
		/* cleaning */
		userTransaction.begin();
		workflowPersistence.deleteByCode("VPAP");
		userTransaction.commit();
	}
	
	/* Deployment */
	
	@org.jboss.arquillian.container.test.api.Deployment
	public static WebArchive createArchive(){
		return ShrinkWrap.create(WebArchive.class)
				.addAsResource("project-defaults-test.yml", "project-defaults.yml")
				.addAsResource("bpmn/withhuman/Validation du PAP.bpmn2", "bpmn/withhuman/Validation du PAP.bpmn2")
				.addAsLibraries(Maven.resolver().loadPomFromFile("pom-test.xml").importRuntimeDependencies().resolve().withTransitivity().asFile())
		;
	}
}

package ci.gouv.dgbf.system.workflow.server.persistence.impl.integration;

import java.util.Properties;

import javax.inject.Inject;
import javax.transaction.UserTransaction;

import org.apache.commons.io.IOUtils;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jbpm.services.task.identity.JBossUserGroupCallbackImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.task.UserGroupCallback;

import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessTaskPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.persistence.impl.WorkflowProcessTaskPersistenceImpl;

@RunWith(Arquillian.class)
public class WorkflowProcessTaskImplIntegrationTest {

	@Inject UserTransaction userTransaction;
	@Inject private WorkflowPersistence workflowPersistence;
	@Inject private WorkflowProcessTaskPersistence workflowProcessTaskPersistence;
	
	private UserGroupCallback userGroupCallback;
	
	@Before
	public void listenBefore(){
		Properties properties = new Properties();
		properties.put("charge_etude", "");
		properties.put("sous_directeur", "");
		properties.put("directeur", "");	
		userGroupCallback = new JBossUserGroupCallbackImpl(properties);
	}
	
	@Test
	public void readByWorkflowByUserIdentifier() throws Exception{
		Workflow workflow = new Workflow();
		workflow.setCode("VPAP").setName("Validation PAP").setBytes(IOUtils.toByteArray(getClass().getResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2")));
		userTransaction.begin();
		workflowPersistence.create(workflow);
		userTransaction.commit();
		
		WorkflowProcess workflowProcess = new WorkflowProcess().setCode("PAP001").setWorkflow(workflowPersistence.readByCode("VPAP"));
		
		((WorkflowProcessTaskPersistenceImpl)workflowProcessTaskPersistence).setUserGroupCallback(userGroupCallback);
		System.out.println("WorkflowProcessTaskImplIntegrationTest.readByWorkflowByUserIdentifier() : "+
				workflowProcessTaskPersistence.readByWorkflowProcessByUserIdentifier(workflowProcess, "charge_etude"));
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

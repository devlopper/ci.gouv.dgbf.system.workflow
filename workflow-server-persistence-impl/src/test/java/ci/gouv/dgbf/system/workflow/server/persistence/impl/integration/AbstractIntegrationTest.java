package ci.gouv.dgbf.system.workflow.server.persistence.impl.integration;

import java.util.Properties;

import javax.inject.Inject;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jbpm.services.task.identity.JBossUserGroupCallbackImpl;
import org.junit.Before;
import org.junit.runner.RunWith;

import ci.gouv.dgbf.system.workflow.server.persistence.api.PersistenceHelper;

@RunWith(Arquillian.class)
public abstract class AbstractIntegrationTest {

	@Inject protected UserTransaction userTransaction;
	@Inject protected PersistenceHelper persistenceHelper;
	
	/* Configuration */
	
	@Before
	public void listenBefore(){
		__listenBefore__();
	}
	
	protected void __listenBefore__(){
		Properties properties = new Properties();
		properties.put("charge_etude", "");
		properties.put("sous_directeur", "");
		properties.put("directeur", "");	
		persistenceHelper.setUserGroupCallback(new JBossUserGroupCallbackImpl(properties));
		persistenceHelper.addProcessDefinitionFromClassPath("/bpmn/withhuman/Validation du PAP.bpmn2","/bpmn/withhuman/Validation du PAP V01.bpmn2");
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

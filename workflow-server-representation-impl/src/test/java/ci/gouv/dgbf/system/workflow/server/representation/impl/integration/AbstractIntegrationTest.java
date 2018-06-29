package ci.gouv.dgbf.system.workflow.server.representation.impl.integration;

import java.util.Properties;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(Arquillian.class)
public abstract class AbstractIntegrationTest {

	//@Inject protected PersistenceHelper persistenceHelper;
	@Drone protected WebDriver browser;
	
	/* Configuration */
	
	//@Before
	public void listenBefore(){
		__listenBefore__();
	}
	
	protected void __listenBefore__(){
		Properties properties = new Properties();
		properties.put("charge_etude", "");
		properties.put("sous_directeur", "");
		properties.put("directeur", "");	
		//persistenceHelper.setUserGroupCallback(new JBossUserGroupCallbackImpl(properties));
		if(Boolean.TRUE.equals(isAddProcessDefinitionFromClassPathOnListenBefore()))
			__listenBefore__addProcessDefinitionFromClassPath();
	}
	
	protected void __listenBefore__addProcessDefinitionFromClassPath(){
		
	}
	
	protected Boolean isAddProcessDefinitionFromClassPathOnListenBefore(){
		return Boolean.FALSE;
	}
	
	/* Deployment */
	
	@org.jboss.arquillian.container.test.api.Deployment(testable=false)
	public static WebArchive createArchive(){
		return ShrinkWrap.create(WebArchive.class)
				.addAsResource("project-defaults-test.yml", "project-defaults.yml")
				.addAsResource("bpmn/withhuman/Validation du PAP.bpmn2", "bpmn/withhuman/Validation du PAP.bpmn2")
				.addAsResource("bpmn/withhuman/Validation du PAP V01.bpmn2", "bpmn/withhuman/Validation du PAP V01.bpmn2")
				.addAsLibraries(Maven.resolver().loadPomFromFile("pom-test.xml").importRuntimeDependencies().resolve().withTransitivity().asFile())
		;
	}

}

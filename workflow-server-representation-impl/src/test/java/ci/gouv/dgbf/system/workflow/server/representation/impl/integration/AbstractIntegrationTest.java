package ci.gouv.dgbf.system.workflow.server.representation.impl.integration;

import java.net.URL;
import java.util.Properties;

import javax.ws.rs.core.UriBuilder;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(Arquillian.class)
public abstract class AbstractIntegrationTest {

	static {
		Properties properties = new Properties();
		properties.put("charge_etude", "");
		properties.put("sous_directeur", "");
		properties.put("directeur", "");	
		//PersistenceHelperImpl.USER_GROUP_CALLBACK = new JBossUserGroupCallbackImpl(properties);
		//System.setProperty("jbpmusers", "u1 , u2");
	}
	
	/* Global variable */
	protected static Boolean __INITIALISED__;
	protected static ResteasyClient CLIENT;
	protected static ResteasyWebTarget TARGET;
	protected static Long IDENTIFIER;
	
	@Drone protected WebDriver browser;
	@ArquillianResource protected URL contextPath;
	
	/* Configuration */
	
	@Before
	public void listenBefore(){
		__listenBefore__();
		if(__INITIALISED__ == null){
			__initialise__();
			__INITIALISED__ = Boolean.TRUE;
		}
	}
	
	protected void __listenBefore__(){
		if(Boolean.TRUE.equals(isAddProcessDefinitionFromClassPathOnListenBefore()))
			__listenBefore__addProcessDefinitionFromClassPath();
	}
	
	protected void __listenBefore__addProcessDefinitionFromClassPath(){
		
	}
	
	protected Boolean isAddProcessDefinitionFromClassPathOnListenBefore(){
		return Boolean.FALSE;
	}
	
	protected void __initialise__(){
		CLIENT = new ResteasyClientBuilder().build();
		TARGET = CLIENT.target(UriBuilder.fromPath(contextPath.toExternalForm()));
	}
	
	/* Deployment */
	
	@org.jboss.arquillian.container.test.api.Deployment(testable=false)
	public static WebArchive createArchive(){
		return ShrinkWrap.create(WebArchive.class)
				.addAsResource("project-defaults-test.yml", "project-defaults.yml")
				.addAsResource("bpmn/withhuman/Validation du PAP.bpmn2", "bpmn/withhuman/Validation du PAP.bpmn2")
				.addAsResource("bpmn/withhuman/Validation du PAP V01.bpmn2", "bpmn/withhuman/Validation du PAP V01.bpmn2")
				.addAsWebInfResource("META-INF/beans.xml","beans.xml")
				.addAsManifestResource("META-INF/beans.xml","beans.xml")
				.addClass(PersistenceHelperImpl.class)
				.addAsLibraries(Maven.resolver().loadPomFromFile("pom-test.xml").importRuntimeDependencies().resolve().withTransitivity().asFile())
		;
	}

}

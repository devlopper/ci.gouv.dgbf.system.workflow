package ci.gouv.dgbf.system.workflow.server.persistence.impl.integration;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jbpm.services.api.DefinitionService;
import org.jbpm.services.api.DeploymentService;
import org.jbpm.services.api.model.DeployedUnit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class JbpmDeploymentServiceIntegrationTest {

	@Inject private DeploymentService deploymentService;
	@Inject private DefinitionService definitionService;
	
	@Test
	public void isDeploymentServiceNotNull(){
		Assert.assertNotNull(deploymentService);
	}
	
	//@Test
	public void isDeployedUnitNotNullWhenActivated() throws IllegalArgumentException, IOException{
		deploymentService.activate("MyDeploymentId002");
		definitionService.buildProcessDefinition("MyDeploymentId002", IOUtils.toString(getClass().getResourceAsStream("/bpmn/demo.bpmn")
				,Charset.forName("UTF-8")), null, Boolean.TRUE);
		DeployedUnit deployedUnit = deploymentService.getDeployedUnit("MyDeploymentId002");
		System.out.println("JbpmDeploymentServiceIntegrationTest.isDeployedUnitNotNullWhenActivated() : "+deploymentService.getDeployedUnits());
		Assert.assertNotNull(deployedUnit);
	}
	
	//@Test
	public void isDeployedUnitNullWhenNotActivated(){
		DeployedUnit deployedUnit = deploymentService.getDeployedUnit("MyDeploymentIdAAA");
		Assert.assertNotNull(deployedUnit);
	}
	
	/* Deployment */
	
	@org.jboss.arquillian.container.test.api.Deployment
	public static WebArchive createArchive(){
		return ShrinkWrap.create(WebArchive.class)
				.addAsResource("project-defaults-test.yml", "project-defaults.yml")
				.addAsResource("bpmn/demo.bpmn", "bpmn/demo.bpmn")	
				.addAsResource("bpmn/demo_another_name.bpmn", "bpmn/demo_another_name.bpmn")	
				.addAsResource("bpmn/withhuman/process01.bpmn", "bpmn/withhuman/process01.bpmn")
				.addAsResource("bpmn/withhuman/Validate Sale.bpmn2", "bpmn/withhuman/Validate Sale.bpmn2")
				.addAsLibraries(Maven.resolver().loadPomFromFile("pom-test.xml").importRuntimeDependencies().resolve().withTransitivity().asFile())
		;
	}
	
}

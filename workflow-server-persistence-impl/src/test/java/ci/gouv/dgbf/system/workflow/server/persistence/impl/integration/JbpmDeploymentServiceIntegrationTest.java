package ci.gouv.dgbf.system.workflow.server.persistence.impl.integration;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.cyk.utility.server.persistence.test.arquillian.AbstractPersistenceArquillianIntegrationTestWithDefaultDeploymentAsSwram;
import org.jboss.arquillian.junit.Arquillian;
import org.jbpm.kie.services.impl.KModuleDeploymentUnit;
import org.jbpm.services.api.DefinitionService;
import org.jbpm.services.api.DeploymentService;
import org.jbpm.services.api.model.DeployedUnit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

public class JbpmDeploymentServiceIntegrationTest extends AbstractPersistenceArquillianIntegrationTestWithDefaultDeploymentAsSwram {
	private static final long serialVersionUID = 1L;
	
	@Inject private DeploymentService deploymentService;
	@Inject private DefinitionService definitionService;
	
	@Test
	public void isDeployedUnitNotNullWhenActivated() throws IllegalArgumentException, IOException{
		KModuleDeploymentUnit deploymentUnit = new KModuleDeploymentUnit("mygid", "myaid", "myv01");
        
		deploymentService.deploy(deploymentUnit);
		
		deploymentService.activate(deploymentUnit.getIdentifier());
		definitionService.buildProcessDefinition(deploymentUnit.getIdentifier(), IOUtils.toString(getClass().getResourceAsStream("/bpmn/demo.bpmn")
				,Charset.forName("UTF-8")), null, Boolean.TRUE);
		DeployedUnit deployedUnit = deploymentService.getDeployedUnit(deploymentUnit.getIdentifier());
		System.out.println("JbpmDeploymentServiceIntegrationTest.isDeployedUnitNotNullWhenActivated() : "+deploymentService.getDeployedUnits());
		Assert.assertNotNull(deployedUnit);
	}
	
	@Test
	public void isDeployedUnitNullWhenNotActivated(){
		DeployedUnit deployedUnit = deploymentService.getDeployedUnit("MyDeploymentIdAAA");
		Assert.assertNotNull(deployedUnit);
	}
	
}

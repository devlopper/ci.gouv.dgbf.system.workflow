package ci.gouv.dgbf.system.workflow.server.persistence.impl.integration;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import ci.gouv.dgbf.system.workflow.server.persistence.api.BusinessProcessModelNotationHelper;

@RunWith(Arquillian.class)
public class BusinessProcessModelNotationHelperImplIntegrationTest {

	@Inject private BusinessProcessModelNotationHelper businessProcessModelNotationHelper;
	
	@Test
	public void isIdentifierEqualsComDotSampleDotHello() throws IOException{
		Assert.assertEquals("com.sample.hello", businessProcessModelNotationHelper.getIdentifier(IOUtils.toString(getClass().getResourceAsStream("/bpmn/demo.bpmn"),Charset.forName("UTF-8"))));
	}
	
	/* Deployment */
	
	@org.jboss.arquillian.container.test.api.Deployment
	public static WebArchive createArchive(){
		return ShrinkWrap.create(WebArchive.class)
				.addAsResource("project-defaults.yml", "project-defaults.yml")
				//.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")	
				.addAsResource("bpmn/demo.bpmn", "bpmn/demo.bpmn")	
				.addAsResource("bpmn/demo_another_name.bpmn", "bpmn/demo_another_name.bpmn")	
				.addAsResource("bpmn/withhuman/process01.bpmn", "bpmn/withhuman/process01.bpmn")
				.addAsResource("bpmn/withhuman/Validate Sale.bpmn2", "bpmn/withhuman/Validate Sale.bpmn2")
				.addAsLibraries(Maven.resolver().loadPomFromFile("pom-test.xml").importRuntimeDependencies().resolve().withTransitivity().asFile())
		;
	}
	
}

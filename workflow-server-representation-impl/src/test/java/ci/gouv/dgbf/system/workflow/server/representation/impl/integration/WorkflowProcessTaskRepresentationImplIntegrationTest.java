package ci.gouv.dgbf.system.workflow.server.representation.impl.integration;

import javax.ws.rs.core.UriBuilder;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.junit.Assert;
import org.junit.Test;

import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowDto;

public class WorkflowProcessTaskRepresentationImplIntegrationTest extends AbstractIntegrationTest {
	
	@Test
	public void createWorkflow(){
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(UriBuilder.fromPath("http://localhost:8080"));
		WorkflowRepresentation workflowRepresentation = target.proxy(WorkflowRepresentation.class);
		
		Assert.assertEquals(new Long(0), workflowRepresentation.countAll());
		Assert.assertNull(workflowRepresentation.getByCode("ci.gouv.dgbf.workflow.validation.pap"));
		
		workflowRepresentation.createOne(new WorkflowDto().setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2"));
		
		Assert.assertEquals(new Long(1), workflowRepresentation.countAll());
		WorkflowDto workflowDto = workflowRepresentation.getByCode("ci.gouv.dgbf.workflow.validation.pap");
		Assert.assertNotNull(workflowDto);
		Assert.assertEquals("Validation du PAP", workflowDto.getName());
	}
	
}

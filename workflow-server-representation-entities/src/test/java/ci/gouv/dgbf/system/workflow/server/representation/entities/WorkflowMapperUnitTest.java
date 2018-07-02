package ci.gouv.dgbf.system.workflow.server.representation.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;

public class WorkflowMapperUnitTest {
	
	private static Logger LOGGER = LogManager.getLogger(WorkflowMapperUnitTest.class);
	
	@Test
	public void getPersistenceFromRepresentation(){
		WorkflowDto representation = new WorkflowDto().setIdentifier(1l).setCode("c01").setName("n01").setModel("model01");
		Workflow persistence = WorkflowEntityMapper.INSTANCE.getPersistenceFromRepresentation(representation);
		Assert.assertEquals(representation.getIdentifier(), persistence.getIdentifier());
		Assert.assertEquals(representation.getCode(), persistence.getCode());
		Assert.assertEquals(representation.getName(), persistence.getName());
		Assert.assertEquals(representation.getModel(), persistence.getModel());
		LOGGER.info("DONE!!!");
	}
	
	@Test
	public void getRepresentationFromPersistence(){
		Workflow persistence = new Workflow().setIdentifier(1l).setCode("c01").setName("n01").setModel("model01");
		WorkflowDto representation = WorkflowEntityMapper.INSTANCE.getRepresentationFromPersistence(persistence);
		Assert.assertEquals(persistence.getIdentifier(), representation.getIdentifier());
		Assert.assertEquals(persistence.getCode(), representation.getCode());
		Assert.assertEquals(persistence.getName(), representation.getName());
		Assert.assertEquals(persistence.getModel(), representation.getModel());
	}
	
}

package ci.gouv.dgbf.system.workflow.persistence.entities;

import org.junit.Assert;
import org.junit.Test;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.bpmn.Bpmn;

public class BpmnXmlUnitTest {

	@Test
	public void unmarshall(){
		Bpmn bpmnXml = Bpmn.__execute__(System.getProperty("user.dir")+"/src/test/resources/bpmn/withhuman/Validation du PAP.bpmn2");
		Assert.assertEquals("charge_etude", bpmnXml.getProcessFirstUserTaskPotentialOwner());
	}
	
	@Test
	public void unmarshallProcess02(){
		Bpmn bpmn = Bpmn.__execute__(System.getProperty("user.dir")+"/src/test/resources/bpmn/withhuman/process02.bpmn");
		Assert.assertNotNull(bpmn);
		Assert.assertNotNull(bpmn.getProcess());
	}
}

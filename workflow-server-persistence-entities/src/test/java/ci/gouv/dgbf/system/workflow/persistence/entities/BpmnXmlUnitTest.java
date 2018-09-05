package ci.gouv.dgbf.system.workflow.persistence.entities;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.junit.Assert;
import org.junit.Test;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.bpmn.Bpmn;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.bpmn.Process;

public class BpmnXmlUnitTest {

	@Test
	public void marshal() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Bpmn.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			Bpmn bpmn = new Bpmn();
			bpmn.setProcess(new Process());
			bpmn.getProcess().setId("myid");
			bpmn.getProcess().setName("myname");
			StringWriter writer = new StringWriter();
			marshaller.marshal(bpmn, writer);
			String string = writer.toString();
			assertThat(string).contains("<bpmn2:definitions").contains("</bpmn2:definitions>");
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
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

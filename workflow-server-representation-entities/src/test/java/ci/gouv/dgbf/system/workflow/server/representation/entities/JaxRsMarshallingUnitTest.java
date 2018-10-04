package ci.gouv.dgbf.system.workflow.server.representation.entities;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Assert;
import org.junit.Test;

public class JaxRsMarshallingUnitTest {

	@Test
	public void marshallWorkflowDto() throws JAXBException {
		WorkflowDto dto = new WorkflowDto();
		dto.setCode("MyCode").setName("MyName").setModel("MyModel");
		JAXBContext jaxbContext = JAXBContext.newInstance(WorkflowDto.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		//jaxbMarshaller.marshal(dto, System.out);
	}
	
	@Test
	public void unmarshallWorkflowDto() throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(WorkflowDto.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		WorkflowDto dto = (WorkflowDto) unmarshaller.unmarshal(new StringReader(XML));
		Assert.assertNotNull(dto.getModel());
		//System.out.println(dto.getModel());
	}
	
	@Test
	public void marshallWorkflowProcessDto() throws JAXBException {
		WorkflowProcessDto dto = new WorkflowProcessDto();
		dto.setCode("MyCode").setWorkflow("MyModel");
		JAXBContext jaxbContext = JAXBContext.newInstance(WorkflowProcessDto.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		//jaxbMarshaller.marshal(dto, System.out);
	}
	
	@Test
	public void unmarshallWorkflowProcessDto() throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(WorkflowProcessDto.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		WorkflowProcessDto dto = (WorkflowProcessDto) unmarshaller.unmarshal(new StringReader(XML_WORKFLOW_PROCESS));
		Assert.assertNotNull(dto.getCode());
		//System.out.println(dto.getCode());
	}
	
	public static String XML = ""
			+ " <workflowDto>"
			+ " <code>C</code>"
			+ " <name>N</name>"
			+ " <model> <![CDATA["
			+ " <definitions id='Definition' targetNamespace='http://www.jboss.org/drools'"
			+ " typeLanguage='http://www.java.com/javaTypes'"
			+ " expressionLanguage='http://www.mvel.org/2.0'"
			+ " xmlns='http://www.omg.org/spec/BPMN/20100524/MODEL'"
			+ " xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'"
			+ " xsi:schemaLocation='http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd'"
			+ " xmlns:g='http://www.jboss.org/drools/flow/gpd'"
			+ " xmlns:bpmndi='http://www.omg.org/spec/BPMN/20100524/DI'"
			+ " xmlns:dc='http://www.omg.org/spec/DD/20100524/DC'"
			+ " xmlns:di='http://www.omg.org/spec/DD/20100524/DI'"
			+ " xmlns:tns='http://www.jboss.org/drools'>"
			+ " <process processType='Private' isExecutable='true' id='com.sample.hello' name='com.sample.hello' tns:packageName='com.sample' >"
			+ " <startEvent id='_jbpm-unique-0' name='Start'  isInterrupting='false'>"
			+ " </startEvent>"
			+ " <scriptTask id='_jbpm-unique-1' name='Action' scriptFormat='http://www.java.com/java' >"
			+ " <script>System.out.println('Hello World ! This is from the API');</script>"
			+ " </scriptTask>"
			+ " <endEvent id='_jbpm-unique-2' name='End' >"
			+ " <terminateEventDefinition />"
			+ " </endEvent>"
			+ " <sequenceFlow id='_jbpm-unique-0-_jbpm-unique-1' sourceRef='_jbpm-unique-0' targetRef='_jbpm-unique-1' />"
			+ " <sequenceFlow id='_jbpm-unique-1-_jbpm-unique-2' sourceRef='_jbpm-unique-1' targetRef='_jbpm-unique-2' />"
			+ " </process>"
			+ " </definitions>"
			+ "]]></model>"
			+ " </workflowDto>"
			+ "";

	public static String XML_WORKFLOW_PROCESS = ""
			+ " <workflowProcessDto>"
			+ " <code>C</code>"
			+ " <workflowCode>N</workflowCode>"
			+ " </workflowProcessDto>"
			+ "";
}

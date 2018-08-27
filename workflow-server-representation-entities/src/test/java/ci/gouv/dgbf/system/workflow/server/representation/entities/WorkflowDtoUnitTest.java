package ci.gouv.dgbf.system.workflow.server.representation.entities;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Assert;
import org.junit.Test;

public class WorkflowDtoUnitTest {

	@Test
	public void one_marshal() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(WorkflowDto.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter writer = new StringWriter();
			marshaller.marshal(new WorkflowDto().setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2"), writer);
			String string = writer.toString();
			assertThat(string).contains("<workflowDto>").contains("</workflowDto>");
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Test
	public void one_unmarshal() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(WorkflowDto.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			WorkflowDto dto = (WorkflowDto) unmarshaller.unmarshal(new StringReader(XML));
			Assert.assertEquals("com.sample.hello", dto.getCode());
			Assert.assertEquals("com.sample.hello", dto.getName());
			assertThat(dto.getModel()).contains("<process ").contains("</process>").contains("id=\"com.sample.hello\"").contains("name=\"com.sample.hello\"");
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	/*
	@Test
	public void many_of_one_marshal() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ManyOfOne.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter writer = new StringWriter();
			marshaller.marshal(new ManyOfOne().add(new One().setF1("v1").setF2("v2"))
					.add(new One().setF1("va1").setF2("va2")), writer);
			String string = writer.toString();
			assertThat(string).contains("<manyOfOne>").contains("<one>").contains("<f1>v1</f1>").contains("<f2>v2</f2>").contains("</one>").contains("</manyOfOne>");
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Test
	public void many_of_one_unmarshal() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ManyOfOne.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			ManyOfOne manyOfOne = (ManyOfOne) unmarshaller.unmarshal(getClass().getResourceAsStream("manyofone.xml"));
			Assert.assertEquals(2, manyOfOne.getOnes().size());
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Test
	public void many_of_one_parameterized_marshal() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Many.class,One.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter writer = new StringWriter();
			marshaller.marshal(new Many().add(new One().setF1("v1").setF2("v2"))
					.add(new One().setF1("va1").setF2("va2")), writer);
			String string = writer.toString();
			assertThat(string).contains("<many>").contains("<f1>v1</f1>").contains("<f2>v2</f2>")
				.contains("<f1>va1</f1>").contains("<f2>va2</f2>").contains("</many>");
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Test
	public void many_of_one_parameterized_unmarshal() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Many.class,One.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Many many = (Many) unmarshaller.unmarshal(getClass().getResourceAsStream("manyobjectsofone.xml"));
			Assert.assertEquals(2, many.getElements().size());
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Test
	public void two_marshal() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Two.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter writer = new StringWriter();
			marshaller.marshal(new Two().setF1("v1").setF2("v2"), writer);
			String string = writer.toString();
			assertThat(string).contains("<two>").contains("<f1>v1</f1>").contains("<f2>v2</f2>").contains("</two>");
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Test
	public void two_unmarshal() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Two.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Two two = (Two) unmarshaller.unmarshal(getClass().getResourceAsStream("two.xml"));
			Assert.assertEquals("v1", two.getF1());
			Assert.assertEquals("v2", two.getF2());
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Test
	public void many_of_two_parameterized_marshal() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Many.class,Two.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter writer = new StringWriter();
			marshaller.marshal(new Many().add(new Two().setF1("v1").setF2("v2"))
					.add(new Two().setF1("va1").setF2("va2")), writer);
			String string = writer.toString();
			assertThat(string).contains("<many>").contains("<f1>v1</f1>").contains("<f2>v2</f2>")
				.contains("<f1>va1</f1>").contains("<f2>va2</f2>").contains("</many>");
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Test
	public void many_of_two_parameterized_unmarshal() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Many.class,Two.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Many many = (Many) unmarshaller.unmarshal(getClass().getResourceAsStream("manyobjectsoftwo.xml"));
			Assert.assertEquals(2, many.getElements().size());
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Test
	public void many_of_one_and_two_parameterized_marshal() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Many.class,Two.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter writer = new StringWriter();
			marshaller.marshal(new Many().add(new Two().setF1("v1").setF2("v2")).add(new Two().setF1("ONEv1").setF2("ONEv2"))
					.add(new Two().setF1("va1").setF2("va2")), writer);
			String string = writer.toString();
			assertThat(string).contains("<many>").contains("<f1>v1</f1>").contains("<f2>v2</f2>").contains("<f1>ONEv1</f1>").contains("<f2>ONEv2</f2>")
				.contains("<f1>va1</f1>").contains("<f2>va2</f2>").contains("</many>");
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Test
	public void many_of_one_and_two_parameterized_unmarshal() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Many.class,One.class,Two.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Many many = (Many) unmarshaller.unmarshal(getClass().getResourceAsStream("manyobjectsofoneandtwo.xml"));
			Assert.assertEquals(3, many.getElements().size());
			List<Object> elements = (List<Object>) many.getElements();
			assertThat(elements.get(0)).isInstanceOf(Two.class);
			assertThat(elements.get(1)).isInstanceOf(One.class);
			assertThat(elements.get(2)).isInstanceOf(Two.class);
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	*/
	
	private static final String XML = ""
			+ " <workflowDto>"
			+ " <code>ci.gouv.dgbf.workflow.validation</code>"
			+ " <name>Validation PAP</name>"
			+ " <model> <![CDATA["
			+ " <definitions id=\"Definition\" targetNamespace=\"http://www.jboss.org/drools\""
			+ " typeLanguage=\"http://www.java.com/javaTypes\""
			+ " expressionLanguage=\"http://www.mvel.org/2.0\""
			+ " xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\""
			+ " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
			+ " xsi:schemaLocation=\"http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd\""
			+ " xmlns:g=\"http://www.jboss.org/drools/flow/gpd\""
			+ " xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\""
			+ " xmlns:dc=\"http://www.omg.org/spec/DD/20100524/DC\""
			+ " xmlns:di=\"http://www.omg.org/spec/DD/20100524/DI\""
			+ " xmlns:tns=\"http://www.jboss.org/drools\">"
			+ " <process processType=\"Private\" isExecutable=\"true\" id=\"com.sample.hello\" name=\"com.sample.hello\" tns:packageName=\"com.sample\" >"
			+ " <startEvent id=\"_jbpm-unique-0\" name=\"Start\"  isInterrupting=\"false\">"
			+ " </startEvent>"
			+ " <scriptTask id=\"_jbpm-unique-1\" name=\"Action\" scriptFormat=\"http://www.java.com/java\" >"
			+ " <script>System.out.println(\"Hello World ! This is from the API\");</script>"
			+ " </scriptTask>"
			+ " <endEvent id=\"_jbpm-unique-2\" name=\"End\" >"
			+ " <terminateEventDefinition />"
			+ " </endEvent>"
			+ " <sequenceFlow id=\"_jbpm-unique-0-_jbpm-unique-1\" sourceRef=\"_jbpm-unique-0\" targetRef=\"_jbpm-unique-1\" />"
			+ " <sequenceFlow id=\"_jbpm-unique-1-_jbpm-unique-2\" sourceRef=\"_jbpm-unique-1\" targetRef=\"_jbpm-unique-2\" />"
			+ " </process>"
			+ " </definitions>"
			+ "]]></model>"
			+ " </workflowDto>"
			+ "";
}

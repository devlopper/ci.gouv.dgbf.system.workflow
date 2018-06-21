package ci.gouv.dgbf.system.workflow.server.persistence.impl.unit;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


public class WorkflowPersistenceImplUnitTest {

	@Test
	public void getBytesFromClassPath() throws Exception{
		InputStream inputStream = getClass().getResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2");
		Assert.assertNotNull(inputStream);
		byte[] bytes = IOUtils.toByteArray(getClass().getResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2"));
		Assert.assertNotNull(bytes);
		String string = new String(bytes);
		Assert.assertNotNull(string);

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document xmlDocument = builder.parse(new ByteArrayInputStream(bytes));
		XPath xPath = XPathFactory.newInstance().newXPath();
		
		String expression = "definitions/process";
		NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
		Assert.assertNotNull(nodeList.item(0));
		Assert.assertEquals("ci.gouv.dgbf.workflow.validation.pap", nodeList.item(0).getAttributes().getNamedItem("id").getTextContent());
		
	}
	
}

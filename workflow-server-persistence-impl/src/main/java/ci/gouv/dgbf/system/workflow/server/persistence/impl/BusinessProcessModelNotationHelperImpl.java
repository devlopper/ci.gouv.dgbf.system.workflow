package ci.gouv.dgbf.system.workflow.server.persistence.impl;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javax.inject.Singleton;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import ci.gouv.dgbf.system.workflow.server.persistence.api.BusinessProcessModelNotationHelper;

@Singleton
public class BusinessProcessModelNotationHelperImpl implements BusinessProcessModelNotationHelper,Serializable {
	private static final long serialVersionUID = 1L;

	public String getIdentifier(String string){
		String identifier = null;
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document xmlDocument = builder.parse(new ByteArrayInputStream(string.getBytes()));
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodeList = (NodeList) xPath.compile("definitions/process").evaluate(xmlDocument, XPathConstants.NODESET);
			identifier = nodeList.item(0).getAttributes().getNamedItem("id").getTextContent();	
		} catch(Exception exception) {
			exception.printStackTrace();
		}
		return identifier;
	}
	
}

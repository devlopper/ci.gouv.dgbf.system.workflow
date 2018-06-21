package ci.gouv.dgbf.system.workflow.server.persistence.impl;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironment;
import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.RuntimeManagerFactory;
import org.kie.api.task.UserGroupCallback;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.manager.context.ProcessInstanceIdContext;
import org.kie.internal.utils.KieHelper;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import ci.gouv.dgbf.system.workflow.server.persistence.api.PersistenceHelper;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Singleton @Getter @Setter @Accessors(chain=true)
public class PersistenceHelperImpl implements PersistenceHelper, Serializable {
	private static final long serialVersionUID = 1L;

	@PersistenceUnit private EntityManagerFactory entityManagerFactory;
	private Set<String> kieBaseResourceByClassPath;
	private KieBase kieBase;
	private UserGroupCallback userGroupCallback;
	private RuntimeEnvironment runtimeEnvironment;
	private RuntimeManager runtimeManager;
	@Inject private WorkflowPersistence workflowPersistence;
	
	@PostConstruct
	public void __listenPostConstruct__() {
		buildKieBase();
	}
	
	@Override
	public PersistenceHelper addKieBaseResourceByClassPath(Collection<String> paths) {
		if(paths!=null && !paths.isEmpty()){
			if(kieBaseResourceByClassPath == null)
				kieBaseResourceByClassPath = new LinkedHashSet<>();
			kieBaseResourceByClassPath.addAll(paths);
		}
		return this;
	}
	
	@Override
	public PersistenceHelper addKieBaseResourceByClassPath(String...paths) {
		if(paths!=null && paths.length>0){
			addKieBaseResourceByClassPath(Arrays.asList(paths));
		}
		return this;
	}
	
	@Override
	public PersistenceHelper buildKieBase() {
		KieHelper kieHelper = new KieHelper();
		for(Workflow index : workflowPersistence.readAll())
			kieHelper.addResource(ResourceFactory.newByteArrayResource(index.getBytes()));
		if(kieBaseResourceByClassPath!=null)
			for(String index : kieBaseResourceByClassPath)
				kieHelper.addResource(ResourceFactory.newClassPathResource(index));
		kieBase = kieHelper.build();
		return this;
	}
	
	@Override
	public KieSession getKieSession() {
		return getRuntimeManager().getRuntimeEngine(ProcessInstanceIdContext.get()).getKieSession();
	}
	
	@Override
	public RuntimeEnvironment getRuntimeEnvironment() {
		if(runtimeEnvironment == null)
			runtimeEnvironment = RuntimeEnvironmentBuilder.Factory.get().newDefaultBuilder().entityManagerFactory(entityManagerFactory)
				.knowledgeBase(getKieBase()).userGroupCallback(getUserGroupCallback()).get();
		return runtimeEnvironment;
	}
	
	@Override
	public RuntimeManager getRuntimeManager() {
		if(runtimeManager == null)
			runtimeManager = RuntimeManagerFactory.Factory.get().newSingletonRuntimeManager(getRuntimeEnvironment());
		return runtimeManager;
	}
	
	@Override
	public RuntimeEngine getRuntimeEngine() {
		return getRuntimeManager().getRuntimeEngine(ProcessInstanceIdContext.get());
	}
	
	@Override
	public String getProcessDefinitionIdentifier(byte[] bytes) {
		String identifier = null;
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document xmlDocument = builder.parse(new ByteArrayInputStream(bytes));
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodeList = (NodeList) xPath.compile("definitions/process").evaluate(xmlDocument, XPathConstants.NODESET);
			identifier = nodeList.item(0).getAttributes().getNamedItem("id").getTextContent();	
		} catch(Exception exception) {
			exception.printStackTrace();
		}
		return identifier;
	}
	
}

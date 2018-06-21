package ci.gouv.dgbf.system.workflow.server.persistence.impl;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironment;
import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.RuntimeManagerFactory;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.TaskSummary;
import org.kie.internal.KieInternalServices;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.process.CorrelationAwareProcessRuntime;
import org.kie.internal.runtime.manager.context.ProcessInstanceIdContext;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessTaskPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTask;

public class WorkflowProcessTaskPersistenceImpl extends AbstractEntityPersistenceImpl<WorkflowProcessTask> implements WorkflowProcessTaskPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Collection<WorkflowProcessTask> readByWorkflowProcessByUserIdentifier(WorkflowProcess workflowProcess,String userIdentifier) {
		String workflowIdentifier = null;
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document xmlDocument = builder.parse(new ByteArrayInputStream(workflowProcess.getWorkflow().getBytes()));
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodeList = (NodeList) xPath.compile("definitions/process").evaluate(xmlDocument, XPathConstants.NODESET);
			workflowIdentifier = nodeList.item(0).getAttributes().getNamedItem("id").getTextContent();	
		} catch(Exception exception) {
			exception.printStackTrace();
			return null;
		}
		
		RuntimeEnvironment runtimeEnvironment = RuntimeEnvironmentBuilder.Factory.get().newDefaultBuilder().entityManagerFactory(entityManagerFactory)
				.addAsset(ResourceFactory.newByteArrayResource(workflowProcess.getWorkflow().getBytes()), ResourceType.BPMN2).userGroupCallback(userGroupCallback).get();
		RuntimeManager runtimeManager = RuntimeManagerFactory.Factory.get().newSingletonRuntimeManager(runtimeEnvironment);
		
		RuntimeEngine runtimeEngine = runtimeManager.getRuntimeEngine(ProcessInstanceIdContext.get());
		
		TaskService taskService = runtimeEngine.getTaskService();
		
		KieSession session = runtimeEngine.getKieSession();
		ProcessInstance processInstance = ((CorrelationAwareProcessRuntime)session).getProcessInstance(KieInternalServices.Factory.get().newCorrelationKeyFactory()
				.newCorrelationKey(workflowProcess.getCode()));
		
		List<TaskSummary> taskSummaries = taskService.getTasksAssignedAsPotentialOwnerByProcessId(userIdentifier, workflowIdentifier);
		Collection<WorkflowProcessTask> workflowProcessTasks = new ArrayList<>();
		for(TaskSummary index : taskSummaries){
			WorkflowProcessTask workflowProcessTask = new WorkflowProcessTask();
			workflowProcessTasks.add(workflowProcessTask);
		}
		return workflowProcessTasks;
	}
	
}

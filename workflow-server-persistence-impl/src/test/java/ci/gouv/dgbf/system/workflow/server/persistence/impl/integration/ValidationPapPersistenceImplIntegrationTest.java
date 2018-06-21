package ci.gouv.dgbf.system.workflow.server.persistence.impl.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Properties;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jbpm.persistence.correlation.CorrelationKeyInfo;
import org.jbpm.persistence.correlation.CorrelationPropertyInfo;
import org.jbpm.services.task.identity.JBossUserGroupCallbackImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.audit.AuditService;
import org.kie.api.runtime.manager.audit.ProcessInstanceLog;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.TaskSummary;
import org.kie.internal.runtime.manager.context.ProcessInstanceIdContext;

import ci.gouv.dgbf.system.workflow.server.persistence.api.PersistenceHelper;

@RunWith(Arquillian.class)
public class ValidationPapPersistenceImplIntegrationTest {
	
	@Inject private PersistenceHelper persistenceHelper;
	
	@Before
	public void listenBefore(){
		Properties properties = new Properties();
		properties.put("charge_etude", "");
		properties.put("sous_directeur", "");
		properties.put("directeur", "");	
		persistenceHelper.setUserGroupCallback(new JBossUserGroupCallbackImpl(properties));
		
		persistenceHelper.addKieBaseResourceByClassPath("/bpmn/withhuman/Validation du PAP.bpmn2").buildKieBase();
	}
	
	/**/
	
	@Test
	public void isChargeEtudeEstPremierIntervenant() {
		RuntimeManager runtimeManager = persistenceHelper.getRuntimeManager();
		RuntimeEngine runtimeEngine = runtimeManager.getRuntimeEngine(ProcessInstanceIdContext.get());
		KieSession session = runtimeEngine.getKieSession();
		TaskService taskService = runtimeEngine.getTaskService();
		
		AuditService auditService = runtimeEngine.getAuditService();
		
		ProcessInstance processInstance = session.startProcess("ci.gouv.dgbf.workflow.validation.pap");
		//We map process instance to our business reference
		CorrelationKeyInfo correlationKeyInfo = new CorrelationKeyInfo();
		correlationKeyInfo.setProcessInstanceId(processInstance.getId());  
		correlationKeyInfo.setName("processKey");  
		correlationKeyInfo.addProperty(new CorrelationPropertyInfo("property", "PAP01"));  
		
		//System.out.println("TS02 : "+taskService.getTasksAssignedAsPotentialOwner("charge_etude", "en-UK"));
		
		//Assert state
		ProcessInstanceLog processInstanceLog = auditService.findProcessInstance(processInstance.getId());
		assertNotNull("Process instance has not been found", processInstanceLog);
		assertEquals("Process instance is not active", new Integer(ProcessInstance.STATE_ACTIVE), processInstanceLog.getStatus());
		
		
		//assertProcessInstanceActive(processInstance.getId());
		//assertNodeExists(processInstance, "Structure Analysis");
		
		//assertNodeTriggered(processInstance.getId(), "Structure Analysis");
		// let sale structure analyser execute Task Structure Analysis
		
		//WorkflowProcessTaskPersistenceImpl workflowProcessTaskPersistenceImpl = new WorkflowProcessTaskPersistenceImpl();
		//workflowProcessTaskPersistenceImpl.setEntityManagerFactory(entityManagerFactory).setUserGroupCallback(userGroupCallback);
		
		List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwnerByProcessId("charge_etude", "ci.gouv.dgbf.workflow.validation.pap");
		TaskSummary task = list.get(0);
		System.out.println("Charge etude is executing task 1 " + task.getName());
		//System.out.println("Charge etude is executing task 0 " + workflowProcessTaskPersistenceImpl.readByWorkflowIdentifierByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap", "charge_etude"));
		taskService.start(task.getId(), "charge_etude");
		taskService.complete(task.getId(), "charge_etude", null);
		/*
		//assertNodeTriggered(processInstance.getId(), "Content Analysis");
		list = taskService.getTasksAssignedAsPotentialOwner("ContentAnalyser", "en-UK");
		task = list.get(0);
		System.out.println("ContentAnalyser is executing task " + task.getName());
		taskService.start(task.getId(), "ContentAnalyser");
		taskService.complete(task.getId(), "ContentAnalyser", null);
		
		//assertNodeTriggered(processInstance.getId(), "Processing");
		list = taskService.getTasksAssignedAsPotentialOwner("Processor", "en-UK");
		task = list.get(0);
		System.out.println("Processor is executing task " + task.getName());
		taskService.start(task.getId(), "Processor");
		taskService.complete(task.getId(), "Processor", null);
		
		//assertNodeTriggered(processInstance.getId(), "Validation");
		list = taskService.getTasksAssignedAsPotentialOwner("Validator", "en-UK");
		task = list.get(0);
		System.out.println("Validator is executing task " + task.getName());
		taskService.start(task.getId(), "Validator");
		taskService.complete(task.getId(), "Validator", null);
		
		//assertProcessInstanceCompleted(processInstance.getId());
		*/
		runtimeManager.disposeRuntimeEngine(runtimeEngine);
		runtimeManager.close();
	}
	
	/* Deployment */
	
	@org.jboss.arquillian.container.test.api.Deployment
	public static WebArchive createArchive(){
		return ShrinkWrap.create(WebArchive.class)
				.addAsResource("project-defaults-test.yml", "project-defaults.yml")
				//.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")	
				.addAsResource("bpmn/withhuman/Validation du PAP.bpmn2", "bpmn/withhuman/Validation du PAP.bpmn2")
				.addAsLibraries(Maven.resolver().loadPomFromFile("pom-test.xml").importRuntimeDependencies().resolve().withTransitivity().asFile())
		;
	}
}

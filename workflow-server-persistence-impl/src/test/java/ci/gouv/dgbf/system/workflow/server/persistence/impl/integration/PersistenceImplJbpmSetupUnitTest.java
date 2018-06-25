package ci.gouv.dgbf.system.workflow.server.persistence.impl.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import org.apache.commons.io.IOUtils;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jbpm.services.api.DefinitionService;
import org.jbpm.services.api.DeploymentService;
import org.jbpm.services.api.ProcessService;
import org.jbpm.services.api.RuntimeDataService;
import org.jbpm.services.api.UserTaskService;
import org.jbpm.services.api.model.DeployedUnit;
import org.jbpm.services.api.model.DeploymentUnit;
import org.jbpm.services.api.model.ProcessDefinition;
import org.jbpm.services.task.identity.JBossUserGroupCallbackImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironment;
import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.RuntimeManagerFactory;
import org.kie.api.runtime.manager.audit.AuditService;
import org.kie.api.runtime.manager.audit.NodeInstanceLog;
import org.kie.api.runtime.manager.audit.ProcessInstanceLog;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;
import org.kie.api.task.UserGroupCallback;
import org.kie.api.task.model.TaskSummary;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.manager.context.ProcessInstanceIdContext;

import ci.gouv.dgbf.system.workflow.server.persistence.api.PersistenceHelper;

@RunWith(Arquillian.class)
public class PersistenceImplJbpmSetupUnitTest {
	
	@PersistenceUnit private EntityManagerFactory entityManagerFactory;
	@PersistenceContext private EntityManager entityManager;
	@Inject private DeploymentService deploymentService;
	@Inject private RuntimeDataService runtimeDataService;
	@Inject private DefinitionService definitionService;
	@Inject private UserTaskService userTaskService;
	@Inject private ProcessService processService;
	@Inject private PersistenceHelper persistenceHelper;
	
	@Test
	public void isPersistenceHelperRuntimeEnvironmentNotNull(){
		Assert.assertNotNull(persistenceHelper.getRuntimeEnvironment());
	}
	
	@Test
	public void isPersistenceHelperRuntimeManagerNotNull(){
		RuntimeManager runtimeManager = persistenceHelper.getRuntimeManager();
		Assert.assertNotNull(runtimeManager);
		persistenceHelper.closeRuntimeManager();
	}
	
	@Test
	public void isDeploymentServiceNotNull(){
		Assert.assertNotNull(deploymentService);
	}
	
	@Test
	public void isDeploymentActivated(){
		deploymentService.activate("MyDeploymentId001");
	}
	
	@Test
	public void isDeployedUnitNotNull(){
		deploymentService.activate("MyDeploymentId002");
		DeployedUnit deployedUnit = deploymentService.getDeployedUnit("MyDeploymentId002");
		Assert.assertNotNull(deployedUnit);
	}
	
	@Test
	public void isRuntimeDataServiceNotNull(){
		Assert.assertNotNull(runtimeDataService);
	}
	
	@Test
	public void isDefinitionServiceNotNull(){
		Assert.assertNotNull(definitionService);
	}
	
	@Test
	public void isProcessDefinitionBuilt(){
		String deploymentIdentifier = "BuildProcessDefinition001";
		deploymentService.activate(deploymentIdentifier);
		ProcessDefinition processDefinition = null;
		try {
			processDefinition = definitionService.buildProcessDefinition(deploymentIdentifier, IOUtils.toString(getClass().getResourceAsStream("/bpmn/demo.bpmn")
					,Charset.forName("UTF-8")), null, true);
		} catch (IllegalArgumentException | IOException e) {
			fail(e.getMessage());
		}
		Assert.assertNotNull(processDefinition);
		Assert.assertEquals("com.sample.hello", processDefinition.getId());
		Assert.assertNotNull(definitionService.getProcessDefinition(deploymentIdentifier, "com.sample.hello"));
		deploymentService.deactivate(deploymentIdentifier);
	}
	
	@Test
	public void isProcessDefinitionBuiltUpdated(){
		String deploymentIdentifier = "BuildProcessDefinition001";
		deploymentService.activate(deploymentIdentifier);
		
		ProcessDefinition processDefinition = null;
		try {
			definitionService.buildProcessDefinition(deploymentIdentifier, IOUtils.toString(getClass().getResourceAsStream("/bpmn/demo.bpmn")
					,Charset.forName("UTF-8")), null, true);
		} catch (IllegalArgumentException | IOException e) {
			fail(e.getMessage());
		}
		
		processDefinition = definitionService.getProcessDefinition(deploymentIdentifier, "com.sample.hello");
		Assert.assertEquals("Hello Process", processDefinition.getName());
		
		try {
			definitionService.buildProcessDefinition(deploymentIdentifier, IOUtils.toString(getClass().getResourceAsStream("/bpmn/demo_another_name.bpmn")
					,Charset.forName("UTF-8")), null, true);
		} catch (IllegalArgumentException | IOException e) {
			fail(e.getMessage());
		}
		
		processDefinition = definitionService.getProcessDefinition(deploymentIdentifier, "com.sample.hello");
		Assert.assertEquals("Hello Process My name has changed", processDefinition.getName());
		
		deploymentService.deactivate(deploymentIdentifier);
	}
	
	@Test
	public void isUserTaskServiceNotNull(){
		Assert.assertNotNull(userTaskService);
	}
	
	@Test
	public void isProcessServiceNotNull(){
		Assert.assertNotNull(processService);
	}
	
	@Test
	public void isEntityManagerFactoryNotNull(){
		Assert.assertNotNull(entityManagerFactory);
	}
	
	@Test
	public void isEntityManagerNotNull(){
		Assert.assertNotNull(entityManager);	
	}
	
	@Test
	public void isSelectProcessInstanceInfoQueryNotNull(){
		Assert.assertNotNull(entityManager.createQuery("SELECT p FROM ProcessInstanceInfo p"));
	}
	
	@Test
	public void isJbpmRuntimeEnvironmentNotNull(){
		RuntimeEnvironment runtimeEnvironment = RuntimeEnvironmentBuilder.Factory.get().newDefaultBuilder().entityManagerFactory(entityManagerFactory)
				.addAsset(ResourceFactory.newClassPathResource("bpmn/demo.bpmn"), ResourceType.BPMN2).get();
		Assert.assertNotNull(runtimeEnvironment);
	}
	
	@Test
	public void isJbpmRuntimeManagerNotNull(){
		RuntimeManager runtimeManager = getRuntimeManager("bpmn/demo.bpmn");
		Assert.assertNotNull(runtimeManager);
		runtimeManager.close();
	}
	
	@Test
	public void isJbpmRuntimeEngineNotNull(){
		RuntimeManager runtimeManager = getRuntimeManager("bpmn/demo.bpmn");
		RuntimeEngine runtimeEngine = runtimeManager.getRuntimeEngine(ProcessInstanceIdContext.get());
		Assert.assertNotNull(runtimeEngine);
		runtimeManager.close();
	}
	
	@Test
	public void isJbpmSessionNotNull(){
		RuntimeManager runtimeManager = getRuntimeManager("bpmn/demo.bpmn");
		RuntimeEngine runtimeEngine = runtimeManager.getRuntimeEngine(ProcessInstanceIdContext.get());
		KieSession knowledgeSession = runtimeEngine.getKieSession();
		Assert.assertNotNull(knowledgeSession);
		runtimeManager.close();
	}
	
	
	@Test
	public void executeHumanProcess01() {
		RuntimeManager runtimeManager = getRuntimeManager("bpmn/withhuman/process01.bpmn");
		RuntimeEngine runtimeEngine = runtimeManager.getRuntimeEngine(ProcessInstanceIdContext.get());
		KieSession session = runtimeEngine.getKieSession();
		TaskService taskService = runtimeEngine.getTaskService();
		AuditService auditService = runtimeEngine.getAuditService();
		
		ProcessInstance processInstance = session.startProcess("org.cyk.playground.bpm.jboss.process01");

		//Assert state
		ProcessInstanceLog processInstanceLog = auditService.findProcessInstance(processInstance.getId());
        assertNotNull("Process instance has not been found", processInstanceLog);
        assertEquals("Process instance is not active", new Integer(ProcessInstance.STATE_ACTIVE), processInstanceLog.getStatus());
		
        //Assert triggered
        List<String> names = new ArrayList<>(Arrays.asList("Task 1"));
        List<? extends NodeInstanceLog> nodeInstanceLogs = auditService.findNodeInstances(processInstance.getId());
        if (nodeInstanceLogs != null) {
            for (NodeInstanceLog index : nodeInstanceLogs) {
                String nodeName = index.getNodeName();
                if ((index.getType() == NodeInstanceLog.TYPE_ENTER || index.getType() == NodeInstanceLog.TYPE_EXIT) && names.contains(nodeName)) {
                    names.remove(nodeName);
                }
            }
        }
        if (!names.isEmpty()) {
            String s = names.get(0);
            for (int i = 1; i < names.size(); i++) {
                s += ", " + names.get(i);
            }
            fail("Node(s) not executed: " + s);
        }
        
		// let john execute Task 1
		List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
		TaskSummary task = list.get(0);
		System.out.println("John is executing task " + task.getName());
		taskService.start(task.getId(), "john");
		taskService.complete(task.getId(), "john", null);

		//assertNodeTriggered(processInstance.getId(), "Task 2");
		
		// let mary execute Task 2
		list = taskService.getTasksAssignedAsPotentialOwner("mary", "en-UK");
		task = list.get(0);
		System.out.println("Mary is executing task " + task.getName());
		taskService.start(task.getId(), "mary");
		taskService.complete(task.getId(), "mary", null);

		//assertProcessInstanceCompleted(processInstance.getId());
		
		runtimeManager.disposeRuntimeEngine(runtimeEngine);
		runtimeManager.close();
	}
	
	@Test
	public void executeHumanProcessValidateSale() {
		Properties properties = new Properties();
		properties.put("StructureAnalyser", "");
		properties.put("ContentAnalyser", "");
		properties.put("Processor", "");
		properties.put("Validator", "");
		//userGroupCallback = new JBossUserGroupCallbackImpl(properties);
		
		RuntimeManager runtimeManager = getRuntimeManager("bpmn/withhuman/Validate Sale.bpmn2",new JBossUserGroupCallbackImpl(properties));
		RuntimeEngine runtimeEngine = runtimeManager.getRuntimeEngine(ProcessInstanceIdContext.get());
		KieSession session = runtimeEngine.getKieSession();
		
		TaskService taskService = runtimeEngine.getTaskService();
		AuditService auditService = runtimeEngine.getAuditService();
		
		ProcessInstance processInstance = session.startProcess("org.cyk.playground.bpm.jboss.ValidateSale");
		
		//Assert state
		ProcessInstanceLog processInstanceLog = auditService.findProcessInstance(processInstance.getId());
		assertNotNull("Process instance has not been found", processInstanceLog);
		assertEquals("Process instance is not active", new Integer(ProcessInstance.STATE_ACTIVE), processInstanceLog.getStatus());
		
		//assertProcessInstanceActive(processInstance.getId());
		//assertNodeExists(processInstance, "Structure Analysis");
		
		//assertNodeTriggered(processInstance.getId(), "Structure Analysis");
		// let sale structure analyser execute Task Structure Analysis
		List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner("StructureAnalyser", "en-UK");
		TaskSummary task = list.get(0);
		System.out.println("StructureAnalyser is executing task " + task.getName());
		taskService.start(task.getId(), "StructureAnalyser");
		taskService.complete(task.getId(), "StructureAnalyser", null);
	
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
		
		runtimeManager.disposeRuntimeEngine(runtimeEngine);
		runtimeManager.close();
	}
	
	
	private RuntimeManager getRuntimeManager(String processFileName,UserGroupCallback userGroupCallback){
		RuntimeEnvironment runtimeEnvironment = RuntimeEnvironmentBuilder.Factory.get().newDefaultBuilder().entityManagerFactory(entityManagerFactory)
				.addAsset(ResourceFactory.newClassPathResource(processFileName), ResourceType.BPMN2).userGroupCallback(userGroupCallback).get();
		return RuntimeManagerFactory.Factory.get().newSingletonRuntimeManager(runtimeEnvironment);
	}
	
	private RuntimeManager getRuntimeManager(String processFileName){
		return getRuntimeManager(processFileName, null);
	}
	
	/* Deployment */
	
	@org.jboss.arquillian.container.test.api.Deployment
	public static WebArchive createArchive(){
		return ShrinkWrap.create(WebArchive.class)
				.addAsResource("project-defaults-test.yml", "project-defaults.yml")
				//.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")	
				.addAsResource("bpmn/demo.bpmn", "bpmn/demo.bpmn")	
				.addAsResource("bpmn/demo_another_name.bpmn", "bpmn/demo_another_name.bpmn")	
				.addAsResource("bpmn/withhuman/process01.bpmn", "bpmn/withhuman/process01.bpmn")
				.addAsResource("bpmn/withhuman/Validate Sale.bpmn2", "bpmn/withhuman/Validate Sale.bpmn2")
				.addAsLibraries(Maven.resolver().loadPomFromFile("pom-test.xml").importRuntimeDependencies().resolve().withTransitivity().asFile())
		;
	}
}

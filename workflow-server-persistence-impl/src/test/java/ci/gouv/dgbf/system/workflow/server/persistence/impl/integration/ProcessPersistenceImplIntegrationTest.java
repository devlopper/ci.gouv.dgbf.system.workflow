package ci.gouv.dgbf.system.workflow.server.persistence.impl.integration;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jbpm.services.task.identity.JBossUserGroupCallbackImpl;
import org.junit.Assert;
import org.junit.Before;
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
import org.kie.api.task.UserGroupCallback;
import org.kie.internal.KieInternalServices;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.process.CorrelationAwareProcessRuntime;
import org.kie.internal.runtime.manager.context.ProcessInstanceIdContext;

@RunWith(Arquillian.class)
public class ProcessPersistenceImplIntegrationTest {
	
	private UserGroupCallback userGroupCallback;
	
	/**/
	
	@PersistenceUnit private EntityManagerFactory entityManagerFactory;
	private String processFileName = "bpmn/withhuman/Validation du PAP.bpmn2";
	
	/**/
	
	@Test
	public void isProcessInstanceCreatedWithCorrelationKey() {
		RuntimeManager runtimeManager = getRuntimeManager(processFileName,userGroupCallback);
		RuntimeEngine runtimeEngine = runtimeManager.getRuntimeEngine(ProcessInstanceIdContext.get());
		KieSession session = runtimeEngine.getKieSession();
		AuditService auditService = runtimeEngine.getAuditService();
		
		Assert.assertEquals(0, auditService.findProcessInstances().size());
		Assert.assertNull(auditService.findProcessInstance(123));
		
		Assert.assertNull(((CorrelationAwareProcessRuntime)session).getProcessInstance(KieInternalServices.Factory.get().newCorrelationKeyFactory().newCorrelationKey("PAP001")));
		Assert.assertNull(((CorrelationAwareProcessRuntime)session).getProcessInstance(KieInternalServices.Factory.get().newCorrelationKeyFactory().newCorrelationKey("PAP002")));
		Assert.assertNull(((CorrelationAwareProcessRuntime)session).getProcessInstance(KieInternalServices.Factory.get().newCorrelationKeyFactory().newCorrelationKey("PAP003")));
		
		Long processInstancePAP001 = ((CorrelationAwareProcessRuntime)session).startProcess("ci.gouv.dgbf.workflow.validation.pap",KieInternalServices.Factory.get().newCorrelationKeyFactory().newCorrelationKey("PAP001"),null).getId();
		Assert.assertEquals(1, auditService.findProcessInstances().size());
		Assert.assertEquals(1, auditService.findProcessInstances("ci.gouv.dgbf.workflow.validation.pap").size());
		Assert.assertNotNull(auditService.findProcessInstance(processInstancePAP001));
		Assert.assertNotNull(((CorrelationAwareProcessRuntime)session).getProcessInstance(KieInternalServices.Factory.get().newCorrelationKeyFactory().newCorrelationKey("PAP001")));
		
		Long processInstancePAP002 = ((CorrelationAwareProcessRuntime)session).startProcess("ci.gouv.dgbf.workflow.validation.pap",KieInternalServices.Factory.get().newCorrelationKeyFactory().newCorrelationKey("PAP002"),null).getId();
		Assert.assertEquals(2, auditService.findProcessInstances().size());
		Assert.assertEquals(2, auditService.findProcessInstances("ci.gouv.dgbf.workflow.validation.pap").size());
		Assert.assertNotNull(auditService.findProcessInstance(processInstancePAP002));
		Assert.assertNotNull(((CorrelationAwareProcessRuntime)session).getProcessInstance(KieInternalServices.Factory.get().newCorrelationKeyFactory().newCorrelationKey("PAP001")));
		
		runtimeManager.disposeRuntimeEngine(runtimeEngine);
		runtimeManager.close();
		
		runtimeManager = getRuntimeManager(processFileName,userGroupCallback);
		runtimeEngine = runtimeManager.getRuntimeEngine(ProcessInstanceIdContext.get());
		session = runtimeEngine.getKieSession();
		auditService = runtimeEngine.getAuditService();
		
		Assert.assertEquals(2, auditService.findProcessInstances().size());
		Assert.assertEquals(2, auditService.findProcessInstances("ci.gouv.dgbf.workflow.validation.pap").size());
		Assert.assertNotNull(((CorrelationAwareProcessRuntime)session).getProcessInstance(KieInternalServices.Factory.get().newCorrelationKeyFactory().newCorrelationKey("PAP001")));
		Assert.assertNotNull(((CorrelationAwareProcessRuntime)session).getProcessInstance(KieInternalServices.Factory.get().newCorrelationKeyFactory().newCorrelationKey("PAP002")));
		
		runtimeManager.disposeRuntimeEngine(runtimeEngine);
		runtimeManager.close();
	}
	
	
	private RuntimeManager getRuntimeManager(String processFileName,UserGroupCallback userGroupCallback){
		RuntimeEnvironment runtimeEnvironment = RuntimeEnvironmentBuilder.Factory.get().newDefaultBuilder().entityManagerFactory(entityManagerFactory)
				.addAsset(ResourceFactory.newClassPathResource(processFileName), ResourceType.BPMN2).userGroupCallback(userGroupCallback).get();
		return RuntimeManagerFactory.Factory.get().newSingletonRuntimeManager(runtimeEnvironment);
	}
	
	/* Configuration */
	
	@Before
	public void listenBefore(){
		Properties properties = new Properties();
		properties.put("charge_etude", "");
		properties.put("sous_directeur", "");
		properties.put("directeur", "");	
		userGroupCallback = new JBossUserGroupCallbackImpl(properties);
	}
	
	/* Deployment */
	
	@org.jboss.arquillian.container.test.api.Deployment
	public static WebArchive createArchive(){
		return ShrinkWrap.create(WebArchive.class)
				.addAsResource("project-defaults-test.yml", "project-defaults.yml")
				.addAsResource("bpmn/withhuman/Validation du PAP.bpmn2", "bpmn/withhuman/Validation du PAP.bpmn2")
				.addAsLibraries(Maven.resolver().loadPomFromFile("pom-test.xml").importRuntimeDependencies().resolve().withTransitivity().asFile())
		;
	}
}

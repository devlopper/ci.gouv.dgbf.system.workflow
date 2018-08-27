package ci.gouv.dgbf.system.workflow.server.persistence.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.apache.commons.io.IOUtils;
import org.jbpm.services.api.DefinitionService;
import org.jbpm.services.api.DeploymentService;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironment;
import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.RuntimeManagerFactory;
import org.kie.api.task.TaskService;
import org.kie.api.task.UserGroupCallback;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.manager.context.ProcessInstanceIdContext;

import ci.gouv.dgbf.system.workflow.server.persistence.api.PersistenceHelper;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Singleton @Getter @Setter @Accessors(chain=true)
public class PersistenceHelperImpl implements PersistenceHelper, Serializable {
	private static final long serialVersionUID = 1L;

	private Collection<byte[]> processDefinitions;
	//TODO this one should come from database
	private String deploymentIdentifier;
	@Inject private DeploymentService deploymentService;
	@Inject private DefinitionService definitionService;
	
	@PersistenceUnit private EntityManagerFactory entityManagerFactory;
	private Set<String> kieBaseResourceByClassPath;
	private KieBase kieBase;
	private UserGroupCallback userGroupCallback;
	private RuntimeEnvironment runtimeEnvironment;
	private RuntimeManager runtimeManager;
	
	@PostConstruct
	public void __listenPostConstruct__() {
		//deploymentIdentifier = "Deployment";
		//buildKieBase();
		
		//FIXME find a way to get user identifiers
		java.util.Properties properties = new java.util.Properties();
		properties.put("charge_etude", "");
		properties.put("sous_directeur", "");
		properties.put("directeur", "");	
		setUserGroupCallback(new org.jbpm.services.task.identity.JBossUserGroupCallbackImpl(properties));
		
	}
	
	@Override
	public PersistenceHelper initialise() {
		buildKieBase();
		return this;
	}
	
	public String getDeploymentIdentifier(){
		if(deploymentIdentifier == null){
			deploymentIdentifier = "Deployment";
			deploymentService.activate(deploymentIdentifier);
		}
		return deploymentIdentifier;
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
		/*KieHelper kieHelper = new KieHelper();
		for(Workflow index : CDI.current().select(WorkflowPersistence.class).get().readAll())
			kieHelper.addResource(ResourceFactory.newByteArrayResource(index.getModel().getBytes()));
		if(kieBaseResourceByClassPath!=null)
			for(String index : kieBaseResourceByClassPath)
				kieHelper.addResource(ResourceFactory.newClassPathResource(index));
		kieBase = kieHelper.build();
		*/
		
		KieServices kieServices = KieServices.Factory.get();
		
		KieContainer kieContainer = kieServices.newKieContainer( kieServices.getRepository().getDefaultReleaseId() );

		kieBase = kieContainer.getKieBase();
		
		return this;
	}
	
	@Override
	public KieSession getKieSession() {
		return getRuntimeManager().getRuntimeEngine(ProcessInstanceIdContext.get()).getKieSession();
	}
	
	@Override
	public PersistenceHelper buildRuntimeEnvironment() {
		UserGroupCallback userGroupCallback = getUserGroupCallback();
		RuntimeEnvironmentBuilder runtimeEnvironmentBuilder = RuntimeEnvironmentBuilder.Factory.get().newDefaultBuilder().entityManagerFactory(getEntityManagerFactory())
				.knowledgeBase(getKieBase()).userGroupCallback(userGroupCallback);
		if(processDefinitions!=null)
			for(byte[] index : processDefinitions){
				runtimeEnvironmentBuilder.addAsset(ResourceFactory.newByteArrayResource(index), ResourceType.BPMN2);
				definitionService.buildProcessDefinition(getDeploymentIdentifier(),  new String(index), null, Boolean.TRUE);
			}
		runtimeEnvironment = runtimeEnvironmentBuilder.get();
		return this;
	}
	
	public RuntimeEnvironment getRuntimeEnvironment(){
		if(runtimeEnvironment == null)
			buildRuntimeEnvironment();
		return runtimeEnvironment;
	}
	
	@Override
	public RuntimeManager getRuntimeManager() {
		if(runtimeManager == null)
			runtimeManager = RuntimeManagerFactory.Factory.get().newSingletonRuntimeManager(getRuntimeEnvironment());
		return runtimeManager;
	}
	
	@Override
	public PersistenceHelper closeRuntimeManager() {
		if(runtimeManager!=null){
			runtimeManager.close();
			runtimeManager = null;
		}
		return this;
	}
	
	@Override
	public RuntimeEngine getRuntimeEngine() {
		return getRuntimeManager().getRuntimeEngine(ProcessInstanceIdContext.get());
	}
	
	@Override
	public TaskService getTaskService() {
		return getRuntimeEngine().getTaskService();
	}
	
	@Override
	public PersistenceHelper setProcessDefinitions(Collection<byte[]> processDefinitions) {
		this.processDefinitions = processDefinitions;
		return this;
	}
	
	@Override
	public PersistenceHelper addProcessDefinitions(Collection<byte[]> processDefinitions) {
		if(processDefinitions!=null && !processDefinitions.isEmpty()){
			if(this.processDefinitions == null)
				this.processDefinitions = new ArrayList<>();
			this.processDefinitions.addAll(processDefinitions);
		}
		return this;
	}
	
	@Override
	public PersistenceHelper addProcessDefinitions(byte[]... processDefinitions) {
		if(processDefinitions!=null && processDefinitions.length>0)
			addProcessDefinitions(Arrays.asList(processDefinitions));
		return this;
	}
	
	@Override
	public PersistenceHelper addProcessDefinitionFromClassPath(Collection<String> paths) {
		if(paths!=null && !paths.isEmpty()){
			Collection<byte[]> bytes = new ArrayList<>();	
			for(String index : paths)
				try {
					bytes.add(IOUtils.toByteArray(getClass().getResourceAsStream(index)));
					//definitionService.buildProcessDefinition(getDeploymentIdentifier(),  IOUtils.toString(getClass().getResourceAsStream(index),Charset.forName("UTF-8")), null, Boolean.TRUE);
				} catch (IllegalArgumentException | IOException e) {
					e.printStackTrace();
				}
			addProcessDefinitions(bytes);
		}
		return this;
	}
	
	@Override
	public PersistenceHelper addProcessDefinitionFromClassPath(String... paths) {
		if(paths!=null && paths.length>0){
			addProcessDefinitionFromClassPath(Arrays.asList(paths));
		}
		return this;
	}
	
	@Override
	public PersistenceHelper addProcessDefinitionsFromWorkflow(Collection<Workflow> workflows) {
		if(workflows!=null && !workflows.isEmpty()){
			Collection<byte[]> bytes = new ArrayList<>();	
			for(Workflow index : workflows)
				bytes.add(index.getModel().getBytes());
			addProcessDefinitions(bytes);
		}
		return this;
	}
	
	@Override
	public PersistenceHelper addProcessDefinitionsFromWorkflow(Workflow... workflows) {
		if(workflows!=null && workflows.length>0)
			addProcessDefinitionsFromWorkflow(Arrays.asList(workflows));
		return this;
	}
}

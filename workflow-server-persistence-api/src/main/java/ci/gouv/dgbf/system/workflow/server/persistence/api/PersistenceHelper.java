package ci.gouv.dgbf.system.workflow.server.persistence.api;

import java.util.Collection;

import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironment;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.task.UserGroupCallback;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;

public interface PersistenceHelper {

	/* JBPM functions */
	
	String getDeploymentIdentifier();
	PersistenceHelper setDeploymentIdentifier(String identifier);
	
	@Deprecated PersistenceHelper addKieBaseResourceByClassPath(Collection<String> paths);
	@Deprecated PersistenceHelper addKieBaseResourceByClassPath(String...paths);
	
	@Deprecated
	PersistenceHelper buildKieBase();
	
	@Deprecated KieBase getKieBase();
	
	PersistenceHelper setUserGroupCallback(UserGroupCallback userGroupCallback);
	UserGroupCallback getUserGroupCallback();
	
	PersistenceHelper buildRuntimeEnvironment();
	RuntimeEnvironment getRuntimeEnvironment();
	
	RuntimeManager getRuntimeManager();
	PersistenceHelper closeRuntimeManager();
	
	RuntimeEngine getRuntimeEngine();
	KieSession getKieSession();
	
	PersistenceHelper initialise();
	
	Collection<byte[]> getProcessDefinitions();
	PersistenceHelper setProcessDefinitions(Collection<byte[]> processDefinitions);
	PersistenceHelper addProcessDefinitions(Collection<byte[]> processDefinitions);
	PersistenceHelper addProcessDefinitions(byte[]...processDefinitions);
	
	PersistenceHelper addProcessDefinitionsFromWorkflow(Collection<Workflow> workflows);
	PersistenceHelper addProcessDefinitionsFromWorkflow(Workflow...workflows);
	PersistenceHelper addProcessDefinitionFromClassPath(Collection<String> paths);
	PersistenceHelper addProcessDefinitionFromClassPath(String...paths);
	
}

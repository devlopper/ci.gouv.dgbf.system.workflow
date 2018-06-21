package ci.gouv.dgbf.system.workflow.server.persistence.api;

import java.util.Collection;

import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironment;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.task.UserGroupCallback;

public interface PersistenceHelper {

	/* JBPM functions */
	
	PersistenceHelper addKieBaseResourceByClassPath(Collection<String> paths);
	PersistenceHelper addKieBaseResourceByClassPath(String...paths);
	
	PersistenceHelper buildKieBase();
	
	KieBase getKieBase();
	
	PersistenceHelper setUserGroupCallback(UserGroupCallback userGroupCallback);
	UserGroupCallback getUserGroupCallback();
	
	RuntimeEnvironment getRuntimeEnvironment();
	RuntimeManager getRuntimeManager();
	RuntimeEngine getRuntimeEngine();
	KieSession getKieSession();
	
	String getProcessDefinitionIdentifier(byte[] bytes);
}

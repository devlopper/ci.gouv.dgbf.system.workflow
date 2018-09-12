package ci.gouv.dgbf.system.workflow.server.persistence.impl;

import java.util.Collection;

import org.cyk.utility.helper.Helper;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironment;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.task.UserGroupCallback;

public interface JbpmHelper extends Helper {

	RuntimeEnvironment getRuntimeEnvironment();
	JbpmHelper setRuntimeEnvironment(RuntimeEnvironment runtimeEnvironment);
	JbpmHelper buildRuntimeEnvironment(); 
	JbpmHelper destroyRuntimeEnvironment(); 
	
	UserGroupCallback getUserGroupCallback();
	JbpmHelper setUserGroupCallback(UserGroupCallback userGroupCallback);
	
	RuntimeManager getRuntimeManager();
	JbpmHelper setRuntimeManager(RuntimeManager runtimeManager);
	
	RuntimeEngine getRuntimeEngine();
	
	String getProcessesMavenRepositoryFolder();
	JbpmHelper setProcessesMavenRepositoryFolder(String processesMavenRepositoryFolder);
	
	Collection<String> getProcessesFromMavenRepository();

	/**/
	
	String PROCESSES_MAVEN_REPOSITORY_FOLDER_ENVIRONMENT_VARIABLE = "ci.gouv.dgbf.system.workflow.jbpm.maven.repository.path";
}

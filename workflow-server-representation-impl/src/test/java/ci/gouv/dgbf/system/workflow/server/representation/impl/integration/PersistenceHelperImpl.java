package ci.gouv.dgbf.system.workflow.server.representation.impl.integration;

import java.io.Serializable;
import java.util.Properties;

import javax.enterprise.inject.Alternative;
import javax.inject.Singleton;

import org.jbpm.services.task.identity.JBossUserGroupCallbackImpl;

import ci.gouv.dgbf.system.workflow.server.persistence.api.PersistenceHelper;

@Singleton @Alternative
public class PersistenceHelperImpl extends ci.gouv.dgbf.system.workflow.server.persistence.impl.PersistenceHelperImpl implements PersistenceHelper, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __listenPostConstruct__() {
		System.out.println("PersistenceHelperImpl.__listenPostConstruct__() ALTERNATIVE ********************************************************");
		super.__listenPostConstruct__();
		Properties properties = new Properties();
		properties.put("charge_etude", "");
		properties.put("sous_directeur", "");
		properties.put("directeur", "");	
		setUserGroupCallback(new JBossUserGroupCallbackImpl(properties));
	}
	
}

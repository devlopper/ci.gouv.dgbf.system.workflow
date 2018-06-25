package ci.gouv.dgbf.system.workflow.server.persistence.impl;

import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.jbpm.runtime.manager.impl.RuntimeEnvironmentBuilder;
import org.jbpm.services.api.DeploymentService;
import org.jbpm.services.cdi.Kjar;
import org.jbpm.services.cdi.producer.UserGroupInfoProducer;
import org.jbpm.services.task.audit.JPATaskLifeCycleEventListener;
import org.jbpm.services.task.lifecycle.listeners.TaskLifeCycleEventListener;
import org.kie.api.runtime.manager.RuntimeEnvironment;
import org.kie.internal.identity.IdentityProvider;
import org.kie.internal.runtime.manager.cdi.qualifier.PerProcessInstance;
import org.kie.internal.runtime.manager.cdi.qualifier.PerRequest;
import org.kie.internal.runtime.manager.cdi.qualifier.Singleton;
import org.kie.internal.task.api.UserInfo;

public class CdiBeanProducer { 

	@PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Inject
    //@org.jbpm.services.cdi.Selectable
    private UserGroupInfoProducer userGroupInfoProducer;

    @Inject
    @Kjar
    private DeploymentService deploymentService;
    
    @Produces
    public EntityManagerFactory getEntityManagerFactory() {
        return this.entityManagerFactory;
    }

    @Produces
    public org.kie.api.task.UserGroupCallback produceSelectedUserGroupCalback() {
        return userGroupInfoProducer.produceCallback();
    }

    @Produces
    public UserInfo produceUserInfo() {
        return userGroupInfoProducer.produceUserInfo();
    }

    @Produces
    @Named("Logs")
    public TaskLifeCycleEventListener produceTaskAuditListener() {
        return new JPATaskLifeCycleEventListener(true);
    }

    @Produces
    public DeploymentService getDeploymentService() {
        return this.deploymentService;
    }

    @Produces
    public IdentityProvider produceIdentityProvider() {
        return new IdentityProvider() {

			@Override
			public String getName() {
				return null;
			}

			@Override
			public List<String> getRoles() {
				return null;
			}

			@Override
			public boolean hasRole(String arg0) {
				return false;
			}
        };
    }
    
    @Produces
    @Singleton
    @PerRequest
    @PerProcessInstance
    public RuntimeEnvironment produceEnvironment(EntityManagerFactory entityManagerFactory) {
    	org.kie.api.runtime.manager.RuntimeEnvironmentBuilder runtimeEnvironmentBuilder = RuntimeEnvironmentBuilder.Factory.get()
                .newDefaultBuilder()
                .entityManagerFactory(entityManagerFactory)
                .userGroupCallback(produceSelectedUserGroupCalback())
                //.registerableItemsFactory(InjectableRegisterableItemsFactory.getFactory(beanManager, null))
                ;
    	return runtimeEnvironmentBuilder.get();
    }
    

}
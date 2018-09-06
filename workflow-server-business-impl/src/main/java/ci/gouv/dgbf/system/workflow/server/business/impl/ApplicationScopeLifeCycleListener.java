package ci.gouv.dgbf.system.workflow.server.business.impl;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;
import org.cyk.utility.assertion.AssertionsProviderClassMap;

import ci.gouv.dgbf.system.workflow.server.business.api.WorkflowAssertionsProvider;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __initialize__(Object object) {
		__inject__(AssertionsProviderClassMap.class).set(Workflow.class, WorkflowAssertionsProvider.class);
	}
	
	@Override
	protected void __destroy__(Object object) {}
	
}
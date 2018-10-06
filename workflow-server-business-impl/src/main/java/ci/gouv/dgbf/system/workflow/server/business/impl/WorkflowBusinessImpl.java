package ci.gouv.dgbf.system.workflow.server.business.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Singleton;

import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

import ci.gouv.dgbf.system.workflow.server.business.api.WorkflowBusiness;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.bpmn.Bpmn;
import ci.gouv.dgbf.system.workflow.server.persistence.impl.JbpmHelper;

@Singleton
public class WorkflowBusinessImpl extends AbstractBusinessEntityImpl<Workflow,WorkflowPersistence> implements WorkflowBusiness,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	public WorkflowBusiness synchroniseWithJbpmMavenRepository() {
		Collection<String> strings = __inject__(JbpmHelper.class).getProcessesFromMavenRepository();
		if(__injectCollectionHelper__().isNotEmpty(strings)) {
			Collection<Workflow> workflows = new ArrayList<>();
			for(String index : strings) {
				Bpmn bpmn = Bpmn.__executeWithContent__(index);
				Workflow workflow = getPersistence().readByCode(bpmn.getProcess().getId());
				if(workflow == null)
					workflow = new Workflow();
				workflow.setModel(index);
				workflows.add(workflow);
			}
			saveMany(workflows);
			__inject__(JbpmHelper.class).buildRuntimeEnvironment();
		}
		return this;
	}

	@Override
	protected Class<Workflow> __getEntityClass__() {
		return Workflow.class;
	}
	
}

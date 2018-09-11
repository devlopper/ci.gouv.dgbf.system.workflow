package ci.gouv.dgbf.system.workflow.server.business.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Singleton;
import javax.transaction.Transactional;

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
	public Workflow findByCode(String code) {
		return getPersistence().readByCode(code);
	}

	@Override @Transactional //TODO use super method
	public WorkflowBusiness deleteByCode(String code) {
		getPersistence().deleteByCode(code);
		return this;
	}
	
	@Override
	public WorkflowBusiness synchroniseWithJbpmMavenRepository() {
		//__inject__(JbpmHelper.class).setProcessesMavenRepositoryFolder("E:\\Servers\\Process\\JBPM\\jbpm-installer-7.7.0.Final");
		Collection<String> strings = __inject__(JbpmHelper.class).getProcessesFromMavenRepository();
		if(__injectCollectionHelper__().isNotEmpty(strings)) {
			Collection<Workflow> workflows = new ArrayList<>();
			for(String index : strings) {
				Bpmn bpmn = Bpmn.__executeWithContent__(index);
				Workflow workflow = getPersistence().readByCode(bpmn.getProcess().getId());
				if(workflow == null) {
					workflow = new Workflow().setModel(index);
					workflows.add(workflow);
				}
			}
			createMany(workflows);
			__inject__(JbpmHelper.class).buildRuntimeEnvironment();
		}
		return this;
	}

	@Override
	protected Class<Workflow> __getEntityClass__() {
		return Workflow.class;
	}
	
}

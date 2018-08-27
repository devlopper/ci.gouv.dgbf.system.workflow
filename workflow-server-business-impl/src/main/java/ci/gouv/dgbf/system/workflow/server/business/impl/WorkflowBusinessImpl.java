package ci.gouv.dgbf.system.workflow.server.business.impl;

import java.io.Serializable;

import javax.inject.Singleton;
import javax.transaction.Transactional;

import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

import ci.gouv.dgbf.system.workflow.server.business.api.WorkflowBusiness;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;

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
	protected Class<Workflow> __getEntityClass__() {
		return Workflow.class;
	}
	
}

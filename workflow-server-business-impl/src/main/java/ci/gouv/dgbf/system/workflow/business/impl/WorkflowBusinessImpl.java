package ci.gouv.dgbf.system.workflow.business.impl;

import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.Transactional;

import ci.gouv.dgbf.system.workflow.business.api.WorkflowBusiness;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;

public class WorkflowBusinessImpl extends AbstractEntityBusinessImpl<Workflow> implements WorkflowBusiness,Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private WorkflowPersistence persistence;
	
	@Override
	protected Class<Workflow> __getEntityClass__() {
		return Workflow.class;
	}
	
	@Override
	protected WorkflowPersistence getPersistence() {
		return persistence;
	}

	@Override
	public Workflow findByCode(String code) {
		return getPersistence().readByCode(code);
	}

	@Override @Transactional
	public WorkflowBusiness deleteByCode(String code) {
		getPersistence().deleteByCode(code);
		return this;
	}

}

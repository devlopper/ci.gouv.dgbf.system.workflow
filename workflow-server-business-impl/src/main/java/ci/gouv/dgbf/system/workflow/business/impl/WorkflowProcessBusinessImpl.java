package ci.gouv.dgbf.system.workflow.business.impl;

import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.Transactional;

import ci.gouv.dgbf.system.workflow.business.api.WorkflowProcessBusiness;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;

public class WorkflowProcessBusinessImpl extends AbstractEntityBusinessImpl<WorkflowProcess> implements WorkflowProcessBusiness,Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private WorkflowProcessPersistence persistence;
	
	@Override
	public WorkflowProcess findByWorkflowCodeByCode(String workflowCode, String code) {
		return persistence.readByWorkflowCodeByCode(workflowCode, code);
	}
	
	@Override @Transactional
	public WorkflowProcessBusiness deleteByWorkflowCodeByCode(String workflowCode, String code) {
		persistence.deleteByWorkflowCodeByCode(workflowCode,code);
		return this;
	}
	
	@Override
	protected Class<WorkflowProcess> __getEntityClass__() {
		return WorkflowProcess.class;
	}
	
	@Override
	protected WorkflowProcessPersistence getPersistence() {
		return persistence;
	}

}

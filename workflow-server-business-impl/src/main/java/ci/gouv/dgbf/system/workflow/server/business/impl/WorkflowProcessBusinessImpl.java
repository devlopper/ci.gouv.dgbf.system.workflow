package ci.gouv.dgbf.system.workflow.server.business.impl;

import java.io.Serializable;

import javax.inject.Singleton;
import javax.transaction.Transactional;

import org.cyk.utility.server.business.AbstractBusinessEntityImpl;
import org.cyk.utility.server.business.BusinessServiceProvider;

import ci.gouv.dgbf.system.workflow.server.business.api.WorkflowProcessBusiness;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;

@Singleton
public class WorkflowProcessBusinessImpl extends AbstractBusinessEntityImpl<WorkflowProcess,WorkflowProcessPersistence> implements WorkflowProcessBusiness,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override //TODO this must be done automatically. the specific persistence must be called from the business
	public BusinessServiceProvider<WorkflowProcess> create(WorkflowProcess workflowProcess) {
		getPersistence().create(workflowProcess);
		return this;
	}
	
	@Override
	public WorkflowProcess findByWorkflowCodeByCode(String workflowCode, String code) {
		return getPersistence().readByWorkflowCodeByCode(workflowCode, code);
	}
	
	@Override @Transactional
	public WorkflowProcessBusiness deleteByWorkflowCodeByCode(String workflowCode, String code) {
		getPersistence().deleteByWorkflowCodeByCode(workflowCode,code);
		return this;
	}
	
	@Override
	protected Class<WorkflowProcess> __getEntityClass__() {
		return WorkflowProcess.class;
	}

}

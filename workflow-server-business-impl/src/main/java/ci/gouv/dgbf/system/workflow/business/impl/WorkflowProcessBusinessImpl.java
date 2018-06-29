package ci.gouv.dgbf.system.workflow.business.impl;

import java.io.Serializable;

import javax.inject.Inject;

import ci.gouv.dgbf.system.workflow.business.api.WorkflowProcessBusiness;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;

public class WorkflowProcessBusinessImpl extends AbstractEntityBusinessImpl<WorkflowProcess> implements WorkflowProcessBusiness,Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private WorkflowProcessPersistence persistence;
	
	@Override
	protected Class<WorkflowProcess> __getEntityClass__() {
		return WorkflowProcess.class;
	}
	
	@Override
	protected WorkflowProcessPersistence getPersistence() {
		return persistence;
	}

}

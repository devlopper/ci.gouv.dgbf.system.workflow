package ci.gouv.dgbf.system.workflow.business.impl;

import java.io.Serializable;

import javax.inject.Inject;

import ci.gouv.dgbf.system.workflow.business.api.WorkflowProcessTaskLogBusiness;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessTaskLogPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTaskLog;

public class WorkflowProcessTaskLogBusinessImpl extends AbstractEntityBusinessImpl<WorkflowProcessTaskLog> implements WorkflowProcessTaskLogBusiness,Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private WorkflowProcessTaskLogPersistence persistence;
	
	@Override
	protected WorkflowProcessTaskLogPersistence getPersistence() {
		return persistence;
	}

}

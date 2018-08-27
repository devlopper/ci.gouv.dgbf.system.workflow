package ci.gouv.dgbf.system.workflow.server.business.impl;

import java.io.Serializable;

import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

import ci.gouv.dgbf.system.workflow.server.business.api.WorkflowProcessTaskLogBusiness;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessTaskLogPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTaskLog;

public class WorkflowProcessTaskLogBusinessImpl extends AbstractBusinessEntityImpl<WorkflowProcessTaskLog,WorkflowProcessTaskLogPersistence> implements WorkflowProcessTaskLogBusiness,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Class<WorkflowProcessTaskLog> __getEntityClass__() {
		return WorkflowProcessTaskLog.class;
	}
	
}

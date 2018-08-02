package ci.gouv.dgbf.system.workflow.server.persistence.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;
import org.jbpm.services.api.model.ProcessInstanceDesc;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.audit.ProcessInstanceLog;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.KieInternalServices;
import org.kie.internal.process.CorrelationAwareProcessRuntime;
import org.kie.internal.runtime.manager.context.ProcessInstanceIdContext;

import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessLogPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessLog;

public class WorkflowProcessLogPersistenceImpl extends AbstractEntityPersistenceImpl<WorkflowProcessLog> implements WorkflowProcessLogPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private WorkflowPersistence workflowPersistence;
	@Inject private WorkflowProcessPersistence workflowProcessPersistence;
	
	@Override
	public WorkflowProcessLog readByWorkflowByProcessCode(Workflow workflow, String processCode) {
		return readByWorkflowCodeByProcessCode(workflow.getCode(), processCode);
	}

	@Override
	public Collection<WorkflowProcessLog> readByWorkflow(Workflow workflow) {
		return readByWorkflowCode(workflow.getCode());
	}
	
	@Override
	public WorkflowProcessLog readByWorkflowCodeByProcessCode(String workflowCode, String code) {
		String correlationKey = KieInternalServices.Factory.get().newCorrelationKeyFactory()
				.newCorrelationKey(Arrays.asList(workflowCode,code)).toExternalForm();
		try {
			return entityManager.createNamedQuery("WorkflowProcessLog.readByCorrelationKey", WorkflowProcessLog.class).setParameter("correlationKey", correlationKey)
					.getSingleResult();
		} catch (NoResultException exception) {
			return null;
		}
	}

	@Override
	public Long countByWorkflow(Workflow workflow) {
		return countByWorkflowCode(workflow.getCode());
	}

	@Override
	public Collection<WorkflowProcessLog> readByWorkflowCode(String workflowCode) {
		return entityManager.createNamedQuery("WorkflowProcessLog.readByProcessId", WorkflowProcessLog.class).setParameter("processId", workflowCode).getResultList();
	}

	@Override
	public Long countByWorkflowCode(String workflowCode) {
		return entityManager.createNamedQuery("WorkflowProcessLog.countByProcessId", Long.class).setParameter("processId", workflowCode).getSingleResult();
	}
	
	
}

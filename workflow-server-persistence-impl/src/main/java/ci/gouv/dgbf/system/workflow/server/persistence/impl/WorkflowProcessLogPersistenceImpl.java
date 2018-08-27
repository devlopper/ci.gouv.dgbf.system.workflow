package ci.gouv.dgbf.system.workflow.server.persistence.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.kie.internal.KieInternalServices;

import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessLogPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessLog;

@Singleton
public class WorkflowProcessLogPersistenceImpl extends AbstractPersistenceEntityImpl<WorkflowProcessLog> implements WorkflowProcessLogPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private EntityManager entityManager;
	
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

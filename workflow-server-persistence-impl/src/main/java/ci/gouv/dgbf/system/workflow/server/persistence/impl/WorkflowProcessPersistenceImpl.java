package ci.gouv.dgbf.system.workflow.server.persistence.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import javax.persistence.NoResultException;

import org.kie.internal.KieInternalServices;
import org.kie.internal.process.CorrelationAwareProcessRuntime;

import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;

public class WorkflowProcessPersistenceImpl extends AbstractEntityPersistenceImpl<WorkflowProcess> implements WorkflowProcessPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public WorkflowProcessPersistence create(WorkflowProcess workflowProcess) {
		String identifier = workflowProcess.getWorkflow().getCode();// businessProcessModelNotationHelper.getIdentifier(workflowProcess.getWorkflow().getModel());
		((CorrelationAwareProcessRuntime)persistenceHelper.getKieSession()).startProcess(identifier,KieInternalServices.Factory.get().newCorrelationKeyFactory()
				.newCorrelationKey(Arrays.asList(workflowProcess.getWorkflow().getCode(),workflowProcess.getCode())),null);
		return this;
	}
	
	@Override
	public Collection<WorkflowProcess> readByWorkflowCode(String workflowCode) {
		return entityManager.createNamedQuery("WorkflowProcess.readByProcessId", WorkflowProcess.class).setParameter("processId", workflowCode).getResultList();
	}

	@Override
	public Long countByWorkflowCode(String workflowCode) {
		return entityManager.createNamedQuery("WorkflowProcess.countByProcessId", Long.class).setParameter("processId", workflowCode).getSingleResult();
	}
	
	@Override
	public Collection<WorkflowProcess> readByWorkflow(Workflow workflow) {
		return readByWorkflowCode(workflow.getCode());
	}

	@Override
	public Long countByWorkflow(Workflow workflow) {
		return countByWorkflowCode(workflow.getCode());
	}
	
	@Override
	public WorkflowProcess readByWorkflowCodeByCode(String workflowCode, String code) {
		String correlationKey = KieInternalServices.Factory.get().newCorrelationKeyFactory()
				.newCorrelationKey(Arrays.asList(workflowCode,code)).toExternalForm();
		try {
			return entityManager.createNamedQuery("WorkflowProcess.readByCorrelationKey", WorkflowProcess.class).setParameter("correlationKey", correlationKey)
					.getSingleResult();
		} catch (NoResultException exception) {
			return null;
		}
	}
	
	@Override
	public Long countAll() {
		return entityManager.createNamedQuery("WorkflowProcess.countAll", Long.class).getSingleResult();
	}
	
	@Override
	public WorkflowProcess readByWorkflowByCode(Workflow workflow, String code) {
		return readByWorkflowCodeByCode(workflow.getCode(), code);
	}

	@Override
	public WorkflowProcessPersistence deleteByWorkflowCodeByCode(String workflowCode, String code) {
		delete(readByWorkflowCodeByCode(workflowCode, code));
		return this;
	}
	
	/**/
	
}

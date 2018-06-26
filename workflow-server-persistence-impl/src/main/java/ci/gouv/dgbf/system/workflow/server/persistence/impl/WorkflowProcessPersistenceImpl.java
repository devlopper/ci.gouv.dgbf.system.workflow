package ci.gouv.dgbf.system.workflow.server.persistence.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.kie.internal.KieInternalServices;
import org.kie.internal.process.CorrelationAwareProcessRuntime;

import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;

public class WorkflowProcessPersistenceImpl extends AbstractEntityPersistenceImpl<WorkflowProcess> implements WorkflowProcessPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private WorkflowPersistence workflowPersistence;
	
	@Override
	public WorkflowProcessPersistence create(WorkflowProcess workflowProcess) {
		String identifier = businessProcessModelNotationHelper.getIdentifier(workflowProcess.getWorkflow().getModel());
		((CorrelationAwareProcessRuntime)persistenceHelper.getKieSession()).startProcess(identifier,KieInternalServices.Factory.get().newCorrelationKeyFactory()
				.newCorrelationKey(Arrays.asList(workflowProcess.getWorkflow().getCode(),workflowProcess.getCode())),null);
		return this;
	}
	
	@Override
	public WorkflowProcess readByWorkflowByCode(Workflow workflow, String code) {
		String correlationKey = KieInternalServices.Factory.get().newCorrelationKeyFactory()
				.newCorrelationKey(Arrays.asList(workflow.getCode(),code)).toExternalForm();
		try {
			return entityManager.createNamedQuery("WorkflowProcess.readByCorrelationKey", WorkflowProcess.class).setParameter("correlationKey", correlationKey)
					.getSingleResult();
		} catch (NoResultException exception) {
			return null;
		}
	}

	@Override
	public Collection<WorkflowProcess> readByWorkflow(Workflow workflow) {
		return entityManager.createNamedQuery("WorkflowProcess.readByProcessId", WorkflowProcess.class).setParameter("processId", workflow.getCode()).getResultList();
	}
	
	@Override
	public WorkflowProcess readByWorkflowCodeByCode(String workflowCode, String code) {
		return readByWorkflowByCode(workflowPersistence.readByCode(workflowCode), code);
	}

	@Override
	public Long countByWorkflow(Workflow workflow) {
		Collection<WorkflowProcess> workflowProcesses = readByWorkflow(workflow);
		return workflowProcesses == null ? 0 : new Long(workflowProcesses.size());
	}

	@Override
	public Collection<WorkflowProcess> readByWorkflowCode(String workflowCode) {
		return readByWorkflow(workflowPersistence.readByCode(workflowCode));
	}

	@Override
	public Long countByWorkflowCode(String workflowCode) {
		return countByWorkflow(workflowPersistence.readByCode(workflowCode));
	}
	
	/**/
	
}

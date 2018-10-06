package ci.gouv.dgbf.system.workflow.server.persistence.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.cyk.utility.server.persistence.PersistenceServiceProvider;
import org.kie.internal.KieInternalServices;
import org.kie.internal.process.CorrelationAwareProcessRuntime;

import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;

@Singleton
public class WorkflowProcessPersistenceImpl extends AbstractPersistenceEntityImpl<WorkflowProcess> implements WorkflowProcessPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private EntityManager entityManager;
			
	@Override
	public PersistenceServiceProvider<WorkflowProcess> create(WorkflowProcess workflowProcess, Properties properties) {
		String identifier = workflowProcess.getWorkflow().getCode();
		((CorrelationAwareProcessRuntime)__inject__(JbpmHelper.class).getRuntimeEngine().getKieSession()).startProcess(identifier,KieInternalServices.Factory.get().newCorrelationKeyFactory()
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
	public Collection<WorkflowProcess> read(Properties properties) {
		return entityManager.createNamedQuery("WorkflowProcess.readAll", WorkflowProcess.class).getResultList();
	}
		
	@Override
	public Long count(Properties properties) {
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

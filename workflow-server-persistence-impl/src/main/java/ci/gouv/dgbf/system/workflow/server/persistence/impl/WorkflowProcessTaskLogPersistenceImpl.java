package ci.gouv.dgbf.system.workflow.server.persistence.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessLogPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessTaskLogPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessLog;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTaskLog;

@Singleton
public class WorkflowProcessTaskLogPersistenceImpl extends AbstractPersistenceEntityImpl<WorkflowProcessTaskLog> implements WorkflowProcessTaskLogPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private EntityManager entityManager;
	
	@Override
	public Collection<WorkflowProcessTaskLog> readByWorkflowCode(String workflowCode) {
		return entityManager.createNamedQuery("WorkflowProcessTaskLog.readByProcessId", WorkflowProcessTaskLog.class).setParameter("processId", workflowCode).getResultList();	
	}
	
	@Override
	public Long countByWorkflowCode(String workflowCode) {
		return entityManager.createNamedQuery("WorkflowProcessTaskLog.countByProcessId", Long.class).setParameter("processId", workflowCode).getSingleResult();
	}
	
	@Override
	public Collection<WorkflowProcessTaskLog> readByWorkflowProcessIdentifier(Long workflowProcessIdentifier) {
		return entityManager.createNamedQuery("WorkflowProcessTaskLog.readByProcessInstanceId", WorkflowProcessTaskLog.class).setParameter("processInstanceId", workflowProcessIdentifier).getResultList();
	}
	
	@Override
	public Long countByWorkflowProcessIdentifier(Long workflowProcessIdentifier) {
		return entityManager.createNamedQuery("WorkflowProcessTaskLog.countByProcessInstanceId", Long.class).setParameter("processInstanceId", workflowProcessIdentifier).getSingleResult();
	}
	
	@Override
	public Collection<WorkflowProcessTaskLog> readByWorkflowProcessIdentifierByUserCode(Long workflowProcessIdentifier,String userCode) {
		return entityManager.createNamedQuery("WorkflowProcessTaskLog.readByProcessInstanceIdByActualOwner", WorkflowProcessTaskLog.class).setParameter("processInstanceId"
				, workflowProcessIdentifier).setParameter("actualOwner", userCode).getResultList();
	}
	
	@Override
	public Long countByWorkflowProcessIdentifierByUserCode(Long workflowProcessIdentifier, String userCode) {
		return entityManager.createNamedQuery("WorkflowProcessTaskLog.countByProcessInstanceIdByActualOwner", Long.class).setParameter("processInstanceId", workflowProcessIdentifier)
				.setParameter("actualOwner", userCode).getSingleResult();
	}
	
	@Override
	public Collection<WorkflowProcessTaskLog> readByWorkflowProcess(WorkflowProcess workflowProcess) {
		return workflowProcess == null ? null : readByWorkflowProcessIdentifier(workflowProcess.getIdentifier());
	}
	
	@Override
	public Long countByWorkflowProcess(WorkflowProcess workflowProcess) {
		return workflowProcess == null ? 0l :  countByWorkflowProcessIdentifier(workflowProcess.getIdentifier());
	}
	
	@Override
	public Collection<WorkflowProcessTaskLog> readByWorkflowCodeByProcessCode(String workflowCode,String processCode) {
		WorkflowProcessLog workflowProcessLog = __inject__(WorkflowProcessLogPersistence.class).readByWorkflowCodeByProcessCode(workflowCode, processCode);
		return workflowProcessLog == null ? null : readByWorkflowProcessIdentifier(workflowProcessLog.getWorkflowProcess().getIdentifier());	
	}
	
	@Override
	public Long countByWorkflowCodeByProcessCode(String workflowCode, String processCode) {
		WorkflowProcessLog workflowProcessLog = __inject__(WorkflowProcessLogPersistence.class).readByWorkflowCodeByProcessCode(workflowCode, processCode);
		return workflowProcessLog == null ? 0l : countByWorkflowProcessIdentifier(workflowProcessLog.getWorkflowProcess().getIdentifier());
	}
	
	@Override
	public Collection<WorkflowProcessTaskLog> readByWorkflowProcessByUserCode(WorkflowProcess workflowProcess,String userCode) {
		return entityManager.createNamedQuery("WorkflowProcessTaskLog.readByProcessInstanceIdByActualOwner", WorkflowProcessTaskLog.class).setParameter("processInstanceId"
				, workflowProcess.getIdentifier()).setParameter("actualOwner", userCode)
				.getResultList();
	}
	
	@Override
	public Long countByWorkflowProcessByUserCode(WorkflowProcess workflowProcess, String userCode) {
		return workflowProcess == null ? 0l :  countByWorkflowProcessIdentifierByUserCode(workflowProcess.getIdentifier(),userCode);
	}
	
	@Override
	public Collection<WorkflowProcessTaskLog> readByWorkflowCodeByProcessCodeByUserCode(String workflowCode,String processCode, String userCode) {
		WorkflowProcessLog workflowProcessLog = __inject__(WorkflowProcessLogPersistence.class).readByWorkflowCodeByProcessCode(workflowCode, processCode);
		return workflowProcessLog == null ? null : readByWorkflowProcessIdentifierByUserCode(workflowProcessLog.getWorkflowProcess().getIdentifier(), userCode);
	}
	
	@Override
	public Long countByWorkflowCodeByProcessCodeByUserCode(String workflowCode, String processCode,String userCode) {
		WorkflowProcessLog workflowProcessLog = __inject__(WorkflowProcessLogPersistence.class).readByWorkflowCodeByProcessCode(workflowCode, processCode);
		return workflowProcessLog == null ? 0l : countByWorkflowProcessIdentifierByUserCode(workflowProcessLog.getWorkflowProcess().getIdentifier(), userCode);
	}

	@Override
	public Collection<WorkflowProcessTaskLog> read(Properties properties) {
		return entityManager.createNamedQuery("WorkflowProcessTaskLog.readAll", WorkflowProcessTaskLog.class).getResultList();	
	}
	
	@Override
	public Long count(Properties properties) {
		return entityManager.createNamedQuery("WorkflowProcessTaskLog.countAll", Long.class).getSingleResult();	
	}

	@Override
	public Collection<WorkflowProcessTaskLog> readByWorkflowProcessIdentifierByUserCodeByStatusCodes(Long workflowProcessIdentifier, String userCode, Collection<String> statusCodes) {
		return entityManager.createNamedQuery("WorkflowProcessTaskLog.readByProcessInstanceIdByActualOwnerByStatus", WorkflowProcessTaskLog.class).setParameter("processInstanceId"
				, workflowProcessIdentifier).setParameter("actualOwner", userCode).setParameter("status", statusCodes).getResultList();
	}

	@Override
	public Collection<WorkflowProcessTaskLog> readByWorkflowProcessIdentifierByUserCodeByStatusCodes(Long workflowProcessIdentifier, String userCode, String... statusCodes) {
		return readByWorkflowProcessIdentifierByUserCodeByStatusCodes(workflowProcessIdentifier, userCode, __injectCollectionHelper__().instanciate(statusCodes));
	}

	@Override
	public Long countByWorkflowProcessIdentifierByUserCodeByStatusCodes(Long workflowProcessIdentifier, String userCode,Collection<String> statusCodes) {
		return entityManager.createNamedQuery("WorkflowProcessTaskLog.countByProcessInstanceIdByActualOwnerByStatus", Long.class).setParameter("processInstanceId", workflowProcessIdentifier)
				.setParameter("actualOwner", userCode).setParameter("status", statusCodes).getSingleResult();
	}

	@Override
	public Long countByWorkflowProcessIdentifierByUserCodeByStatusCodes(Long workflowProcessIdentifier, String userCode,String... statusCodes) {
		return countByWorkflowProcessIdentifierByUserCodeByStatusCodes(workflowProcessIdentifier, userCode, __injectCollectionHelper__().instanciate(statusCodes));
	}

	@Override
	public Collection<WorkflowProcessTaskLog> readByWorkflowCodeByProcessCodeByUserCodeByStatusCodes(String workflowCode, String processCode, String userCode, Collection<String> statusCodes) {
		WorkflowProcessLog workflowProcessLog = __inject__(WorkflowProcessLogPersistence.class).readByWorkflowCodeByProcessCode(workflowCode, processCode);
		return workflowProcessLog == null ? null : readByWorkflowProcessIdentifierByUserCodeByStatusCodes(workflowProcessLog.getWorkflowProcess().getIdentifier(), userCode, statusCodes);
	}

	@Override
	public Collection<WorkflowProcessTaskLog> readByWorkflowCodeByProcessCodeByUserCodeByStatusCodes(String workflowCode, String processCode, String userCode, String... statusCodes) {
		return readByWorkflowCodeByProcessCodeByUserCodeByStatusCodes(workflowCode, processCode, userCode, __injectCollectionHelper__().instanciate(statusCodes));
	}

	@Override
	public Long countByWorkflowCodeByProcessCodeByUserCodeByStatusCodes(String workflowCode, String processCode,String userCode, Collection<String> statusCodes) {
		WorkflowProcessLog workflowProcessLog = __inject__(WorkflowProcessLogPersistence.class).readByWorkflowCodeByProcessCode(workflowCode, processCode);
		return workflowProcessLog == null ? 0l : countByWorkflowProcessIdentifierByUserCodeByStatusCodes(workflowProcessLog.getWorkflowProcess().getIdentifier(), userCode, statusCodes);
	}

	@Override
	public Long countByWorkflowCodeByProcessCodeByUserCodeByStatusCodes(String workflowCode, String processCode,String userCode, String... statusCodes) {
		return countByWorkflowCodeByProcessCodeByUserCodeByStatusCodes(workflowCode, processCode, userCode, __injectCollectionHelper__().instanciate(statusCodes));
	}

	
}

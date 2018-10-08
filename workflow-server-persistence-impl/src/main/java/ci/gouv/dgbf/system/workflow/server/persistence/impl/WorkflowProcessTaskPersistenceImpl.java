package ci.gouv.dgbf.system.workflow.server.persistence.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.enumeration.EnumGetter;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.kie.api.task.model.Status;

import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessTaskPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTask;

@Singleton
public class WorkflowProcessTaskPersistenceImpl extends AbstractPersistenceEntityImpl<WorkflowProcessTask> implements WorkflowProcessTaskPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private WorkflowProcessPersistence workflowProcessPersistence;
	@Inject private EntityManager entityManager;
	
	@Override
	public Collection<WorkflowProcessTask> readByWorkflowCode(String workflowCode) {
		return entityManager.createNamedQuery("WorkflowProcessTask.readByProcessId", WorkflowProcessTask.class).setParameter("processId", workflowCode).getResultList();
	}
	
	@Override
	public Long countByWorkflowCode(String workflowCode) {
		return entityManager.createNamedQuery("WorkflowProcessTask.countByProcessId", Long.class).setParameter("processId", workflowCode).getSingleResult();
	}
	
	@Override
	public Collection<WorkflowProcessTask> readByWorkflowProcess(WorkflowProcess workflowProcess) {
		return entityManager.createNamedQuery("WorkflowProcessTask.readByProcessInstanceId", WorkflowProcessTask.class).setParameter("processInstanceId"
				, workflowProcess.getIdentifier()).getResultList();
	}
	
	@Override
	public Long countByWorkflowProcess(WorkflowProcess workflowProcess) {
		return entityManager.createNamedQuery("WorkflowProcessTask.countByProcessInstanceId", Long.class).setParameter("processInstanceId"
				, workflowProcess.getIdentifier()).getSingleResult();
	}
	
	@Override
	public Collection<WorkflowProcessTask> readByWorkflowCodeByProcessCode(String workflowCode,String processCode) {
		return readByWorkflowProcess(workflowProcessPersistence.readByWorkflowCodeByCode(workflowCode, processCode));
	}
	
	@Override
	public Long countByWorkflowCodeByProcessCode(String workflowCode, String processCode) {
		return countByWorkflowProcess(workflowProcessPersistence.readByWorkflowCodeByCode(workflowCode, processCode));
	}
	
	@Override
	public Collection<WorkflowProcessTask> readByWorkflowCodeByUserCode(String workflowCode, String userCode) {
		return entityManager.createNamedQuery("WorkflowProcessTask.readByProcessIdByActualOwnerId", WorkflowProcessTask.class).setParameter("processId"
				, workflowCode).setParameter("actualOwnerId", userCode).getResultList();
	}
	
	@Override
	public Long countByWorkflowCodeByUserCode(String workflowCode,String userCode) {
		return entityManager.createNamedQuery("WorkflowProcessTask.countByProcessIdByActualOwnerId", Long.class).setParameter("processId"
				, workflowCode).setParameter("actualOwnerId", userCode).getSingleResult();
	}
	
	@Override
	public Collection<WorkflowProcessTask> readByWorkflowProcessByUserIdentifier(WorkflowProcess workflowProcess,String userCode) {
		return entityManager.createNamedQuery("WorkflowProcessTask.readByProcessInstanceIdByActualOwnerId", WorkflowProcessTask.class).setParameter("processInstanceId"
				, workflowProcess.getIdentifier()).setParameter("actualOwnerId", userCode).getResultList();
	}
	
	@Override
	public Long countByWorkflowProcessByUserIdentifier(WorkflowProcess workflowProcess, String userCode) {
		return workflowProcess == null ? 0l : entityManager.createNamedQuery("WorkflowProcessTask.countByProcessInstanceIdByActualOwnerId", Long.class).setParameter("processInstanceId"
				, workflowProcess.getIdentifier()).setParameter("actualOwnerId", userCode).getSingleResult();
	}
	
	@Override
	public Collection<WorkflowProcessTask> readByWorkflowCodeByProcessCodeByUserCode(String workflowCode,String processCode, String userCode) {
		return readByWorkflowProcessByUserIdentifier(workflowProcessPersistence.readByWorkflowCodeByCode(workflowCode, processCode), userCode);
	}
	
	@Override
	public Long countByWorkflowCodeByProcessCodeByUserCode(String workflowCode, String processCode,String userCode) {
		return countByWorkflowProcessByUserIdentifier(workflowProcessPersistence.readByWorkflowCodeByCode(workflowCode, processCode), userCode);
	}

	@Override
	public Collection<WorkflowProcessTask> readByWorkflowCodeByProcessCodeByUserCodeByStatusCode(String workflowCode,String processCode, String userCode,String statusCode) {
		WorkflowProcess workflowProcess = __inject__(WorkflowProcessPersistence.class).readByWorkflowCodeByCode(workflowCode, processCode);
		Status status = __inject__(EnumGetter.class).setClazz(Status.class).setIsNameCaseSensitive(Boolean.FALSE).setName(statusCode).execute().getOutputAs(Status.class);
		return entityManager.createNamedQuery("WorkflowProcessTask.readByProcessInstanceIdByActualOwnerIdByStatus", WorkflowProcessTask.class).setParameter("processInstanceId"
				, workflowProcess.getIdentifier()).setParameter("actualOwnerId", userCode).setParameter("status", status).getResultList();
	}
	
	@Override
	public Long countByWorkflowCodeByProcessCodeByUserCodeByStatusCode(String workflowCode, String processCode,String userCode,String statusCode) {
		WorkflowProcess workflowProcess = __inject__(WorkflowProcessPersistence.class).readByWorkflowCodeByCode(workflowCode, processCode);
		Status status = __inject__(EnumGetter.class).setClazz(Status.class).setIsNameCaseSensitive(Boolean.FALSE).setName(statusCode).execute().getOutputAs(Status.class);
		return entityManager.createNamedQuery("WorkflowProcessTask.countByProcessInstanceIdByActualOwnerIdByStatus", Long.class).setParameter("processInstanceId"
				, workflowProcess.getIdentifier()).setParameter("actualOwnerId", userCode).setParameter("status", status).getSingleResult();
	}
	
	@Override
	public Collection<WorkflowProcessTask> read(Properties properties) {
		return entityManager.createNamedQuery("WorkflowProcessTask.readAll", WorkflowProcessTask.class).getResultList();
	}
	
	@Override
	public Long count(Properties properties) {
		return entityManager.createNamedQuery("WorkflowProcessTask.countAll", Long.class).getSingleResult();
	}

	@Override
	public Collection<WorkflowProcessTask> readByUserCodeByStatusCodes(String userCode,Collection<String> statusCodes) {
		@SuppressWarnings("unchecked")
		Collection<Status> status = __injectEnumCollectionGetter__().setGetter(__injectEnumGetter__().setClazz(Status.class)).setNames(statusCodes).execute().getOutput();
		return entityManager.createNamedQuery("WorkflowProcessTask.readByActualOwnerIdByStatus", WorkflowProcessTask.class)
				.setParameter("actualOwnerId", userCode).setParameter("status", status).getResultList();
	}

	@Override
	public Long countByUserCodeByStatusCodes(String userCode, Collection<String> statusCodes) {
		return entityManager.createNamedQuery("WorkflowProcessTask.countByActualOwnerIdByStatus", Long.class)
				.setParameter("actualOwnerId", userCode).setParameter("status", statusCodes).getSingleResult();
	}

	@Override
	public Collection<WorkflowProcessTask> readByUserCodeByStatusCodes(String userCode, String... statusCodes) {
		return readByUserCodeByStatusCodes(userCode, __injectCollectionHelper__().instanciate(statusCodes));
	}

	@Override
	public Long countByUserCodeByStatusCodes(String userCode, String... statusCodes) {
		return countByUserCodeByStatusCodes(userCode, __injectCollectionHelper__().instanciate(statusCodes));
	}
	
}

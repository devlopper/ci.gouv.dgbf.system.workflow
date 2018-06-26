package ci.gouv.dgbf.system.workflow.server.persistence.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.jbpm.services.api.model.ProcessInstanceDesc;
import org.kie.api.runtime.manager.audit.ProcessInstanceLog;
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
		String identifier = businessProcessModelNotationHelper.getIdentifier(workflowProcess.getWorkflow().getModelAsBpmn());
		((CorrelationAwareProcessRuntime)persistenceHelper.getKieSession()).startProcess(identifier,KieInternalServices.Factory.get().newCorrelationKeyFactory()
				.newCorrelationKey(Arrays.asList(workflowProcess.getWorkflow().getCode(),workflowProcess.getCode())),null);
		return this;
	}
	
	@Override
	public WorkflowProcess readByWorkflowByCode(Workflow workflow, String code) {
		/*RuntimeManager runtimeManager = persistenceHelper.getRuntimeManager();
		RuntimeEngine runtimeEngine = runtimeManager.getRuntimeEngine(ProcessInstanceIdContext.get());
		KieSession session = runtimeEngine.getKieSession();
		ProcessInstance processInstance = ((CorrelationAwareProcessRuntime)session).getProcessInstance(KieInternalServices.Factory.get().newCorrelationKeyFactory()
				.newCorrelationKey(Arrays.asList(workflow.getCode(),code)));
		if(processInstance == null)
			return null;
		WorkflowProcess workflowProcess = instanciateByJbpmProcessInstanceIdentifier(processInstance.getId(), workflow);
		return workflowProcess;
		*/
		String correlationKey = KieInternalServices.Factory.get().newCorrelationKeyFactory()
				.newCorrelationKey(Arrays.asList(workflow.getCode(),code)).toExternalForm();
		try {
			return entityManager.createNamedQuery("WorkflowProcess.readByCorrelationKey", WorkflowProcess.class).setParameter("correlationKey", correlationKey)
					.getSingleResult().setWorkflow(workflow);
		} catch (NoResultException exception) {
			return null;
		}
	}

	@Override
	public Collection<WorkflowProcess> readByWorkflow(Workflow workflow) {
		String identifier = businessProcessModelNotationHelper.getIdentifier(workflow.getModelAsBpmn());
		Collection<WorkflowProcess> workflowProcesses = null;
		@SuppressWarnings("unchecked")
		Collection<ProcessInstanceLog> processInstances = (Collection<ProcessInstanceLog>) persistenceHelper.getRuntimeEngine().getAuditService().findProcessInstances(identifier);
		if(processInstances != null && !processInstances.isEmpty()){
			workflowProcesses = new ArrayList<>();
			for(ProcessInstanceLog index : processInstances){
				WorkflowProcess workflowProcess = instanciateByJbpmProcessInstanceIdentifier(index.getProcessInstanceId(), workflow);
				workflowProcesses.add(workflowProcess);
			}				
		}
		return workflowProcesses;
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
	
	@Override
	public WorkflowProcess instanciateByJbpmProcessInstanceIdentifier(Long jbpmProcessInstanceIdentifier,Workflow workflow) {
		WorkflowProcess workflowProcess = null;
		ProcessInstanceDesc processInstanceDesc = runtimeDataService.getProcessInstanceById(jbpmProcessInstanceIdentifier);
		if(processInstanceDesc!=null){
			String[] keys = processInstanceDesc.getCorrelationKey().split(":");
			if(workflow == null)
				workflow = workflowPersistence.readByCode(keys[0]);
			workflowProcess = new WorkflowProcess().setCode(keys[1]).setWorkflow(workflow);	
		}
		return workflowProcess;
	}
	
	@Override
	public WorkflowProcess instanciateByJbpmProcessInstanceIdentifier(Long jbpmProcessInstanceIdentifier) {
		return instanciateByJbpmProcessInstanceIdentifier(jbpmProcessInstanceIdentifier, null);
	}
	
	/**/
	
	
	
}

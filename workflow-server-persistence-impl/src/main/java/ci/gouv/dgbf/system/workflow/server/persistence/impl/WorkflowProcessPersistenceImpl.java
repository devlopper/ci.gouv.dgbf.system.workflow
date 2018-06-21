package ci.gouv.dgbf.system.workflow.server.persistence.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.audit.ProcessInstanceLog;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.KieInternalServices;
import org.kie.internal.process.CorrelationAwareProcessRuntime;
import org.kie.internal.runtime.manager.context.ProcessInstanceIdContext;

import ci.gouv.dgbf.system.workflow.server.persistence.api.PersistenceHelper;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;

public class WorkflowProcessPersistenceImpl extends AbstractEntityPersistenceImpl<WorkflowProcess> implements WorkflowProcessPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private PersistenceHelper persistenceHelper;
	@Inject private WorkflowPersistence workflowPersistence;
	
	@Override
	public WorkflowProcessPersistence create(WorkflowProcess workflowProcess) {
		String identifier = persistenceHelper.getProcessDefinitionIdentifier(workflowProcess.getWorkflow().getBytes());
		((CorrelationAwareProcessRuntime)persistenceHelper.getKieSession()).startProcess(identifier,KieInternalServices.Factory.get().newCorrelationKeyFactory()
				.newCorrelationKey(Arrays.asList(workflowProcess.getWorkflow().getCode(),workflowProcess.getCode())),null).getId();
		return this;
	}
	
	@Override
	public WorkflowProcess readByWorkflowByCode(Workflow workflow, String code) {
		
		
		RuntimeManager runtimeManager = persistenceHelper.getRuntimeManager();
		RuntimeEngine runtimeEngine = runtimeManager.getRuntimeEngine(ProcessInstanceIdContext.get());
		KieSession session = runtimeEngine.getKieSession();
		ProcessInstance processInstance = ((CorrelationAwareProcessRuntime)session).getProcessInstance(KieInternalServices.Factory.get().newCorrelationKeyFactory()
				.newCorrelationKey(code));
		if(processInstance == null)
			return null;
		WorkflowProcess workflowProcess = new WorkflowProcess().setProcessInstance(processInstance);
		return workflowProcess;
	}

	
	@Override
	public Collection<WorkflowProcess> readByWorkflow(Workflow workflow) {
		String identifier = persistenceHelper.getProcessDefinitionIdentifier(workflow.getBytes());
		Collection<WorkflowProcess> workflowProcesses = null;
		@SuppressWarnings("unchecked")
		Collection<ProcessInstanceLog> processInstances = (Collection<ProcessInstanceLog>) persistenceHelper.getRuntimeEngine().getAuditService().findProcessInstances(identifier);
		if(processInstances != null && !processInstances.isEmpty()){
			workflowProcesses = new ArrayList<>();
			for(ProcessInstanceLog index : processInstances){
				WorkflowProcess workflowProcess = new WorkflowProcess();
				workflowProcess.setProcessInstanceLog(index);
				workflowProcesses.add(workflowProcess);
			}
				
		}
		return workflowProcesses;
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
	
	
}

package ci.gouv.dgbf.system.workflow.server.persistence.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;

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
		WorkflowProcessLog workflowProcessLog = null;
		WorkflowProcess workflowProcess = workflowProcessPersistence.readByWorkflowByCode(workflow, processCode);
		RuntimeManager runtimeManager = persistenceHelper.getRuntimeManager();
		RuntimeEngine runtimeEngine = runtimeManager.getRuntimeEngine(ProcessInstanceIdContext.get());
		KieSession session = runtimeEngine.getKieSession();
		ProcessInstance processInstance = ((CorrelationAwareProcessRuntime)session).getProcessInstance(KieInternalServices.Factory.get().newCorrelationKeyFactory()
				.newCorrelationKey(Arrays.asList(workflow.getCode(),processCode)));
		if(processInstance != null){
			workflowProcessLog = new WorkflowProcessLog().setWorkflowProcess(workflowProcess)
					.setJbpmProcessInstanceLog(persistenceHelper.getRuntimeEngine().getAuditService().findProcessInstance(processInstance.getId()));
		}
		return workflowProcessLog;
	}

	@Override
	public Collection<WorkflowProcessLog> readByWorkflow(Workflow workflow) {
		String identifier = businessProcessModelNotationHelper.getIdentifier(workflow.getModelAsBpmn());
		//RuntimeManager runtimeManager = persistenceHelper.getRuntimeManager();
		//RuntimeEngine runtimeEngine = runtimeManager.getRuntimeEngine(ProcessInstanceIdContext.get());
		//KieSession session = runtimeEngine.getKieSession();
		//Collection<WorkflowProcess> workflowProcesses = session.get;
		Collection<WorkflowProcessLog> workflowProcessLogs = null;
		@SuppressWarnings("unchecked")
		Collection<ProcessInstanceLog> processInstanceLogs = (Collection<ProcessInstanceLog>) persistenceHelper.getRuntimeEngine().getAuditService().findProcessInstances(identifier);
		if(processInstanceLogs != null && !processInstanceLogs.isEmpty()){
			workflowProcessLogs = new ArrayList<>();
			for(ProcessInstanceLog index : processInstanceLogs){
				ProcessInstanceDesc processInstanceDesc = runtimeDataService.getProcessInstanceById(index.getProcessInstanceId());
				WorkflowProcessLog workflowProcessLog = new WorkflowProcessLog().setWorkflowProcess(new WorkflowProcess().setCode(StringUtils.substringAfter(processInstanceDesc.getCorrelationKey(),":")).setWorkflow(workflow)
						).setJbpmProcessInstanceLog(index);
				workflowProcessLogs.add(workflowProcessLog);
			}
		}
		return workflowProcessLogs;
	}
	
	@Override
	public WorkflowProcessLog readByWorkflowCodeByProcessCode(String workflowCode, String code) {
		return readByWorkflowByProcessCode(workflowPersistence.readByCode(workflowCode), code);
	}

	@Override
	public Long countByWorkflow(Workflow workflow) {
		Collection<WorkflowProcessLog> workflowProcessLogs = readByWorkflow(workflow);
		return workflowProcessLogs == null ? 0 : new Long(workflowProcessLogs.size());
	}

	@Override
	public Collection<WorkflowProcessLog> readByWorkflowCode(String workflowCode) {
		return readByWorkflow(workflowPersistence.readByCode(workflowCode));
	}

	@Override
	public Long countByWorkflowCode(String workflowCode) {
		return countByWorkflow(workflowPersistence.readByCode(workflowCode));
	}
	
	
}

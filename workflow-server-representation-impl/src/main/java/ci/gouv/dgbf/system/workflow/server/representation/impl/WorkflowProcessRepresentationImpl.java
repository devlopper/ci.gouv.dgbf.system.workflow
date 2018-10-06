package ci.gouv.dgbf.system.workflow.server.representation.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;
import org.cyk.utility.value.ValueUsageType;

import ci.gouv.dgbf.system.workflow.server.business.api.WorkflowBusiness;
import ci.gouv.dgbf.system.workflow.server.business.api.WorkflowProcessBusiness;
import ci.gouv.dgbf.system.workflow.server.business.api.WorkflowProcessTaskBusiness;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessNodeLogPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTask;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.bpmn.Bpmn;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.bpmn.UserTask;
import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowProcessRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.entities.Task;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowProcessDto;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowProcessDtoCollection;

public class WorkflowProcessRepresentationImpl extends AbstractRepresentationEntityImpl<WorkflowProcess, WorkflowProcessBusiness, WorkflowProcessDto,WorkflowProcessDtoCollection> implements WorkflowProcessRepresentation,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Response getOne(String identifier, String type) {
		Response response = super.getOne(identifier, type);
		
		WorkflowProcessDto workflowProcessDto = (WorkflowProcessDto)response.getEntity();
		Workflow workflow = __inject__(WorkflowBusiness.class).findOne(__injectNumberHelper__().getLong(workflowProcessDto.getWorkflow()));
		if(workflow!=null && workflow.getModel()!=null && !workflow.getModel().isEmpty()) {
			Bpmn bpmn = Bpmn.__executeWithContent__(workflow.getModel());
			if(bpmn!=null && bpmn.getProcess()!=null && bpmn.getProcess().getUserTasks()!=null) {
				Collection<WorkflowProcessTask> workflowProcessTasks = __inject__(WorkflowProcessTaskBusiness.class).findByWorkflowCodeByProcessCode(workflowProcessDto.getWorkflow()
						, workflowProcessDto.getCode());
				for(UserTask index : bpmn.getProcess().getUserTasks()) {
					Task task = new Task().setName(index.getName()).addUsers(index.getPotentialOwnerResourceAssignmentExpressionFormalExpression());
					if(workflowProcessTasks!=null)
						for(WorkflowProcessTask indexWorkflowProcessTask : workflowProcessTasks)
							if(indexWorkflowProcessTask.getName().equals(task.getName())) {
								task.setStatus(indexWorkflowProcessTask.getStatus().name());
							}
					workflowProcessDto.getTasks(Boolean.TRUE).add(task);
				}
			}
		}
		return response;
	}
	
	@Override
	public Response getByWorkflowCodeByCode(String workflowCode,String code) {
		WorkflowProcessDto workflowProcessDto = __injectInstanceHelper__().buildOne(WorkflowProcessDto.class, getBusiness().findByWorkflowCodeByCode(workflowCode,code));
		
		if(workflowProcessDto!=null) {
			Workflow workflow = __inject__(WorkflowBusiness.class).findOne(workflowProcessDto.getWorkflow(),ValueUsageType.BUSINESS);
			if(workflow!=null && workflow.getModel()!=null && !workflow.getModel().isEmpty()) {
				Bpmn bpmn = Bpmn.__executeWithContent__(workflow.getModel());
				if(bpmn!=null && bpmn.getProcess()!=null && bpmn.getProcess().getUserTasks()!=null) {
					Collection<WorkflowProcessTask> workflowProcessTasks = __inject__(WorkflowProcessTaskBusiness.class).findByWorkflowCodeByProcessCode(workflowProcessDto.getWorkflow()
							, workflowProcessDto.getCode());
					
					for(WorkflowProcessTask index : workflowProcessTasks) {
						index.setCode(__inject__(WorkflowProcessNodeLogPersistence.class).readByWorkflowCodeByProcessCodeByWorkItemIdentifier(workflowCode, code,index.getWorkItemIdentifier())
								.iterator().next().getCode());
						//System.out.println("TASK : "+index);
					}
					
					for(UserTask index : bpmn.getProcess().getUserTasks()) {
						Task task = new Task().setName(index.getName()).addUsers(index.getPotentialOwnerResourceAssignmentExpressionFormalExpression());
						if(workflowProcessTasks!=null)
							for(WorkflowProcessTask indexWorkflowProcessTask : workflowProcessTasks)
								if(indexWorkflowProcessTask.getCode().equals(index.getId())) {
									task.setStatus(indexWorkflowProcessTask.getStatus().name());
								}
						workflowProcessDto.getTasks(Boolean.TRUE).add(task);
					}
				}
			}
		}
		
		return workflowProcessDto ==null ? Response.status(Response.Status.NOT_FOUND).build() : Response.status(Response.Status.OK).entity(new GenericEntity<WorkflowProcessDto>(workflowProcessDto, getEntityClass())).build();
	}
	
	@Override
	public Response deleteByWorkflowCodeByCode(String workflowCode,String code) {
		getBusiness().deleteByWorkflowCodeByCode(workflowCode,code);
		return Response.status(Response.Status.OK).build();
	}
	
	/**/
	
}

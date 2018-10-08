package ci.gouv.dgbf.system.workflow.server.representation.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;
import org.kie.api.task.model.Status;

import ci.gouv.dgbf.system.workflow.server.business.api.UserBusiness;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessNodeLogPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessTaskLogPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.User;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTaskLog;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.bpmn.Bpmn;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.bpmn.UserTask;
import ci.gouv.dgbf.system.workflow.server.representation.api.UserRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.entities.Task;
import ci.gouv.dgbf.system.workflow.server.representation.entities.UserDto;
import ci.gouv.dgbf.system.workflow.server.representation.entities.UserDtoCollection;

public class UserRepresentationImpl extends AbstractRepresentationEntityImpl<User, UserBusiness, UserDto,UserDtoCollection> implements UserRepresentation,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Response getOne(String identifier, String type) {
		Response response = super.getOne(identifier, type);
		
		UserDto userDto = (UserDto)response.getEntity();
		if(userDto!=null) {
			Collection<Workflow> workflows = __inject__(WorkflowPersistence.class).readMany();
			addTasks(userDto, workflows, __inject__(WorkflowProcessTaskLogPersistence.class).readByUserCodeByStatusCodes(userDto.getCode(), Status.Reserved.name()), Boolean.FALSE);
			addTasks(userDto, workflows, __inject__(WorkflowProcessTaskLogPersistence.class).readByUserCodeByStatusCodes(userDto.getCode(), Status.Completed.name()), Boolean.TRUE);
		}
		return response;
	}
	
	private void addTasks(UserDto userDto,Collection<Workflow> workflows,Collection<WorkflowProcessTaskLog> tasks,Boolean completed) {
		for(WorkflowProcessTaskLog indexTask : tasks) {
			Task task = null;
			for(Workflow indexWorkflow : workflows) {
				Bpmn bpmn = Bpmn.__executeWithContent__(indexWorkflow.getModel());
				if(bpmn!=null && bpmn.getProcess()!=null && bpmn.getProcess().getUserTasks()!=null) {
					indexTask.setCode(__inject__(WorkflowProcessNodeLogPersistence.class).readByWorkflowCodeByProcessIdentifierByWorkItemIdentifier(indexWorkflow.getCode()
							, indexTask.getWorkflowProcessTask().getWorkflowProcess().getIdentifier(),indexTask.getWorkflowProcessTask().getWorkItemIdentifier()).iterator().next().getCode());
					
					for(UserTask indexUserTask : bpmn.getProcess().getUserTasks()) {							
						for(WorkflowProcessTaskLog indexWorkflowProcessTask : tasks)
							if(indexWorkflowProcessTask.getCode()!=null && indexWorkflowProcessTask.getCode().equals(indexUserTask.getId()) && indexUserTask.getPotentialOwnerResourceAssignmentExpressionFormalExpression().equals(userDto.getCode())) {
								task = new Task().setName(indexUserTask.getName()).addUsers(indexUserTask.getPotentialOwnerResourceAssignmentExpressionFormalExpression());
								task.setStatus(indexWorkflowProcessTask.getStatus());
								break;
							}
					}	
				}
			}
			if(task!=null)
				//((Boolean.TRUE.equals(completed) ? userDto.getCompletedTasks(Boolean.TRUE) : userDto.getNotCompletedTasks(Boolean.TRUE)).add(task);
				if(Boolean.TRUE.equals(completed))
					userDto.getCompletedTasks(Boolean.TRUE).add(task);
				else
					userDto.getNotCompletedTasks(Boolean.TRUE).add(task);
		}
	}
	
}

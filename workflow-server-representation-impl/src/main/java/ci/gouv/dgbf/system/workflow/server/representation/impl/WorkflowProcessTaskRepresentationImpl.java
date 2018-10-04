package ci.gouv.dgbf.system.workflow.server.representation.impl;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

import ci.gouv.dgbf.system.workflow.server.business.api.WorkflowProcessTaskBusiness;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTask;
import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowProcessTaskRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowProcessTaskDto;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowProcessTaskDtoCollection;

public class WorkflowProcessTaskRepresentationImpl extends AbstractRepresentationEntityImpl<WorkflowProcessTask, WorkflowProcessTaskBusiness, WorkflowProcessTaskDto,WorkflowProcessTaskDtoCollection> implements WorkflowProcessTaskRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	public Response execute(String workflowCode, String workflowProcessCode, String userIdentifier) {
		getBusiness().execute(workflowCode,workflowProcessCode, userIdentifier);
		return Response.ok().build();
	}
	
	@Override
	public Response getByWorkflowCode(String workflowCode) {
		List<?> collection = (List<?>) __injectInstanceHelper__().buildMany(WorkflowProcessTaskDto.class, getBusiness().findByWorkflowCode(workflowCode));
		return Response.ok(new GenericEntity<List<?>>(collection,(Type) __injectTypeHelper__().instanciateCollectionParameterizedType(List.class, WorkflowProcessTask.class))).build();
	}
	
	@Override
	public Response countByWorkflowCode(String workflowCode) {
		return Response.ok(getBusiness().countByWorkflowCode(workflowCode)).build();
	}
	
	@Override
	public Response getByWorkflowCodeByProcessCodeByUserIdentifier(String workflowCode,String processCode, String userIdentifier) {
		List<?> collection = (List<?>) __injectInstanceHelper__().buildMany(WorkflowProcessTaskDto.class, getBusiness().findByWorkflowCodeByProcessCodeByUserIdentifier(workflowCode,processCode,userIdentifier));
		if(collection == null)
			return Response.ok().build();
		return Response.ok(new GenericEntity<List<?>>(collection,(Type) __injectTypeHelper__().instanciateCollectionParameterizedType(List.class, WorkflowProcessTask.class))).build();
	}
	
	@Override
	public Response countByWorkflowCodeByProcessCodeByUserIdentifier(String workflowCode, String processCode,String userIdentifier) {
		return Response.ok(getBusiness().countByWorkflowCodeByProcessCodeByUserIdentifier(workflowCode, processCode, userIdentifier)).build();
	}
	
}

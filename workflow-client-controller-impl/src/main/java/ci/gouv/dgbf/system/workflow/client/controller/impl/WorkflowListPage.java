package ci.gouv.dgbf.system.workflow.client.controller.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.ws.rs.core.Response;

import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageImpl;

import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowDto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Named
public class WorkflowListPage extends AbstractPageImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<WorkflowDto> workflowDtos;
		
	public List<WorkflowDto> getWorkflowDtos() {
		if(workflowDtos == null) {
			//WorkflowRepresentation workflowRepresentation = __getProxyByUri__(WorkflowRepresentation.class,__getUri__(WorkflowRepresentation.class));
			WorkflowRepresentation workflowRepresentation = __getProxyByRequest__(WorkflowRepresentation.class,FacesContext.getCurrentInstance().getExternalContext().getRequest());
			Response response = workflowRepresentation.getMany();
			Collection<WorkflowDto> dtos = __readEntityAsCollection__(response, WorkflowDto.class);
			workflowDtos = (List<WorkflowDto>) __injectInstanceHelper__().buildMany(WorkflowDto.class, dtos);
		}
		return workflowDtos;
	}
	
}
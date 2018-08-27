package ci.gouv.dgbf.system.workflow.server.representation.api;

import javax.ws.rs.Path;

import org.cyk.utility.server.representation.RepresentationEntity;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowDto;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowDtoCollection;

@Path(WorkflowRepresentation.PATH)
public interface WorkflowRepresentation extends RepresentationEntity<Workflow,WorkflowDto,WorkflowDtoCollection> {

	String PATH = "/workflow/";
}
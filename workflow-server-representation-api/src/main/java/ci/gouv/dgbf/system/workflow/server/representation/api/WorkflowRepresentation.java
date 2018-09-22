package ci.gouv.dgbf.system.workflow.server.representation.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cyk.utility.server.representation.RepresentationEntity;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowDto;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowDtoCollection;

@Path(WorkflowRepresentation.PATH)
public interface WorkflowRepresentation extends RepresentationEntity<Workflow,WorkflowDto,WorkflowDtoCollection> {
	
	@GET
	@Path(PATH_SYNCHRONISE_WITH_JBPM_MAVEN_REPOSITORY)
	@Produces(MediaType.TEXT_PLAIN)
	Response synchroniseWithJbpmMavenRepository();
	
	String PATH = "/workflow";
	
	String PATH_SYNCHRONISE_WITH_JBPM_MAVEN_REPOSITORY = "/execute/synchronise";
}
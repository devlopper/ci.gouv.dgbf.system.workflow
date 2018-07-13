package ci.gouv.dgbf.system.workflow.client.controller.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.ws.rs.core.UriBuilder;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowDto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Named
public class WorkflowListPage extends AbstractListPage implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<WorkflowDto> workflowDtos;
	
	@PostConstruct
	public void listenPostConstruct(){
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(UriBuilder.fromPath("http://localhost:8081"));
		WorkflowRepresentation workflowRepresentation = target.proxy(WorkflowRepresentation.class);
		workflowDtos = new ArrayList<>(workflowRepresentation.getAll());
	}
	
}

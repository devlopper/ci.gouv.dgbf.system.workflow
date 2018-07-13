package ci.gouv.dgbf.system.workflow.client.controller.impl;

import java.io.Serializable;

import javax.ws.rs.core.UriBuilder;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import ci.gouv.dgbf.system.workflow.client.controller.api.WorkflowForm;
import ci.gouv.dgbf.system.workflow.client.controller.entities.WorkflowData;
import ci.gouv.dgbf.system.workflow.client.controller.entities.WorkflowEntityMapper;
import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowDto;

public class WorkflowFormImpl extends AbstractFormImpl<WorkflowData> implements WorkflowForm , Serializable {
	private static final long serialVersionUID = 1L;

	public WorkflowFormImpl() {
		setData(new WorkflowData());
	}
	
	@Override
	public void submit() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(UriBuilder.fromPath("http://localhost:8081"));
		WorkflowRepresentation workflowRepresentation = target.proxy(WorkflowRepresentation.class);
		WorkflowDto dto = WorkflowEntityMapper.INSTANCE.getRepresentationFromData(getData());
		workflowRepresentation.createOne(dto);
	}

}

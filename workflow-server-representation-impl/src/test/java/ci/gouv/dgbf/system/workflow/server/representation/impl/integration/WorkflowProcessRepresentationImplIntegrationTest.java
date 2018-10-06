package ci.gouv.dgbf.system.workflow.server.representation.impl.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.server.representation.test.arquillian.AbstractRepresentationEntityIntegrationTestWithDefaultDeploymentAsSwram;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;

import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowProcessRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowDto;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowProcessDto;

public class WorkflowProcessRepresentationImplIntegrationTest extends AbstractRepresentationEntityIntegrationTestWithDefaultDeploymentAsSwram<WorkflowProcessDto> {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBeforeCallCountIsZero__() throws Exception {
		super.__listenBeforeCallCountIsZero__();
		__inject__(WorkflowRepresentation.class).createOne(new WorkflowDto().setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2"));
	}
	
	@Override public void createMany() throws Exception {}
	@Override public void createOne_businessIdentifierMustNotBeNull() throws Exception {}
	@Override public void createOne_businessIdentifierMustBeUnique() throws Exception {}
	@Override public void createOne() throws Exception {}
	@Override public void readOneByBusinessIdentifier() throws Exception {}
	@Override public void readOneByBusinessIdentifier_notFound() throws Exception {}
	@Override public void readOneBySystemIdentifier() throws Exception {}
	@Override public void readOneBySystemIdentifier_notFound() throws Exception {}
	@Override public void readMany() throws Exception {}
	@Override public void updateOne() throws Exception {}
	@Override public void deleteOne() throws Exception {}
	
	@Test @InSequence(1)
	public void readAllBeforeAnyOperation(){
		Response response = __inject__(WorkflowProcessRepresentation.class).getMany();
		assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
		@SuppressWarnings("unchecked")
		Collection<WorkflowProcessDto> workflowProcessDtos = (Collection<WorkflowProcessDto>) response.getEntity();
		assertThat(workflowProcessDtos).isNull();
	}
	
	@Test @InSequence(2)
	public void countWorkflowProcessBeforeCreate(){
		assertThat(__inject__(WorkflowProcessRepresentation.class).count().getEntity()).isEqualTo(0l);
	}
	
	@Test @InSequence(3)
	public void readWorkflowProcessByWorkflowCodeByCodeBeforeCreate(){
		assertThat(__inject__(WorkflowProcessRepresentation.class).getByWorkflowCodeByCode("ci.gouv.dgbf.workflow.validation.pap","pap001").getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
	}
	
	@Test @InSequence(4)
	public void createWorkflowProcess(){
		Response response = __inject__(WorkflowProcessRepresentation.class).createOne(new WorkflowProcessDto().setCode("pap001").setWorkflow("ci.gouv.dgbf.workflow.validation.pap"));
		assertThat(response.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());
		response.close();
	}
	
	@Test @InSequence(5)
	public void countWorkflowProcessAfterCreate(){
		assertThat(__inject__(WorkflowProcessRepresentation.class).count().getEntity()).isEqualTo(1l);
	}
	
	@Test @InSequence(6)
	public void readWorkflowProcessByWorkflowCodeByCodeAfterCreate(){
		Response response = __inject__(WorkflowProcessRepresentation.class).getByWorkflowCodeByCode("ci.gouv.dgbf.workflow.validation.pap","pap001");
		assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
		WorkflowProcessDto workflowProcessDto = (WorkflowProcessDto) response.getEntity();
		assertThat(workflowProcessDto).isNotNull();
		//IDENTIFIER = workflowProcessDto.getIdentifier();
		assertThat(workflowProcessDto.getCode()).isEqualTo("pap001");
		assertThat(workflowProcessDto.getWorkflow()).isEqualTo("ci.gouv.dgbf.workflow.validation.pap");
		assertThat(workflowProcessDto.getState()).isEqualTo("1");
		
	}
	
	@Test @InSequence(7)
	public void readAllAfterCreate(){
		Response response = __inject__(WorkflowProcessRepresentation.class).getMany();
		assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
		@SuppressWarnings("unchecked")
		Collection<WorkflowProcessDto> workflowProcessDtos = (Collection<WorkflowProcessDto>) response.getEntity();
		assertThat(workflowProcessDtos).isNotNull();
		assertThat(workflowProcessDtos).hasSize(1);
	}
}

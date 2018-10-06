package ci.gouv.dgbf.system.workflow.server.representation.impl.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.server.representation.test.arquillian.AbstractRepresentationEntityIntegrationTestWithDefaultDeploymentAsSwram;
import org.cyk.utility.value.ValueUsageType;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;
import org.kie.api.task.model.Status;

import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowProcessRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowProcessTaskRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowDto;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowProcessDto;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowProcessTaskDto;

public class WorkflowProcessTaskRepresentationImplIntegrationTest extends AbstractRepresentationEntityIntegrationTestWithDefaultDeploymentAsSwram<WorkflowProcessTaskDto> {
	private static final long serialVersionUID = 1L;
	
	private static String IDENTIFIER;
	
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
	
	@Override
	protected void __listenBeforeCallCountIsZero__() throws Exception {
		super.__listenBeforeCallCountIsZero__();
		__inject__(WorkflowRepresentation.class).createOne(new WorkflowDto().setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2"));
		__inject__(WorkflowRepresentation.class).createOne(new WorkflowDto().setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP V01.bpmn2"));
		IDENTIFIER = ((WorkflowDto)__inject__(WorkflowRepresentation.class).getOne("ci.gouv.dgbf.workflow.validation.pap", "business").getEntity()).getIdentifier();
		__inject__(WorkflowProcessRepresentation.class).createOne(new WorkflowProcessDto().setCode("pap001").setWorkflow("ci.gouv.dgbf.workflow.validation.pap"));
		__inject__(WorkflowProcessRepresentation.class).createOne(new WorkflowProcessDto().setCode("pap002").setWorkflow("ci.gouv.dgbf.workflow.validation.pap"));
		__inject__(WorkflowProcessRepresentation.class).createOne(new WorkflowProcessDto().setCode("pap001").setWorkflow("ci.gouv.dgbf.workflow.validation.pap.v01"));
	}
	
	@Test @InSequence(1)
	public void countWorkflowProcessTaskBeforeExecute(){
		assertThat(__inject__(WorkflowProcessTaskRepresentation.class).countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap").getEntity()).isEqualTo(2l);
		assertThat(__inject__(WorkflowProcessTaskRepresentation.class).countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap.v01").getEntity()).isEqualTo(1l);
		assertThat(__inject__(WorkflowProcessTaskRepresentation.class).count().getEntity()).isEqualTo(3l);
	}
	
	@Test @InSequence(2)
	public void readWorkflowProcessTasksBeforeExecute(){
		assertThat(__inject__(WorkflowProcessTaskRepresentation.class).getByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap").getEntity()).asList().isNotEmpty();
		//assertThat(__inject__(WorkflowProcessTaskRepresentation.class).getByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap").iterator().next().getOwner()).isEqualTo("charge_etude");
		assertThat(__inject__(WorkflowProcessTaskRepresentation.class).getByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap.v01").getEntity()).asList().isNotEmpty();
		assertThat(__inject__(WorkflowProcessTaskRepresentation.class).getMany().getEntity()).asList().isNotEmpty();
		
		@SuppressWarnings("unchecked")
		Collection<WorkflowProcessTaskDto> workflowProcessTaskDtos = (Collection<WorkflowProcessTaskDto>) __inject__(WorkflowProcessTaskRepresentation.class)
				.getByWorkflowCodeByProcessCodeByUserIdentifier("ci.gouv.dgbf.workflow.validation.pap","pap001","charge_etude").getEntity();
		assertThat(workflowProcessTaskDtos).hasSize(1);
		
		WorkflowProcessTaskDto workflowProcessTaskDto = workflowProcessTaskDtos.iterator().next();
		assertThat(workflowProcessTaskDto.getStatus()).isEqualTo(Status.Reserved.name());
		assertThat(workflowProcessTaskDto.getName()).isEqualTo("Premiere Validation");
		assertThat(workflowProcessTaskDto.getOwner()).isEqualTo("charge_etude");
		
		WorkflowProcessDto workflowProcessDto = (WorkflowProcessDto) __inject__(WorkflowProcessRepresentation.class).getByWorkflowCodeByCode("ci.gouv.dgbf.workflow.validation.pap", "pap001").getEntity();
		assertThat(workflowProcessDto).isNotNull();
		assertThat(workflowProcessDto.getTasks()).isNotNull();
		assertThat(workflowProcessDto.getTasks().getCollection()).asList().hasSize(3);
		assertThat(workflowProcessDto.getTasks().getAt(0)).isNotNull();
		assertThat(workflowProcessDto.getTasks().getAt(0).getStatus()).isEqualTo("Reserved");
		assertThat(workflowProcessDto.getTasks().getAt(1).getStatus()).isNull();
		assertThat(workflowProcessDto.getTasks().getAt(2).getStatus()).isNull();
	}
	
	@Test @InSequence(3)
	public void executeWorkflowProcessTask(){
		Response response = __inject__(WorkflowProcessTaskRepresentation.class).execute("ci.gouv.dgbf.workflow.validation.pap","pap001","charge_etude");
		assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
		response.close();
	}
	
	@Test @InSequence(4)
	public void countWorkflowProcessTaskAfterExecute(){
		assertThat(__inject__(WorkflowProcessTaskRepresentation.class).countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap").getEntity()).isEqualTo(3l);
		assertThat(__inject__(WorkflowProcessTaskRepresentation.class).countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap.v01").getEntity()).isEqualTo(1l);
		assertThat(__inject__(WorkflowProcessTaskRepresentation.class).count().getEntity()).isEqualTo(4l);
		
		WorkflowProcessDto workflowProcessDto = (WorkflowProcessDto) __inject__(WorkflowProcessRepresentation.class).getByWorkflowCodeByCode("ci.gouv.dgbf.workflow.validation.pap", "pap001").getEntity();
		assertThat(workflowProcessDto).isNotNull();
		assertThat(workflowProcessDto.getTasks()).isNotNull();
		assertThat(workflowProcessDto.getTasks().getCollection()).asList().hasSize(3);
		assertThat(workflowProcessDto.getTasks().getAt(0)).isNotNull();
		assertThat(workflowProcessDto.getTasks().getAt(0).getStatus()).isEqualTo("Completed");
		assertThat(workflowProcessDto.getTasks().getAt(1).getStatus()).isEqualTo("Reserved");
		assertThat(workflowProcessDto.getTasks().getAt(2).getStatus()).isNull();
	}
	
	@Test @InSequence(5)
	public void readWorkflowProcessTaskAfterExecute(){
		assertThat(__inject__(WorkflowProcessTaskRepresentation.class).getByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap").getEntity()).asList().isNotEmpty();
		assertThat(__inject__(WorkflowProcessTaskRepresentation.class).getByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap.v01").getEntity()).asList().isNotEmpty();
		assertThat(__inject__(WorkflowProcessTaskRepresentation.class).getMany().getEntity()).asList().isNotEmpty();
	}
	
	@Test @InSequence(6)
	public void readWorkflowByIdentifierAfterCreate(){
		WorkflowDto workflowDto = (WorkflowDto) __inject__(WorkflowRepresentation.class).getOne(IDENTIFIER,ValueUsageType.SYSTEM.name()).getEntity();
		assertThat(workflowDto).isNotNull();
		assertThat(workflowDto.getIdentifier()).isEqualTo(IDENTIFIER);
		assertThat(workflowDto.getCode()).isEqualTo("ci.gouv.dgbf.workflow.validation.pap");
		assertThat(workflowDto.getName()).isEqualTo("Validation du PAP");
	}
	
	@Test @InSequence(7)
	public void deleteWorkflowByIdentifier(){
		Response response = __inject__(WorkflowRepresentation.class).deleteOne(IDENTIFIER,"system");
		assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
		response.close();
	}
	
	@Test @InSequence(8)
	public void countWorkflowAfterDeleteByIdentifier(){
		assertThat(__inject__(WorkflowRepresentation.class).count().getEntity()).isEqualTo(1l);
	}
	
	@Test @InSequence(9)
	public void readWorkflowByCodeAfterDeleteByIdentifier(){
		assertThat(__inject__(WorkflowRepresentation.class).getOne("ci.gouv.dgbf.workflow.validation.pap","business").getEntity()).isNull();
		assertThat(__inject__(WorkflowRepresentation.class).getOne("ci.gouv.dgbf.workflow.validation.pap.v01","business").getEntity()).isNotNull();
	}
	
	@Test @InSequence(10)
	public void readWorkflowByIdentifierAfterDeleteByIdentifier(){
		assertThat(__inject__(WorkflowRepresentation.class).deleteOne(IDENTIFIER,"system").getStatusInfo().getStatusCode()).isEqualTo(500);
	}
	
}

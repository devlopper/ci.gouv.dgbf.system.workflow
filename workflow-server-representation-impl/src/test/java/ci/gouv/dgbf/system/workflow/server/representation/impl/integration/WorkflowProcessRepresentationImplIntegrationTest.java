package ci.gouv.dgbf.system.workflow.server.representation.impl.integration;

import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.core.Response;

import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;

import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowProcessRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowDto;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowProcessDto;

public class WorkflowProcessRepresentationImplIntegrationTest extends AbstractIntegrationTest {
	/*
	private static WorkflowProcessRepresentation WORKFLOW_PROCESS_REPRESENTATION;
	private static WorkflowRepresentation WORKFLOW_REPRESENTATION;
	
	@Override
	protected void __initialise__() {
		super.__initialise__();
		WORKFLOW_REPRESENTATION = TARGET.proxy(WorkflowRepresentation.class);
		WORKFLOW_PROCESS_REPRESENTATION = TARGET.proxy(WorkflowProcessRepresentation.class);
		
		WORKFLOW_REPRESENTATION.createOne(new WorkflowDto().setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2"));
	}
	
	@Test @InSequence(1)
	public void countWorkflowProcessBeforeCreate(){
		assertThat(WORKFLOW_PROCESS_REPRESENTATION.countAll()).isEqualTo(0);
	}
	
	@Test @InSequence(2)
	public void readWorkflowProcessByWorkflowCodeByCodeBeforeCreate(){
		assertThat(WORKFLOW_PROCESS_REPRESENTATION.getByWorkflowCodeByCode("ci.gouv.dgbf.workflow.validation.pap","pap001")).isNull();
	}
	
	@Test @InSequence(3)
	public void createWorkflowProcess(){
		Response response = WORKFLOW_PROCESS_REPRESENTATION.createOne(new WorkflowProcessDto().setCode("pap001").setWorkflowCode("ci.gouv.dgbf.workflow.validation.pap"));
		assertThat(response.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());
		response.close();
	}
	
	@Test @InSequence(4)
	public void countWorkflowProcessAfterCreate(){
		assertThat(WORKFLOW_PROCESS_REPRESENTATION.countAll()).isEqualTo(1);
	}
	
	@Test @InSequence(5)
	public void readWorkflowProcessByWorkflowCodeByCodeAfterCreate(){
		WorkflowProcessDto workflowProcessDto = WORKFLOW_PROCESS_REPRESENTATION.getByWorkflowCodeByCode("ci.gouv.dgbf.workflow.validation.pap","pap001");
		assertThat(workflowProcessDto).isNotNull();
		IDENTIFIER = workflowProcessDto.getIdentifier();
		assertThat(workflowProcessDto.getCode()).isEqualTo("pap001");
		assertThat(workflowProcessDto.getWorkflowCode()).isEqualTo("ci.gouv.dgbf.workflow.validation.pap");
		assertThat(workflowProcessDto.getState()).isEqualTo("1");
	}
	*/
}

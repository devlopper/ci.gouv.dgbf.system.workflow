package ci.gouv.dgbf.system.workflow.server.representation.impl.integration;

import org.cyk.utility.server.representation.AbstractEntityCollection;
import org.cyk.utility.server.representation.test.arquillian.AbstractRepresentationEntityIntegrationTestWithDefaultDeploymentAsSwram;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;

public class WorkflowProcessRepresentationImplIntegrationTest extends AbstractRepresentationEntityIntegrationTestWithDefaultDeploymentAsSwram<WorkflowProcess> {
	private static final long serialVersionUID = 1L;

	@Override
	protected <ENTITY> Class<? extends AbstractEntityCollection<ENTITY>> __getEntityCollectionClass__(Class<ENTITY> arg0) {
		// TODO Auto-generated method stub
		return null;
	}
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

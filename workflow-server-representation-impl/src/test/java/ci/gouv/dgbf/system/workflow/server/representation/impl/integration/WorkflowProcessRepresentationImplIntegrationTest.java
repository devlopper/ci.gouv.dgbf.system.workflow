package ci.gouv.dgbf.system.workflow.server.representation.impl.integration;

import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.core.Response;

import org.cyk.utility.server.representation.AbstractEntityCollection;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.test.arquillian.AbstractRepresentationEntityIntegrationTestWithDefaultDeploymentAsSwram;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;

import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowProcessRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowDto;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowProcessDto;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowProcessDtoCollection;

public class WorkflowProcessRepresentationImplIntegrationTest extends AbstractRepresentationEntityIntegrationTestWithDefaultDeploymentAsSwram<WorkflowProcessDto> {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBeforeCallCountIsZero__() throws Exception {
		super.__listenBeforeCallCountIsZero__();
		__inject__(WorkflowRepresentation.class).createOne(new WorkflowDto().setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2"));
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Class<? extends RepresentationEntity> __getLayerEntityInterfaceClass__() {
		return WorkflowProcessRepresentation.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected <ENTITY> Class<? extends AbstractEntityCollection<ENTITY>> __getEntityCollectionClass__(Class<ENTITY> aClass) {
		try {
			return (Class<? extends AbstractEntityCollection<ENTITY>>) Class.forName(WorkflowProcessDtoCollection.class.getName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public <ENTITY> void __createEntity__(ENTITY entity, @SuppressWarnings("rawtypes") RepresentationEntity layerEntityInterface) {
		((WorkflowProcessDto)entity).setCode("PAP001").setWorkflow("ci.gouv.dgbf.workflow.validation.pap");
		super.__createEntity__(entity, layerEntityInterface);
	}
	
	@Override public void updateOne() throws Exception {}
	@Override public void deleteOne() throws Exception {}
	@Override public void readOneByBusinessIdentifier() throws Exception {}
	@Override public void readOneBySystemIdentifier() throws Exception {}
	@Override public void createMany() throws Exception {}
	@Override public void createOne() throws Exception {}
	
	
	@Test @InSequence(1)
	public void countWorkflowProcessBeforeCreate(){
		assertThat(__inject__(WorkflowProcessRepresentation.class).count().getEntity()).isEqualTo(0l);
	}
	
	@Test @InSequence(2)
	public void readWorkflowProcessByWorkflowCodeByCodeBeforeCreate(){
		assertThat(__inject__(WorkflowProcessRepresentation.class).getByWorkflowCodeByCode("ci.gouv.dgbf.workflow.validation.pap","pap001").getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
	}
	
	@Test @InSequence(3)
	public void createWorkflowProcess(){
		Response response = __inject__(WorkflowProcessRepresentation.class).createOne(new WorkflowProcessDto().setCode("pap001").setWorkflow("ci.gouv.dgbf.workflow.validation.pap"));
		assertThat(response.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());
		response.close();
	}
	
	@Test @InSequence(4)
	public void countWorkflowProcessAfterCreate(){
		assertThat(__inject__(WorkflowProcessRepresentation.class).count().getEntity()).isEqualTo(1l);
	}
	
	@Test @InSequence(5)
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
	
}

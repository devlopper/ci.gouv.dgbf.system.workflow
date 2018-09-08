package ci.gouv.dgbf.system.workflow.server.deployment.standalone;

import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.core.Response;

import org.cyk.utility.server.representation.AbstractEntityCollection;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.test.arquillian.AbstractRepresentationEntityIntegrationTestWithDefaultDeploymentAsSwram;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowDto;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowDtoCollection;

public class WorkflowRepresentationImplIntegrationTest extends AbstractRepresentationEntityIntegrationTestWithDefaultDeploymentAsSwram<WorkflowDto> {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	@Override
	public Class<? extends RepresentationEntity> __getLayerEntityInterfaceClass__() {
		return WorkflowRepresentation.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected <ENTITY> Class<? extends AbstractEntityCollection<ENTITY>> __getEntityCollectionClass__(Class<ENTITY> aClass) {
		try {
			return (Class<? extends AbstractEntityCollection<ENTITY>>) Class.forName(WorkflowDtoCollection.class.getName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}	
	
	@Override public void createMany() throws Exception {}
	
	@Override public void updateOne() throws Exception {}
	
	@Override
	public <ENTITY> void __createEntity__(ENTITY entity, @SuppressWarnings("rawtypes") RepresentationEntity layerEntityInterface) {
		((WorkflowDto)entity).setModel(XML);
		super.__createEntity__(entity, layerEntityInterface);
	}
	
	@Test @InSequence(1)
	public void countWorkflowBeforeCreate(){
		WorkflowRepresentation workflowRepresentation = (WorkflowRepresentation) ____getLayerEntityInterfaceFromClass____(Workflow.class);
		Response response = workflowRepresentation.count();
		assertThat(response.readEntity(Long.class)).isEqualTo(0);
		response.close();
	}
	
	@Test @InSequence(2)
	public void readWorkflowByCodeBeforeCreate(){
		WorkflowRepresentation workflowRepresentation = (WorkflowRepresentation) ____getLayerEntityInterfaceFromClass____(Workflow.class);
		Response response = workflowRepresentation.getOne("ci.gouv.dgbf.workflow.validation.pap","business");
		assertThat(response.getStatus()).isEqualTo(Response.Status.NO_CONTENT.getStatusCode());
		response.close();
	}
	/*
	@Test @InSequence(3)
	public void createWorkflow(){
		WorkflowRepresentation workflowRepresentation = (WorkflowRepresentation) ____getLayerEntityInterfaceFromClass____(Workflow.class);
		Response response = workflowRepresentation.createOne(new WorkflowDto().setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2"));
		assertThat(response.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());
		response.close();
	}
	
	@Test @InSequence(4)
	public void countWorkflowAfterCreate(){
		WorkflowRepresentation workflowRepresentation = (WorkflowRepresentation) ____getLayerEntityInterfaceFromClass____(Workflow.class);
		assertThat(workflowRepresentation.countAll()).isEqualTo(1);
	}
	
	@Test @InSequence(5)
	public void readWorkflowByCodeAfterCreate(){
		WorkflowRepresentation workflowRepresentation = (WorkflowRepresentation) ____getLayerEntityInterfaceFromClass____(Workflow.class);
		WorkflowDto workflowDto = workflowRepresentation.getByCode("ci.gouv.dgbf.workflow.validation.pap");
		assertThat(workflowDto).isNotNull();
		IDENTIFIER = workflowDto.getIdentifier();
		assertThat(workflowDto.getCode()).isEqualTo("ci.gouv.dgbf.workflow.validation.pap");
		assertThat(workflowDto.getName()).isEqualTo("Validation du PAP");
	}
	
	@Test @InSequence(6)
	public void readWorkflowByIdentifierAfterCreate(){
		WorkflowRepresentation workflowRepresentation = (WorkflowRepresentation) ____getLayerEntityInterfaceFromClass____(Workflow.class);
		WorkflowDto workflowDto = workflowRepresentation.getByIdentifier(IDENTIFIER);
		assertThat(workflowDto).isNotNull();
		assertThat(workflowDto.getIdentifier()).isEqualTo(IDENTIFIER);
		assertThat(workflowDto.getCode()).isEqualTo("ci.gouv.dgbf.workflow.validation.pap");
		assertThat(workflowDto.getName()).isEqualTo("Validation du PAP");
	}
	
	@Test @InSequence(7)
	public void updateWorkflow(){
		WorkflowRepresentation workflowRepresentation = (WorkflowRepresentation) ____getLayerEntityInterfaceFromClass____(Workflow.class);
		WorkflowDto workflowDto = workflowRepresentation.getByIdentifier(IDENTIFIER);
		workflowDto.setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP V01.bpmn2");
		Response response = workflowRepresentation.updateOne(workflowDto);
		assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
		response.close();
	}
	
	@Test @InSequence(8)
	public void countWorkflowAfterUpdate(){
		WorkflowRepresentation workflowRepresentation = (WorkflowRepresentation) ____getLayerEntityInterfaceFromClass____(Workflow.class);
		assertThat(workflowRepresentation.countAll()).isEqualTo(1);
	}
	
	@Test @InSequence(9)
	public void readWorkflowByCodeAfterUpdate(){
		WorkflowRepresentation workflowRepresentation = (WorkflowRepresentation) ____getLayerEntityInterfaceFromClass____(Workflow.class);
		assertThat(workflowRepresentation.getByCode("ci.gouv.dgbf.workflow.validation.pap")).isNull();
		WorkflowDto workflowDto = workflowRepresentation.getByCode("ci.gouv.dgbf.workflow.validation.pap.v01");
		assertThat(workflowDto).isNotNull();
		IDENTIFIER = workflowDto.getIdentifier();
		assertThat(workflowDto.getCode()).isEqualTo("ci.gouv.dgbf.workflow.validation.pap.v01");
		assertThat(workflowDto.getName()).isEqualTo("Validation du PAP V01");
	}
	
	@Test @InSequence(10)
	public void readWorkflowByIdentifierAfterUpdate(){
		WorkflowRepresentation workflowRepresentation = (WorkflowRepresentation) ____getLayerEntityInterfaceFromClass____(Workflow.class);
		WorkflowDto workflowDto = workflowRepresentation.getByIdentifier(IDENTIFIER);
		assertThat(workflowDto).isNotNull();
		assertThat(workflowDto.getIdentifier()).isEqualTo(IDENTIFIER);
		assertThat(workflowDto.getCode()).isEqualTo("ci.gouv.dgbf.workflow.validation.pap.v01");
		assertThat(workflowDto.getName()).isEqualTo("Validation du PAP V01");
	}
	
	@Test @InSequence(11)
	public void deleteWorkflowByIdentifier(){
		WorkflowRepresentation workflowRepresentation = (WorkflowRepresentation) ____getLayerEntityInterfaceFromClass____(Workflow.class);
		Response response = workflowRepresentation.deleteByIdentifier(IDENTIFIER);
		assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
		response.close();
	}
	
	@Test @InSequence(12)
	public void countWorkflowAfterDeleteByIdentifier(){
		WorkflowRepresentation workflowRepresentation = (WorkflowRepresentation) ____getLayerEntityInterfaceFromClass____(Workflow.class);
		assertThat(workflowRepresentation.countAll()).isEqualTo(0);
	}
	
	@Test @InSequence(13)
	public void readWorkflowByCodeAfterDeleteByIdentifier(){
		WorkflowRepresentation workflowRepresentation = (WorkflowRepresentation) ____getLayerEntityInterfaceFromClass____(Workflow.class);
		assertThat(workflowRepresentation.getByCode("ci.gouv.dgbf.workflow.validation.pap")).isNull();
		assertThat(workflowRepresentation.getByCode("ci.gouv.dgbf.workflow.validation.pap.v01")).isNull();
	}
	
	@Test @InSequence(14)
	public void readWorkflowByIdentifierAfterDeleteByIdentifier(){
		WorkflowRepresentation workflowRepresentation = (WorkflowRepresentation) ____getLayerEntityInterfaceFromClass____(Workflow.class);
		assertThat(workflowRepresentation.getByIdentifier(IDENTIFIER)).isNull();
	}
	
	@Test @InSequence(15)
	public void createWorkflowByManualPost(){
		WorkflowRepresentation workflowRepresentation = (WorkflowRepresentation) ____getLayerEntityInterfaceFromClass____(Workflow.class);
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(UriBuilder.fromPath(contextPath.toExternalForm()+"workflow/create"));
		WorkflowDto dto = new WorkflowDto();
		dto.setModel(XML);
		target.request().post(Entity.entity(XML,MediaType.APPLICATION_XML),String.class);
	}
	
	@Test @InSequence(16)
	public void readWorkflowByCodeAfterCreateByManualPost(){
		WorkflowRepresentation workflowRepresentation = (WorkflowRepresentation) ____getLayerEntityInterfaceFromClass____(Workflow.class);
		assertThat(workflowRepresentation.getByCode("C")).isNull();
		WorkflowDto dto = workflowRepresentation.getByCode("com.sample.hello");
		assertThat(dto).isNotNull();
		assertThat(dto.getCode()).isEqualTo("com.sample.hello");
		assertThat(dto.getName()).isEqualTo("com.sample.hello");
	}
	
	@Test @InSequence(17)
	public void deleteWorkflowByCodeAfterCreateByManualPost(){
		WorkflowRepresentation workflowRepresentation = (WorkflowRepresentation) ____getLayerEntityInterfaceFromClass____(Workflow.class);
		Response response = workflowRepresentation.deleteByCode("com.sample.hello");
		assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
		response.close();
	}
	*/
	public static String XML = ""
			+ " <definitions id=\"Definition\" targetNamespace=\"http://www.jboss.org/drools\""
			+ " typeLanguage=\"http://www.java.com/javaTypes\""
			+ " expressionLanguage=\"http://www.mvel.org/2.0\""
			+ " xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\""
			+ " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
			+ " xsi:schemaLocation=\"http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd\""
			+ " xmlns:g=\"http://www.jboss.org/drools/flow/gpd\""
			+ " xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\""
			+ " xmlns:dc=\"http://www.omg.org/spec/DD/20100524/DC\""
			+ " xmlns:di=\"http://www.omg.org/spec/DD/20100524/DI\""
			+ " xmlns:tns=\"http://www.jboss.org/drools\">"
			+ " <process processType=\"Private\" isExecutable=\"true\" id=\"ci.gouv.dgbf.workflow.validation.pap\" name=\"Validation du PAP\" tns:packageName=\"com.sample\" >"
			+ " <startEvent id=\"_jbpm-unique-0\" name=\"Start\"  isInterrupting=\"false\">"
			+ " </startEvent>"
			+ " <scriptTask id=\"_jbpm-unique-1\" name=\"Action\" scriptFormat=\"http://www.java.com/java\" >"
			+ " <script>System.out.println(\"Hello World ! This is from the API\");</script>"
			+ " </scriptTask>"
			+ " <endEvent id=\"_jbpm-unique-2\" name=\"End\" >"
			+ " <terminateEventDefinition />"
			+ " </endEvent>"
			+ " <sequenceFlow id=\"_jbpm-unique-0-_jbpm-unique-1\" sourceRef=\"_jbpm-unique-0\" targetRef=\"_jbpm-unique-1\" />"
			+ " <sequenceFlow id=\"_jbpm-unique-1-_jbpm-unique-2\" sourceRef=\"_jbpm-unique-1\" targetRef=\"_jbpm-unique-2\" />"
			+ " </process>"
			+ " </definitions>"
			+ "";
}

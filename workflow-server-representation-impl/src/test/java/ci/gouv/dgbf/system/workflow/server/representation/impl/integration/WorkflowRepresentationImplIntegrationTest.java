package ci.gouv.dgbf.system.workflow.server.representation.impl.integration;

import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.core.Response;

import org.cyk.utility.server.representation.test.arquillian.AbstractRepresentationEntityIntegrationTestWithDefaultDeploymentAsSwram;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowDto;

public class WorkflowRepresentationImplIntegrationTest extends AbstractRepresentationEntityIntegrationTestWithDefaultDeploymentAsSwram<WorkflowDto> {
	private static final long serialVersionUID = 1L;
	
	private static String IDENTIFIER;
	
	@Override public void createMany() throws Exception {}
	@Override public void createOne_businessIdentifierMustNotBeNull() throws Exception {}
	//@Override public void createOne_businessIdentifierMustBeUnique() throws Exception {}
	//@Override public void createOne() throws Exception {}
	//@Override public void readOneByBusinessIdentifier() throws Exception {}
	//@Override public void readOneByBusinessIdentifier_notFound() throws Exception {}
	//@Override public void readOneBySystemIdentifier() throws Exception {}
	//@Override public void readOneBySystemIdentifier_notFound() throws Exception {}
	@Override public void readMany() throws Exception {}
	@Override public void updateOne() throws Exception {}
	//@Override public void deleteOne() throws Exception {}
	//@Override public void readOneByBusinessIdentifier() throws Exception {}
	//@Override public void readOneBySystemIdentifier() throws Exception {}
	//@Override public void updateOne() throws Exception {}
	//@Override public void deleteOne() throws Exception {}
	
	@Override
	protected WorkflowDto __instanciateEntity__(Object action) throws Exception {
		return new WorkflowDto().setModel(XML);
	}
	
	@Test @InSequence(1)
	public void countWorkflowBeforeCreate(){
		WorkflowRepresentation workflowRepresentation = (WorkflowRepresentation) ____getLayerEntityInterfaceFromClass____(Workflow.class);
		Response response = workflowRepresentation.count();
		assertThat(response.getEntity()).isEqualTo(0l);
		response.close();
	}
	
	@Test @InSequence(2)
	public void readWorkflowByCodeBeforeCreate(){
		WorkflowRepresentation workflowRepresentation = (WorkflowRepresentation) ____getLayerEntityInterfaceFromClass____(Workflow.class);
		Response response = workflowRepresentation.getOne("ci.gouv.dgbf.workflow.validation.pap","business");
		assertThat(response.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
		response.close();
	}
	
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
		assertThat(workflowRepresentation.count().getEntity()).isEqualTo(1l);
	}
	
	@Test @InSequence(5)
	public void readWorkflowByCodeAfterCreate(){
		WorkflowRepresentation workflowRepresentation = (WorkflowRepresentation) ____getLayerEntityInterfaceFromClass____(Workflow.class);
		WorkflowDto workflowDto = (WorkflowDto) workflowRepresentation.getOne("ci.gouv.dgbf.workflow.validation.pap","business").getEntity();
		assertThat(workflowDto).isNotNull();
		IDENTIFIER = workflowDto.getIdentifier();
		assertThat(workflowDto.getCode()).isEqualTo("ci.gouv.dgbf.workflow.validation.pap");
		assertThat(workflowDto.getName()).isEqualTo("Validation du PAP");
		assertThat(workflowDto.getModel()).isNotNull();
		assertThat(workflowDto.getTasks().getCollection()).isNotNull().asList().hasSize(3);
	}
	
	@Test @InSequence(6)
	public void readWorkflowByIdentifierAfterCreate(){
		WorkflowRepresentation workflowRepresentation = (WorkflowRepresentation) ____getLayerEntityInterfaceFromClass____(Workflow.class);
		WorkflowDto workflowDto = (WorkflowDto) workflowRepresentation.getOne(IDENTIFIER,"system").getEntity();
		assertThat(workflowDto).isNotNull();
		assertThat(workflowDto.getIdentifier()).isEqualTo(IDENTIFIER);
		assertThat(workflowDto.getCode()).isEqualTo("ci.gouv.dgbf.workflow.validation.pap");
		assertThat(workflowDto.getName()).isEqualTo("Validation du PAP");
	}
	
	@Test @InSequence(7)
	public void updateWorkflow(){
		WorkflowRepresentation workflowRepresentation = (WorkflowRepresentation) ____getLayerEntityInterfaceFromClass____(Workflow.class);
		WorkflowDto workflowDto = (WorkflowDto) workflowRepresentation.getOne(IDENTIFIER,"system").getEntity();
		workflowDto.setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP New Name.bpmn2");
		Response response = workflowRepresentation.updateOne(workflowDto,"model");
		assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
		response.close();
	}
	
	@Test @InSequence(8)
	public void countWorkflowAfterUpdate(){
		WorkflowRepresentation workflowRepresentation = (WorkflowRepresentation) ____getLayerEntityInterfaceFromClass____(Workflow.class);
		assertThat(workflowRepresentation.count().getEntity()).isEqualTo(1l);
	}
	
	@Test @InSequence(9)
	public void readWorkflowByCodeAfterUpdate(){
		WorkflowRepresentation workflowRepresentation = (WorkflowRepresentation) ____getLayerEntityInterfaceFromClass____(Workflow.class);
		WorkflowDto workflowDto = (WorkflowDto) workflowRepresentation.getOne("ci.gouv.dgbf.workflow.validation.pap","business").getEntity();
		assertThat(workflowDto).isNotNull();
		IDENTIFIER = workflowDto.getIdentifier();
		assertThat(workflowDto.getCode()).isEqualTo("ci.gouv.dgbf.workflow.validation.pap");
		assertThat(workflowDto.getName()).isEqualTo("Validation du PAP New Name");
	}
	
	@Test @InSequence(10)
	public void readWorkflowByIdentifierAfterUpdate(){
		WorkflowRepresentation workflowRepresentation = (WorkflowRepresentation) ____getLayerEntityInterfaceFromClass____(Workflow.class);
		WorkflowDto workflowDto = (WorkflowDto) workflowRepresentation.getOne(IDENTIFIER,"system").getEntity();
		assertThat(workflowDto).isNotNull();
		assertThat(workflowDto.getIdentifier()).isEqualTo(IDENTIFIER);
		assertThat(workflowDto.getCode()).isEqualTo("ci.gouv.dgbf.workflow.validation.pap");
		assertThat(workflowDto.getName()).isEqualTo("Validation du PAP New Name");
	}
	
	@Test @InSequence(11)
	public void deleteWorkflowByIdentifier(){
		WorkflowRepresentation workflowRepresentation = (WorkflowRepresentation) ____getLayerEntityInterfaceFromClass____(Workflow.class);
		Response response = workflowRepresentation.deleteOne(IDENTIFIER,"system");
		assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
		response.close();
	}
	
	@Test @InSequence(12)
	public void countWorkflowAfterDeleteByIdentifier(){
		WorkflowRepresentation workflowRepresentation = (WorkflowRepresentation) ____getLayerEntityInterfaceFromClass____(Workflow.class);
		assertThat(workflowRepresentation.count().getEntity()).isEqualTo(0l);
	}
	
	@Test @InSequence(13)
	public void readWorkflowByCodeAfterDeleteByIdentifier(){
		WorkflowRepresentation workflowRepresentation = (WorkflowRepresentation) ____getLayerEntityInterfaceFromClass____(Workflow.class);
		assertThat(workflowRepresentation.getOne("ci.gouv.dgbf.workflow.validation.pap","business").getEntity()).isNull();
		assertThat(workflowRepresentation.getOne("ci.gouv.dgbf.workflow.validation.pap.v01","business").getEntity()).isNull();
	}
	
	@Test @InSequence(14)
	public void readWorkflowByIdentifierAfterDeleteByIdentifier(){
		WorkflowRepresentation workflowRepresentation = (WorkflowRepresentation) ____getLayerEntityInterfaceFromClass____(Workflow.class);
		assertThat(workflowRepresentation.getOne(IDENTIFIER,"system").getEntity()).isNull();
	}
	
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

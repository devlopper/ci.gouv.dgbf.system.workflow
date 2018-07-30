package ci.gouv.dgbf.system.workflow.server.representation.impl.integration;

import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.jboss.arquillian.junit.InSequence;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.junit.Test;

import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowProcessRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowProcessTaskRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowDto;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowProcessDto;

public class WorkflowProcessTaskRepresentationImplIntegrationTest extends AbstractIntegrationTest {
	
	private static WorkflowProcessTaskRepresentation WORKFLOW_PROCESS_TASK_REPRESENTATION;
	private static WorkflowProcessRepresentation WORKFLOW_PROCESS_REPRESENTATION;
	private static WorkflowRepresentation WORKFLOW_REPRESENTATION;
		
	@Override
	protected void __initialise__() {
		super.__initialise__();
		WORKFLOW_PROCESS_TASK_REPRESENTATION = TARGET.proxy(WorkflowProcessTaskRepresentation.class);
		WORKFLOW_PROCESS_REPRESENTATION = TARGET.proxy(WorkflowProcessRepresentation.class);
		WORKFLOW_REPRESENTATION = TARGET.proxy(WorkflowRepresentation.class);
		
		WORKFLOW_REPRESENTATION.createOne(new WorkflowDto().setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2"));
		WORKFLOW_PROCESS_REPRESENTATION.createOne(new WorkflowProcessDto().setCode("pap001").setWorkflowCode("ci.gouv.dgbf.workflow.validation.pap"));
		WORKFLOW_PROCESS_REPRESENTATION.createOne(new WorkflowProcessDto().setCode("pap002").setWorkflowCode("ci.gouv.dgbf.workflow.validation.pap"));
		WORKFLOW_PROCESS_REPRESENTATION.createOne(new WorkflowProcessDto().setCode("pap001").setWorkflowCode("ci.gouv.dgbf.workflow.validation.pap.v01"));
	}
	
	@Test @InSequence(1)
	public void countWorkflowProcessTaskBeforeExecute(){
		assertThat(WORKFLOW_PROCESS_TASK_REPRESENTATION.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap")).isEqualTo(2);
		assertThat(WORKFLOW_PROCESS_TASK_REPRESENTATION.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap.v01")).isEqualTo(0);
		assertThat(WORKFLOW_PROCESS_TASK_REPRESENTATION.countAll()).isEqualTo(2);
	}
	
	@Test @InSequence(2)
	public void readWorkflowProcessTasksBeforeExecute(){
		assertThat(WORKFLOW_PROCESS_TASK_REPRESENTATION.getByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap")).isNotEmpty();
		assertThat(WORKFLOW_PROCESS_TASK_REPRESENTATION.getByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap.v01")).isEmpty();
		assertThat(WORKFLOW_PROCESS_TASK_REPRESENTATION.getAll()).isNotEmpty();
	}
	
	@Test @InSequence(3)
	public void executeWorkflowProcessTask(){
		Response response = WORKFLOW_PROCESS_TASK_REPRESENTATION.execute("ci.gouv.dgbf.workflow.validation.pap","pap001","charge_etude");
		assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
		response.close();
	}
	
	@Test @InSequence(4)
	public void countWorkflowProcessTaskAfterExecute(){
		assertThat(WORKFLOW_PROCESS_TASK_REPRESENTATION.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap")).isEqualTo(2);
		assertThat(WORKFLOW_PROCESS_TASK_REPRESENTATION.countByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap.v01")).isEqualTo(0);
		assertThat(WORKFLOW_PROCESS_TASK_REPRESENTATION.countAll()).isEqualTo(2);
	}
	
	@Test @InSequence(5)
	public void readWorkflowProcessTaskAfterExecute(){
		assertThat(WORKFLOW_PROCESS_TASK_REPRESENTATION.getByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap")).isNotEmpty();
		assertThat(WORKFLOW_PROCESS_TASK_REPRESENTATION.getByWorkflowCode("ci.gouv.dgbf.workflow.validation.pap.v01")).isEmpty();
		assertThat(WORKFLOW_PROCESS_TASK_REPRESENTATION.getAll()).isNotEmpty();
	}
	/*
	@Test @InSequence(6)
	public void readWorkflowByIdentifierAfterCreate(){
		WorkflowDto workflowDto = WORKFLOW_REPRESENTATION.getByIdentifier(IDENTIFIER);
		assertThat(workflowDto).isNotNull();
		assertThat(workflowDto.getIdentifier()).isEqualTo(IDENTIFIER);
		assertThat(workflowDto.getCode()).isEqualTo("ci.gouv.dgbf.workflow.validation.pap");
		assertThat(workflowDto.getName()).isEqualTo("Validation du PAP");
	}
	
	@Test @InSequence(7)
	public void updateWorkflow(){
		WorkflowDto workflowDto = WORKFLOW_REPRESENTATION.getByIdentifier(IDENTIFIER);
		workflowDto.setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP V01.bpmn2");
		Response response = WORKFLOW_REPRESENTATION.updateOne(workflowDto);
		assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
		response.close();
	}
	
	@Test @InSequence(8)
	public void countWorkflowAfterUpdate(){
		assertThat(WORKFLOW_REPRESENTATION.countAll()).isEqualTo(1);
	}
	
	@Test @InSequence(9)
	public void readWorkflowByCodeAfterUpdate(){
		assertThat(WORKFLOW_REPRESENTATION.getByCode("ci.gouv.dgbf.workflow.validation.pap")).isNull();
		WorkflowDto workflowDto = WORKFLOW_REPRESENTATION.getByCode("ci.gouv.dgbf.workflow.validation.pap.v01");
		assertThat(workflowDto).isNotNull();
		IDENTIFIER = workflowDto.getIdentifier();
		assertThat(workflowDto.getCode()).isEqualTo("ci.gouv.dgbf.workflow.validation.pap.v01");
		assertThat(workflowDto.getName()).isEqualTo("Validation du PAP V01");
	}
	
	@Test @InSequence(10)
	public void readWorkflowByIdentifierAfterUpdate(){
		WorkflowDto workflowDto = WORKFLOW_REPRESENTATION.getByIdentifier(IDENTIFIER);
		assertThat(workflowDto).isNotNull();
		assertThat(workflowDto.getIdentifier()).isEqualTo(IDENTIFIER);
		assertThat(workflowDto.getCode()).isEqualTo("ci.gouv.dgbf.workflow.validation.pap.v01");
		assertThat(workflowDto.getName()).isEqualTo("Validation du PAP V01");
	}
	
	@Test @InSequence(11)
	public void deleteWorkflowByIdentifier(){
		Response response = WORKFLOW_REPRESENTATION.deleteByIdentifier(IDENTIFIER);
		assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
		response.close();
	}
	
	@Test @InSequence(12)
	public void countWorkflowAfterDeleteByIdentifier(){
		assertThat(WORKFLOW_REPRESENTATION.countAll()).isEqualTo(0);
	}
	
	@Test @InSequence(13)
	public void readWorkflowByCodeAfterDeleteByIdentifier(){
		assertThat(WORKFLOW_REPRESENTATION.getByCode("ci.gouv.dgbf.workflow.validation.pap")).isNull();
		assertThat(WORKFLOW_REPRESENTATION.getByCode("ci.gouv.dgbf.workflow.validation.pap.v01")).isNull();
	}
	
	@Test @InSequence(14)
	public void readWorkflowByIdentifierAfterDeleteByIdentifier(){
		assertThat(WORKFLOW_REPRESENTATION.getByIdentifier(IDENTIFIER)).isNull();
	}
	
	@Test @InSequence(15)
	public void createWorkflowByManualPost(){
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(UriBuilder.fromPath(contextPath.toExternalForm()+"workflow/create"));
		WorkflowDto dto = new WorkflowDto();
		dto.setModel(XML)//.setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2")
			;
		target.request().post(Entity.entity(XML,MediaType.APPLICATION_XML),String.class);
		//System.out.println("WorkflowProcessTaskRepresentationImplIntegrationTest.createWorkflowByManualPost() : "+response);
		//assertThat(response.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());
		//response.close();
	}
	
	@Test @InSequence(16)
	public void readWorkflowByCodeAfterCreateByManualPost(){
		assertThat(WORKFLOW_REPRESENTATION.getByCode("C")).isNull();
		WorkflowDto dto = WORKFLOW_REPRESENTATION.getByCode("com.sample.hello");
		assertThat(dto).isNotNull();
		assertThat(dto.getCode()).isEqualTo("com.sample.hello");
		assertThat(dto.getName()).isEqualTo("com.sample.hello");
	}
	
	@Test @InSequence(17)
	public void deleteWorkflowByCodeAfterCreateByManualPost(){
		Response response = WORKFLOW_REPRESENTATION.deleteByCode("com.sample.hello");
		assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
		response.close();
	}
	
	public static String XML = ""
			+ " <workflowDto>"
			+ " <code>C</code>"
			+ " <name>N</name>"
			+ " <model> <![CDATA["
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
			+ " <process processType=\"Private\" isExecutable=\"true\" id=\"com.sample.hello\" name=\"com.sample.hello\" tns:packageName=\"com.sample\" >"
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
			+ "]]></model>"
			+ " </workflowDto>"
			+ "";
	*/
}

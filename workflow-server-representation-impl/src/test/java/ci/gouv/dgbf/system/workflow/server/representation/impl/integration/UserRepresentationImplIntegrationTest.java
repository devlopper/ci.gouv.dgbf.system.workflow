package ci.gouv.dgbf.system.workflow.server.representation.impl.integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.server.representation.test.arquillian.AbstractRepresentationEntityIntegrationTestWithDefaultDeploymentAsSwram;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;

import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowProcessRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowProcessTaskRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.entities.UserDto;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowDto;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowProcessDto;

public class UserRepresentationImplIntegrationTest extends AbstractRepresentationEntityIntegrationTestWithDefaultDeploymentAsSwram<UserDto> {
	private static final long serialVersionUID = 1L;
	
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
	/*
	@Override
	protected void __listenBeforeCallCountIsZero__() throws Exception {
		super.__listenBeforeCallCountIsZero__();
		__inject__(WorkflowRepresentation.class).createOne(new WorkflowDto().setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP.bpmn2"));
		__inject__(WorkflowRepresentation.class).createOne(new WorkflowDto().setModelFromResourceAsStream("/bpmn/withhuman/Validation du PAP V01.bpmn2"));
		
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
	*/
	
	
}

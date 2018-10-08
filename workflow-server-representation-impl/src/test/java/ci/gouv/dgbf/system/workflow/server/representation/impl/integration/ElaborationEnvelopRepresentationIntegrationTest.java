package ci.gouv.dgbf.system.workflow.server.representation.impl.integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.number.NumberHelper;
import org.cyk.utility.server.representation.AbstractEntityCollection;
import org.cyk.utility.server.representation.test.arquillian.AbstractRepresentationArquillianIntegrationTestWithDefaultDeploymentAsSwram;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Assert;
import org.junit.Test;

import ci.gouv.dgbf.system.workflow.server.representation.api.UserRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowProcessRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowProcessTaskRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.api.WorkflowRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.entities.Task;
import ci.gouv.dgbf.system.workflow.server.representation.entities.Tasks;
import ci.gouv.dgbf.system.workflow.server.representation.entities.UserDto;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowDto;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowProcessDto;

public class ElaborationEnvelopRepresentationIntegrationTest extends AbstractRepresentationArquillianIntegrationTestWithDefaultDeploymentAsSwram {
	private static final long serialVersionUID = 1L;
	
	@Test @InSequence(1)
	public void createWorkflow(){
		__inject__(WorkflowRepresentation.class).createOne(new WorkflowDto().setModelFromResourceAsStream("/bpmn/withhuman/ElaborationEnveloppe.bpmn2"));
		
		assertThat(__inject__(WorkflowRepresentation.class).count().getEntity()).isEqualTo(1l);
		assertThat(__inject__(WorkflowProcessRepresentation.class).count().getEntity()).isEqualTo(0l);
		assertThat(__inject__(WorkflowProcessTaskRepresentation.class).count().getEntity()).isEqualTo(0l);
		
		assertThat(__inject__(UserRepresentation.class).count().getEntity()).isEqualTo(4l);
		
		assertUser("dti", null, null);
	}
	
	@Test @InSequence(2)
	public void createProcess_p01(){
		__inject__(WorkflowProcessRepresentation.class).createOne(new WorkflowProcessDto().setWorkflow("ElabEnv").setCode("p01"));
		assertThat(__inject__(WorkflowRepresentation.class).count().getEntity()).isEqualTo(1l);
		assertThat(__inject__(WorkflowProcessRepresentation.class).count().getEntity()).isEqualTo(1l);
		assertThat(__inject__(WorkflowProcessTaskRepresentation.class).count().getEntity()).isEqualTo(1l);
		
		WorkflowProcessDto workflowProcessDto = (WorkflowProcessDto) __inject__(WorkflowProcessRepresentation.class).getByWorkflowCodeByCode("ElabEnv", "p01").getEntity();
		assertThat(workflowProcessDto.getTasks()).isNotNull();
		assertThat(workflowProcessDto.getTasks().getCollection()).hasSize(5);
		assertThat(workflowProcessDto.getTasks().getAt(0).getStatus()).isEqualTo("Reserved");
		assertThat(workflowProcessDto.getTasks().getAt(0).getUsers()).hasSize(1);
		assertThat(workflowProcessDto.getTasks().getAt(0).getUsers().iterator().next().getIdentifier()).isEqualTo("dti");
		
		assertTasks(workflowProcessDto.getTasks(), 5, new Object[][] {
			{"Reserved",1,"dti"}
			,{null,1,"dbe"}
			,{null,1,"dcb"}
			,{null,1,"dti"}
			,{null,1,"dgbf"}
		});
		
		assertUser("dti", new String[] {"Mise en place"}, null);
		assertUser("dbe", null, null);
		assertUser("dcb", null, null);
		assertUser("dgbf", null, null);
	}
	
	@Test @InSequence(3)
	public void executeTask_p01_dti(){
		__inject__(WorkflowProcessTaskRepresentation.class).execute("ElabEnv", "p01", "dti");
		
		WorkflowProcessDto workflowProcessDto = (WorkflowProcessDto) __inject__(WorkflowProcessRepresentation.class).getByWorkflowCodeByCode("ElabEnv", "p01").getEntity();
		assertTasks(workflowProcessDto.getTasks(), 5, new Object[][] {
			{"Completed",1,"dti"}
			,{"Reserved",1,"dbe"}
			,{null,1,"dcb"}
			,{null,1,"dti"}
			,{null,1,"dgbf"}
		});
		
		assertUser("dti", null, new String[] {"Mise en place"});
		assertUser("dbe", new String[] {"Remplissage"}, null);
		assertUser("dcb", null, null);
		assertUser("dgbf", null, null);
	}
	
	@Test @InSequence(4)
	public void executeTask_p01_dbe(){
		__inject__(WorkflowProcessTaskRepresentation.class).execute("ElabEnv", "p01", "dbe");
		
		WorkflowProcessDto workflowProcessDto = (WorkflowProcessDto) __inject__(WorkflowProcessRepresentation.class).getByWorkflowCodeByCode("ElabEnv", "p01").getEntity();
		assertTasks(workflowProcessDto.getTasks(), 5, new Object[][] {
			{"Completed",1,"dti"}
			,{"Completed",1,"dbe"}
			,{"Reserved",1,"dcb"}
			,{null,1,"dti"}
			,{null,1,"dgbf"}
		});
		
		assertUser("dti", null, new String[] {"Mise en place"});
		assertUser("dbe", null, new String[] {"Remplissage"});
		assertUser("dcb", new String[] {"Controlle"}, null);
		assertUser("dgbf", null, null);
	}
	
	@Test @InSequence(5)
	public void executeTask_p01_dcb(){
		__inject__(WorkflowProcessTaskRepresentation.class).execute("ElabEnv", "p01", "dcb");
		
		WorkflowProcessDto workflowProcessDto = (WorkflowProcessDto) __inject__(WorkflowProcessRepresentation.class).getByWorkflowCodeByCode("ElabEnv", "p01").getEntity();
		assertTasks(workflowProcessDto.getTasks(), 5, new Object[][] {
			{"Completed",1,"dti"}
			,{"Completed",1,"dbe"}
			,{"Completed",1,"dcb"}
			,{"Reserved",1,"dti"}
			,{null,1,"dgbf"}
		});
		
		assertUser("dti", new String[] {"Etude"}, new String[] {"Mise en place"});
		assertUser("dbe", null, new String[] {"Remplissage"});
		assertUser("dcb", null, new String[] {"Controlle"});
		assertUser("dgbf", null, null);
	}
	
	@Test @InSequence(6)
	public void executeTask_p01_dti_02(){
		__inject__(WorkflowProcessTaskRepresentation.class).execute("ElabEnv", "p01", "dti");
		
		WorkflowProcessDto workflowProcessDto = (WorkflowProcessDto) __inject__(WorkflowProcessRepresentation.class).getByWorkflowCodeByCode("ElabEnv", "p01").getEntity();
		assertTasks(workflowProcessDto.getTasks(), 5, new Object[][] {
			{"Completed",1,"dti"}
			,{"Completed",1,"dbe"}
			,{"Completed",1,"dcb"}
			,{"Completed",1,"dti"}
			,{"Reserved",1,"dgbf"}
		});
		
		assertUser("dti", null, new String[] {"Mise en place","Etude"});
		assertUser("dbe", null, new String[] {"Remplissage"});
		assertUser("dcb", null, new String[] {"Controlle"});
		assertUser("dgbf", new String[] {"Envoi"}, null);
	}
	
	@Test @InSequence(7)
	public void executeTask_p01_dgbf(){
		__inject__(WorkflowProcessTaskRepresentation.class).execute("ElabEnv", "p01", "dgbf");
		
		assertUser("dti", null, new String[] {"Mise en place","Etude"});
		assertUser("dbe", null, new String[] {"Remplissage"});
		assertUser("dcb", null, new String[] {"Controlle"});
		assertUser("dgbf", null, new String[] {"Envoi"});
	}
	
	@Override
	protected <ENTITY> Class<? extends AbstractEntityCollection<ENTITY>> __getEntityCollectionClass__(Class<ENTITY> arg0) {
		return null;
	}

	/**/
	
	private static void assertTasks(Tasks tasks,Integer expectedSize,Object[]...expectedTasks) {
		assertThat(tasks).isNotNull();
		assertThat(tasks.getCollection()).hasSize(expectedSize);
		if(expectedTasks!=null)
			for(Integer index = 0 ; index < expectedTasks.length ; index = index + 1) {
				assertThat(tasks.getAt(index).getStatus()).isEqualTo(expectedTasks[index][0]);
				assertThat(tasks.getAt(index).getUsers()).hasSize(__inject__(NumberHelper.class).getInteger(expectedTasks[index][1]));
				assertThat(tasks.getAt(index).getUsers().iterator().next().getIdentifier()).isEqualTo(expectedTasks[index][2]);
			}
	}
	
	private void assertUser(String code,String[] expectedNotCompletedTasks,String[] expectedCompletedTasks) {
		UserDto userDto = (UserDto) __inject__(UserRepresentation.class).getOne(code, "business").getEntity();
		assertThat(userDto).isNotNull();
		if(__inject__(ArrayHelper.class).isEmpty(expectedNotCompletedTasks)) {
			assertThat(userDto.getNotCompletedTasks()).isNull();
		}else {
			assertThat(userDto.getNotCompletedTasks()).isNotNull();
			assertThat(userDto.getNotCompletedTasks().getCollection()).isNotNull();
			assertThat(userDto.getNotCompletedTasks().getCollection()).hasSize(expectedNotCompletedTasks.length);
			
			for(String index : expectedNotCompletedTasks) {
				Boolean found = Boolean.FALSE;
				for(Task indexTask : userDto.getNotCompletedTasks().getCollection())
					if(indexTask.getName().equals(index)) {
						found = Boolean.TRUE;
						break;
					}
				Assert.assertTrue(index+" not found in not completed", found);
			}
		}
		
		if(__inject__(ArrayHelper.class).isEmpty(expectedCompletedTasks)) {
			assertThat(userDto.getCompletedTasks()).isNull();
		}else {
			assertThat(userDto.getCompletedTasks()).isNotNull();
			assertThat(userDto.getCompletedTasks().getCollection()).isNotNull();
			assertThat(userDto.getCompletedTasks().getCollection()).hasSize(expectedCompletedTasks.length);
			
			for(String index : expectedCompletedTasks) {
				Boolean found = Boolean.FALSE;
				for(Task indexTask : userDto.getCompletedTasks().getCollection())
					if(indexTask.getName().equals(index)) {
						found = Boolean.TRUE;
						break;
					}
				Assert.assertTrue(index+" not found in completed", found);
			}
		}
	}
	
}

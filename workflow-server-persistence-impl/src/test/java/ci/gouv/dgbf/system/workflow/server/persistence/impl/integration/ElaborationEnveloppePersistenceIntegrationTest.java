package ci.gouv.dgbf.system.workflow.server.persistence.impl.integration;

import javax.inject.Inject;

import org.cyk.utility.number.NumberHelper;
import org.cyk.utility.server.persistence.test.arquillian.AbstractPersistenceArquillianIntegrationTestWithDefaultDeploymentAsSwram;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Assert;
import org.junit.Test;
import org.kie.api.task.model.Status;

import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessTaskLogPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.api.WorkflowProcessTaskPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTask;
import ci.gouv.dgbf.system.workflow.server.persistence.impl.JbpmHelper;

public class ElaborationEnveloppePersistenceIntegrationTest extends AbstractPersistenceArquillianIntegrationTestWithDefaultDeploymentAsSwram {

	private static final long serialVersionUID = 1L;
	
	@Inject private WorkflowPersistence workflowPersistence;
	@Inject private WorkflowProcessPersistence workflowProcessPersistence;
	@Inject private WorkflowProcessTaskPersistence workflowProcessTaskPersistence;
	@Inject private WorkflowProcessTaskLogPersistence workflowProcessTaskLogPersistence;
	@Inject private JbpmHelper jbpmHelper;
	
	@Override
	protected void __listenBeforeCallCountIsZero__() throws Exception {
		super.__listenBeforeCallCountIsZero__();
		userTransaction.begin();
		workflowPersistence.create(new Workflow().setModelFromResourceAsStream("/bpmn/withhuman/ElaborationEnveloppe.bpmn2"));
		userTransaction.commit();
	}
	
	@Test @InSequence(1)
	public void beforeCreateProcessP001(){
		assertTasks(0, 0);
		assertWorkflowProcessTaskByUser("ElabEnv", "p001", "dti", null, null);
		assertWorkflowProcessTaskByUser("ElabEnv", "p001", "dbe", null, null);
		assertWorkflowProcessTaskByUser("ElabEnv", "p001", "dcb", null, null);
		assertWorkflowProcessTaskByUser("ElabEnv", "p001", "dgbf", null, null);
		
	}
	
	@Test @InSequence(2)
	public void createProcessP001(){
		workflowProcessPersistence.create(new WorkflowProcess().setCode("p001").setWorkflow(workflowPersistence.readByCode("ElabEnv")));
	}
	
	@Test @InSequence(3)
	public void afterCreateProcessP001(){
		assertTasks(1, 1);
		assertWorkflowProcessTaskByUser("ElabEnv", "p001", "dti", new Object[][] { {Status.Reserved,1} }, new Object[][] { {Status.Reserved,1} });
		assertWorkflowProcessTaskByUser("ElabEnv", "p001", "dbe", null, null);
		assertWorkflowProcessTaskByUser("ElabEnv", "p001", "dcb", null, null);
		assertWorkflowProcessTaskByUser("ElabEnv", "p001", "dgbf", null, null);
	}
	
	@Test @InSequence(4)
	public void executeProcessP001_dti_01() throws Exception{
		execute("ElabEnv", "p001", "dti");

		assertTasks(2, 2);
		assertWorkflowProcessTaskByUser("ElabEnv", "p001", "dti", new Object[][] { {Status.Completed,1} }, new Object[][] { {Status.Completed,1} });
		assertWorkflowProcessTaskByUser("ElabEnv", "p001", "dbe", new Object[][] { {Status.Reserved,1} }, new Object[][] { {Status.Reserved,1} });
		assertWorkflowProcessTaskByUser("ElabEnv", "p001", "dcb", null, null);
		assertWorkflowProcessTaskByUser("ElabEnv", "p001", "dgbf", null, null);
	}
	
	@Test @InSequence(5)
	public void executeProcessP001_dbe_01() throws Exception{
		execute("ElabEnv", "p001", "dbe");

		assertTasks(3, 3);
		assertWorkflowProcessTaskByUser("ElabEnv", "p001", "dti", new Object[][] { {Status.Completed,1} }, new Object[][] { {Status.Completed,1} });
		assertWorkflowProcessTaskByUser("ElabEnv", "p001", "dbe", new Object[][] { {Status.Completed,1} }, new Object[][] { {Status.Completed,1} });
		assertWorkflowProcessTaskByUser("ElabEnv", "p001", "dcb", new Object[][] { {Status.Reserved,1} }, new Object[][] { {Status.Reserved,1} });
		assertWorkflowProcessTaskByUser("ElabEnv", "p001", "dgbf", null, null);
	}
	
	@Test @InSequence(6)
	public void executeProcessP001_dcb_01() throws Exception{
		execute("ElabEnv", "p001", "dcb");

		assertTasks(4, 4);
		assertWorkflowProcessTaskByUser("ElabEnv", "p001", "dti", new Object[][] { {Status.Completed,1},{Status.Reserved,1} }, new Object[][] { {Status.Completed,1},{Status.Reserved,1} });
		assertWorkflowProcessTaskByUser("ElabEnv", "p001", "dbe", new Object[][] { {Status.Completed,1} }, new Object[][] { {Status.Completed,1} });
		assertWorkflowProcessTaskByUser("ElabEnv", "p001", "dcb", new Object[][] { {Status.Completed,1} }, new Object[][] { {Status.Completed,1} });
		assertWorkflowProcessTaskByUser("ElabEnv", "p001", "dgbf", null, null);
	}
	
	@Test @InSequence(7)
	public void executeProcessP001_dti_02() throws Exception{
		execute("ElabEnv", "p001", "dti");

		assertTasks(5, 5);
		assertWorkflowProcessTaskByUser("ElabEnv", "p001", "dti", new Object[][] { {Status.Completed,2} }, new Object[][] { {Status.Completed,2} });
		assertWorkflowProcessTaskByUser("ElabEnv", "p001", "dbe", new Object[][] { {Status.Completed,1} }, new Object[][] { {Status.Completed,1} });
		assertWorkflowProcessTaskByUser("ElabEnv", "p001", "dcb", new Object[][] { {Status.Completed,1} }, new Object[][] { {Status.Completed,1} });
		assertWorkflowProcessTaskByUser("ElabEnv", "p001", "dgbf", new Object[][] { {Status.Reserved,1} }, new Object[][] { {Status.Reserved,1} });
	}
	
	@Test @InSequence(8)
	public void executeProcessP001_dgbf_01() throws Exception{
		execute("ElabEnv", "p001", "dgbf");

		assertTasks(5, 5);
		assertWorkflowProcessTaskByUser("ElabEnv", "p001", "dti", null, new Object[][] { {Status.Completed,2} });
		assertWorkflowProcessTaskByUser("ElabEnv", "p001", "dbe", null, new Object[][] { {Status.Completed,1} });
		assertWorkflowProcessTaskByUser("ElabEnv", "p001", "dcb", null, new Object[][] { {Status.Completed,1} });
		assertWorkflowProcessTaskByUser("ElabEnv", "p001", "dgbf", null, new Object[][] { {Status.Completed,1} });
	}
		
	/**/
	
	private void assertWorkflowProcessTaskByUser(String workflowCode,String processCode,String userCode,Object[][] expectedNumberOfTasks,Object[][] expectedNumberOfTaskLogs) {
		Long numberOfTasks = __inject__(WorkflowProcessTaskPersistence.class).countByWorkflowCodeByProcessCodeByUserCode(workflowCode,processCode, userCode);
		if(expectedNumberOfTasks==null) {
			Assert.assertEquals(userCode+" has some tasks",new Long(0),numberOfTasks);
		}else {
			Long s = 0l;
			for(Object[] index : expectedNumberOfTasks)
				s = s + __inject__(NumberHelper.class).getLong(index[1]);
			
			Assert.assertEquals(userCode+" has incorrect #tasks",new Long(s),numberOfTasks);
			for(Object[] index : expectedNumberOfTasks)
				Assert.assertEquals("Number of "+index[0]+" tasks of "+userCode+" is incorrect", __inject__(NumberHelper.class).getLong(index[1]), __inject__(WorkflowProcessTaskPersistence.class).countByWorkflowCodeByProcessCodeByUserCodeByStatusCode(workflowCode, processCode, userCode, index[0].toString()));
		}	

		Long numberOfTasksLog = __inject__(WorkflowProcessTaskLogPersistence.class).countByWorkflowCodeByProcessCodeByUserCode(workflowCode,processCode, userCode);
		if(expectedNumberOfTaskLogs==null) {
			Assert.assertEquals(userCode+" has some tasks logged",new Long(0),numberOfTasksLog);
		}else {
			//for(WorkflowProcessTaskLog i : __inject__(WorkflowProcessTaskLogPersistence.class).read())
			//	System.out.println("ElaborationEnveloppePersistenceIntegrationTest.assertWorkflowProcessTaskByUser() : "+i);
			
			Long s = 0l;
			for(Object[] index : expectedNumberOfTaskLogs)
				s = s + __inject__(NumberHelper.class).getLong(index[1]);
			
			Assert.assertEquals(userCode+" has incorrect #tasks logged",new Long(s),numberOfTasksLog);
			for(Object[] index : expectedNumberOfTaskLogs)
				Assert.assertEquals("Number of logged "+index[0]+" tasks of "+userCode+" is incorrect", __inject__(NumberHelper.class).getLong(index[1]), __inject__(WorkflowProcessTaskLogPersistence.class).countByWorkflowCodeByProcessCodeByUserCodeByStatusCodes(workflowCode, processCode, userCode, index[0].toString()));
		}	
	}
	
	private void assertTasks(Object expectedNumberOfTasks,Object expectedNumberOfLoggedTasks) {
		Assert.assertEquals("Number of tasks",__inject__(NumberHelper.class).getLong(expectedNumberOfTasks),workflowProcessTaskLogPersistence.count());
		Assert.assertEquals("Number of tasks logged",__inject__(NumberHelper.class).getLong(expectedNumberOfLoggedTasks),workflowProcessTaskPersistence.count());
	}
	
	private void execute(String workflowCode,String processCode,String userCode) {
		WorkflowProcessTask workflowProcessTask = workflowProcessTaskPersistence.readByWorkflowCodeByProcessCodeByUserCodeByStatusCode(workflowCode, processCode, userCode,Status.Reserved.name()).iterator().next();
		try {
			userTransaction.begin();
			jbpmHelper.getRuntimeEngine().getTaskService().start(workflowProcessTask.getIdentifier(), userCode);
			jbpmHelper.getRuntimeEngine().getTaskService().complete(workflowProcessTask.getIdentifier(), userCode, null);
			userTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}

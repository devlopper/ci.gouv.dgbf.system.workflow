package ci.gouv.dgbf.system.workflow.server.persistence.entities;

import java.io.Serializable;

import org.cyk.utility.server.persistence.jpa.AbstractEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class WorkflowProcessTaskLog extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private String status;
    private String owner;
    
	private WorkflowProcessTask workflowProcessTask;

	public WorkflowProcessTaskLog(String workflowCode,Long workflowProcessIdentifier,Long identifier,String name,String owner,String status,Long workItemId){
		setIdentifier(identifier);
		setName(name);
		setOwner(owner);
		setStatus(status);
		
		setWorkflowProcessTask(new WorkflowProcessTask().setWorkItemIdentifier(workItemId)
				.setWorkflowProcess(new WorkflowProcess().setIdentifier(workflowProcessIdentifier).setWorkflow(new Workflow().setCode(workflowCode))));
	}
	
	@Override
	public String toString() {
		return getIdentifier()+"/"+getName()+"/"+getStatus()+"/"+getOwner();
	}
	
}

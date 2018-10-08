package ci.gouv.dgbf.system.workflow.server.persistence.entities;

import java.io.Serializable;

import org.cyk.utility.server.persistence.jpa.AbstractEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class WorkflowProcessTask extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	/*private Integer priority;
    private String subject;
    private String description;
    private String initiator;*/
    private org.kie.api.task.model.Status status;
    private String owner;
    private Long workItemIdentifier;
    /*
    private Date dueDate;
    
    private String createdBy;
    private Date createdOn;
    private Date activationTime;
    private Date expirationTime;    
	*/
	private WorkflowProcess workflowProcess;

	public WorkflowProcessTask(Long workflowProcessIdentifier,Long identifier,String name,org.kie.api.task.model.TaskData data){
		setWorkflowProcess(new WorkflowProcess().setIdentifier(workflowProcessIdentifier));
		setIdentifier(identifier);
		//setCode(code);
		setName(name);
		if(data!=null){
			setOwner(this.owner = data.getActualOwner() == null ? null : data.getActualOwner().getId());
			setStatus(this.status = data.getStatus());
			setWorkItemIdentifier(data.getWorkItemId());
		}
	}
	
	@Override
	public String toString() {
		return getCode()+"/"+getName()+"/"+getStatus()+"/"+getOwner();
	}
	
}

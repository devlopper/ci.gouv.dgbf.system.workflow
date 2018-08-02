package ci.gouv.dgbf.system.workflow.server.persistence.entities;

import java.io.Serializable;

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
    /*
    private Date dueDate;
    
    private String createdBy;
    private Date createdOn;
    private Date activationTime;
    private Date expirationTime;    
	*/
	private WorkflowProcess workflowProcess;

	public WorkflowProcessTask(Long identifier,String name,org.kie.api.task.model.TaskData data){
		this.identifier = identifier;
		this.name = name;
		if(data!=null){
			this.owner = data.getActualOwner() == null ? null : data.getActualOwner().getId();
			this.status = data.getStatus();	
		}
		
	}
	
}

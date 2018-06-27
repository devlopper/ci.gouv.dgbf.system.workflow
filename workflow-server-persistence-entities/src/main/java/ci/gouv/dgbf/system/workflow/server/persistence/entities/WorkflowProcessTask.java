package ci.gouv.dgbf.system.workflow.server.persistence.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class WorkflowProcessTask extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private Integer priority;
    private String subject;
    private String description;
    private String initiator;
    private org.kie.api.task.model.Status status;
    private String owner;
    
    private Date dueDate;
    
    private String createdBy;
    private Date createdOn;
    private Date activationTime;
    private Date expirationTime;    
	
	private WorkflowProcess workflowProcess;

	public WorkflowProcessTask(Long identifier,String name,String owner,org.kie.api.task.model.Status status){
		this.identifier = identifier;
		this.name = name;
		this.owner = owner;
		this.status = status;
	}
}

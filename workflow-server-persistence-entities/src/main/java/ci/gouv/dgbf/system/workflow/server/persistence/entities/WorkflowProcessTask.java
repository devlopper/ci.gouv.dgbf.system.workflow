package ci.gouv.dgbf.system.workflow.server.persistence.entities;

import java.io.Serializable;
import java.util.Date;

import org.jbpm.services.api.model.UserTaskInstanceDesc;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class WorkflowProcessTask extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private UserTaskInstanceDesc jbpmUserTaskInstanceDesc;
	
	private Long taskId;

	private String status;

	private Date activationTime;

	private String name;

	private String description;

	private Integer priority;

	private String createdBy;

	private Date createdOn;

	private Date dueDate;

	private Long processInstanceIdentifier;

	private String workflowIdentifier;

	private String actualOwner;

}

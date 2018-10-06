package ci.gouv.dgbf.system.workflow.server.persistence.entities;

import java.io.Serializable;

import org.cyk.utility.server.persistence.jpa.AbstractEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class WorkflowProcessNodeLog extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private WorkflowProcess workflowProcess;
    private String taskIdentifier;
    private Long workItemId;
    
	public WorkflowProcessNodeLog(Long identifier,String code,String taskIdentifier,Long workItemId){
		setIdentifier(identifier);
		setCode(code);
		this.taskIdentifier = taskIdentifier;
		this.workItemId = workItemId;
	}
	
	@Override
	public String toString() {
		return getIdentifier()+"/"+getCode()+"/"+taskIdentifier+"/"+workItemId;
	}
}

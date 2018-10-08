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

	public WorkflowProcessTaskLog(Long identifier,String name,String owner,String status){
		this.identifier = identifier;
		this.name = name;
		this.owner = owner;
		this.status = status;
	}
	
	@Override
	public String toString() {
		return getIdentifier()+"/"+getName()+"/"+getStatus()+"/"+getOwner();
	}
	
}

package ci.gouv.dgbf.system.workflow.server.persistence.entities;

import java.io.Serializable;

import org.cyk.utility.server.persistence.jpa.AbstractEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class WorkflowProcessTaskVariable extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private WorkflowProcessTask workflowProcessTask;
    private String name;
    private String value;
    
	public WorkflowProcessTaskVariable(Long identifier,String name,String value){
		this.identifier = identifier;
		this.name = name;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return getIdentifier()+"/"+name+"/"+value;
	}
}

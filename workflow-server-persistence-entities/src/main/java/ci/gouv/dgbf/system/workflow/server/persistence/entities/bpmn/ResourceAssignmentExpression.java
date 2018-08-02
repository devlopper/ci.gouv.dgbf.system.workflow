package ci.gouv.dgbf.system.workflow.server.persistence.entities.bpmn;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResourceAssignmentExpression implements Serializable {
	private static final long serialVersionUID = 1L;

	private String formalExpression;
	
	@Override
	public String toString() {
		return formalExpression;
	}
}

package ci.gouv.dgbf.system.workflow.client.controller.entities;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WorkflowData extends AbstractData implements Serializable {
	private static final long serialVersionUID = -4069694254943976164L;

	private String code;
	
	private String name;
	
	private String model;
}

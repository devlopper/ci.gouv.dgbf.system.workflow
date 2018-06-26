package ci.gouv.dgbf.system.workflow.server.persistence.entities;

import java.io.Serializable;

import org.kie.api.runtime.manager.audit.ProcessInstanceLog;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class WorkflowProcessLog extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private WorkflowProcess workflowProcess;
	
	private ProcessInstanceLog jbpmProcessInstanceLog;
	        

}

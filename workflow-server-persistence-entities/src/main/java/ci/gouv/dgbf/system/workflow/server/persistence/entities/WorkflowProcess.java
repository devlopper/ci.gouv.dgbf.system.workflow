package ci.gouv.dgbf.system.workflow.server.persistence.entities;

import java.io.Serializable;

import org.kie.api.runtime.manager.audit.ProcessInstanceLog;
import org.kie.api.runtime.process.ProcessInstance;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class WorkflowProcess extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String code;
	private Workflow workflow;
	
	private ProcessInstance processInstance;
	private ProcessInstanceLog processInstanceLog;
}

package ci.gouv.dgbf.system.workflow.server.persistence.entities;

import java.io.Serializable;

import org.kie.api.task.model.TaskSummary;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class WorkflowProcessTask extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private TaskSummary taskSummary;
	
}

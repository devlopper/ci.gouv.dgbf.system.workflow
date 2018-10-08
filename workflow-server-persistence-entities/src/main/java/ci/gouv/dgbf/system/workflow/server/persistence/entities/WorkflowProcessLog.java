package ci.gouv.dgbf.system.workflow.server.persistence.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain = true) @NoArgsConstructor
public class WorkflowProcessLog extends AbstractWorkflowProcess implements Serializable {
	private static final long serialVersionUID = 1L;

	private WorkflowProcess workflowProcess;
	
	public WorkflowProcessLog(Long workflowProcessIdentifier,Long identifier,String code,String workflowCode,Date startDate,Date endDate,Long duration,Integer state){
		super(identifier, code, workflowCode, null, startDate, endDate, duration, state);
		setWorkflowProcess(new WorkflowProcess().setIdentifier(workflowProcessIdentifier));
	}
	
}

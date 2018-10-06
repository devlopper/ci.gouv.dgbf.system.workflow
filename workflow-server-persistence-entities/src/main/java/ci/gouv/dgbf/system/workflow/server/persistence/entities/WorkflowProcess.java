package ci.gouv.dgbf.system.workflow.server.persistence.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class WorkflowProcess extends AbstractWorkflowProcess implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public WorkflowProcess(Long identifier,String code,String workflowCode,String workflowModel,Date startDate,Date endDate,Long duration,Integer state){
		super(identifier, code, workflowCode, workflowModel, startDate, endDate, duration, state);
	}
	
	public WorkflowProcess(Long identifier){
		this.identifier = identifier;
	}
	
	@Override
	public WorkflowProcess setIdentifier(Long identifier) {
		return (WorkflowProcess) super.setIdentifier(identifier);
	}
	
	@Override
	public WorkflowProcess setCode(String code) {
		return (WorkflowProcess) super.setCode(code);
	}
	
	@Override
	public WorkflowProcess setWorkflow(Workflow workflow) {
		return (WorkflowProcess) super.setWorkflow(workflow);
	}
	
	/**/

}

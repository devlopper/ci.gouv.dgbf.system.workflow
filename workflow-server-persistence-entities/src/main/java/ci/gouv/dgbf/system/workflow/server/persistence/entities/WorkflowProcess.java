package ci.gouv.dgbf.system.workflow.server.persistence.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class WorkflowProcess extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String code;
	private Integer state;
	
	private Date startDate,endDate;
	private Long duration;
	
	private Date lastReadDate;
	private Date lastModificationDate;
	
	private Workflow workflow;
	
	public WorkflowProcess(Long identifier,String code,String workflowCode,String workflowModel,Date startDate,Date endDate,Long duration,Integer state){
		this.identifier = identifier;
		this.code = code == null ? null : code.substring(code.indexOf(':')+1);
		if(workflowCode!=null){
			this.workflow = new Workflow().setCode(workflowCode).setModel(workflowModel);
		}
		this.startDate = startDate;
		this.endDate = endDate;
		this.duration = duration;
		this.state = state;
	}
	
	public WorkflowProcess(Long identifier){
		this.identifier = identifier;
	}
	
	/**/
	
	public static final String FIELD_WORKFLOW_STATE = "state";
	public static final String FIELD_WORKFLOW_START_DATE = "startDate";
}

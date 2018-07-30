package ci.gouv.dgbf.system.workflow.server.representation.entities;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTask;

@XmlRootElement @lombok.Getter @lombok.Setter @lombok.experimental.Accessors(chain=true) @lombok.NoArgsConstructor
public class WorkflowProcessTaskDto extends AbstractPersistenceEntityDto<WorkflowProcessTask> implements Serializable {
	private static final long serialVersionUID = 1L;

	private String code;
	private String state;
	
	private Long workflowProcessIdentifier;
	
	@Override
	public WorkflowProcessTaskDto setIdentifier(Long identifier) {
		return (WorkflowProcessTaskDto) super.setIdentifier(identifier);
	}
}

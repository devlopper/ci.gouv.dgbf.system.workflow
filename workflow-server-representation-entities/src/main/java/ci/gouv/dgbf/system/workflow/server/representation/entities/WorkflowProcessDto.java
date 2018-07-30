package ci.gouv.dgbf.system.workflow.server.representation.entities;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;

@XmlRootElement @lombok.Getter @lombok.Setter @lombok.experimental.Accessors(chain=true) @lombok.NoArgsConstructor
public class WorkflowProcessDto extends AbstractPersistenceEntityDto<WorkflowProcess> implements Serializable {
	private static final long serialVersionUID = 1L;

	private String code;
	private String state;
	
	private String workflowCode;
	
	@Override
	public WorkflowProcessDto setIdentifier(Long identifier) {
		return (WorkflowProcessDto) super.setIdentifier(identifier);
	}
}

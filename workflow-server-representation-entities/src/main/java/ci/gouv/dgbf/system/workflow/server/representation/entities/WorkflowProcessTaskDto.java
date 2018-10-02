package ci.gouv.dgbf.system.workflow.server.representation.entities;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.server.representation.AbstractEntityFromPersistenceEntityDto;

@XmlRootElement @lombok.Getter @lombok.Setter @lombok.experimental.Accessors(chain=true) @lombok.NoArgsConstructor
public class WorkflowProcessTaskDto extends AbstractEntityFromPersistenceEntityDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private String status;
	private String owner;
	
	private Long workflowProcessIdentifier;
	
}

package ci.gouv.dgbf.system.workflow.server.representation.entities;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.server.representation.AbstractDto;

@XmlRootElement @lombok.Getter @lombok.Setter @lombok.experimental.Accessors(chain=true) @lombok.NoArgsConstructor
public class WorkflowProcessDto extends AbstractDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String code;
	private String state;
	
	private String workflowCode;
	
}

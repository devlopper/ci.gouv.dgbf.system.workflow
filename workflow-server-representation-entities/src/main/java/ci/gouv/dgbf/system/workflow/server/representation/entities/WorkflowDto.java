package ci.gouv.dgbf.system.workflow.server.representation.entities;

import java.io.IOException;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.io.IOUtils;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;

@XmlRootElement @lombok.Getter @lombok.Setter @lombok.experimental.Accessors(chain=true) @lombok.NoArgsConstructor
public class WorkflowDto extends AbstractPersistenceEntityDto<Workflow> implements Serializable {
	private static final long serialVersionUID = 1L;

	private String code;
	private String name;
	private String model;
	
	public WorkflowDto setModelFromResourceAsStream(String name){
		try {
			setModel(new String(IOUtils.toByteArray(getClass().getResourceAsStream(name))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	@Override
	public WorkflowDto setIdentifier(Long identifier) {
		return (WorkflowDto) super.setIdentifier(identifier);
	}
}

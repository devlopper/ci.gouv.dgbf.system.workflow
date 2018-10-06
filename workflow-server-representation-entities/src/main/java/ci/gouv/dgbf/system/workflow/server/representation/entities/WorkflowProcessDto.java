package ci.gouv.dgbf.system.workflow.server.representation.entities;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.server.representation.AbstractEntityFromPersistenceEntityDto;

@XmlRootElement @lombok.Getter @lombok.Setter @lombok.experimental.Accessors(chain=true) @lombok.NoArgsConstructor
public class WorkflowProcessDto extends AbstractEntityFromPersistenceEntityDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String state;
	
	private String workflow;
	private Tasks tasks;
	
	/**/
	
	@Override
	public WorkflowProcessDto setCode(String code) {
		return (WorkflowProcessDto) super.setCode(code);
	}
	
	public Tasks getTasks(Boolean instanciateIfNull) {
		Tasks tasks = getTasks();
		if(tasks == null && Boolean.TRUE.equals(instanciateIfNull))
			setTasks(tasks = new Tasks());
		return tasks;
	}
}

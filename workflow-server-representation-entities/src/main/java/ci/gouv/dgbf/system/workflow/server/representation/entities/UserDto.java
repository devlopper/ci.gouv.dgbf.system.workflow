package ci.gouv.dgbf.system.workflow.server.representation.entities;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.server.representation.AbstractEntityFromPersistenceEntityDto;

@XmlRootElement
@lombok.Getter @lombok.Setter @lombok.experimental.Accessors(chain=true) @lombok.NoArgsConstructor
public class UserDto extends AbstractEntityFromPersistenceEntityDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Tasks notCompletedTasks;
	private Tasks completedTasks;
	
	@Override
	public UserDto setCode(String code) {
		return (UserDto) super.setCode(code);
	}
	
	public Tasks getNotCompletedTasks(Boolean instanciateIfNull) {
		Tasks tasks = getNotCompletedTasks();
		if(tasks == null && Boolean.TRUE.equals(instanciateIfNull))
			setNotCompletedTasks(tasks = new Tasks());
		return tasks;
	}
	
	public Tasks getCompletedTasks(Boolean instanciateIfNull) {
		Tasks tasks = getCompletedTasks();
		if(tasks == null && Boolean.TRUE.equals(instanciateIfNull))
			setCompletedTasks(tasks = new Tasks());
		return tasks;
	}
}

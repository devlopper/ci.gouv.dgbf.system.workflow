package ci.gouv.dgbf.system.workflow.server.persistence.entities.bpmn;

import java.io.Serializable;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Process implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private Collection<UserTask> userTasks;
	
	@XmlAttribute
	public String getId(){
		return id;
	}
	
	@XmlAttribute
	public String getName(){
		return name;
	}
	
	@XmlElement(name="userTask")
	public Collection<UserTask> getUserTasks(){
		return userTasks;
	}
	
	/**/
	
	public String getFirstUserTaskPotentialOwner(){
		return userTasks == null || userTasks.isEmpty() ? null : userTasks.iterator().next().getPotentialOwnerAsString();
	}
	
	/**/
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(id+"/"+name);
		if(userTasks!=null)
			for(UserTask index : userTasks)
				stringBuilder.append("\n\r\t"+index);
		return stringBuilder.toString();
	}
}

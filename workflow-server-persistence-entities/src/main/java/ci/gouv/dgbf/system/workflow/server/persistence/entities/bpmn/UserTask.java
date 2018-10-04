package ci.gouv.dgbf.system.workflow.server.persistence.entities.bpmn;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class UserTask implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private PotentialOwner potentialOwner;
	
	@XmlAttribute
	public String getId(){
		return id;
	}
	
	@XmlAttribute
	public String getName(){
		return name;
	}
	
	/**/
	
	public String getPotentialOwnerResourceAssignmentExpressionFormalExpression(){
		return potentialOwner == null ? null : potentialOwner.getResourceAssignmentExpressionFormalExpression();
	}
	
}

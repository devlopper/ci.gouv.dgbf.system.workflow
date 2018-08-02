package ci.gouv.dgbf.system.workflow.server.persistence.entities.bpmn;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PotentialOwner implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private ResourceAssignmentExpression resourceAssignmentExpression;
	
	@XmlAttribute
	public String getId(){
		return id;
	}
	
	@XmlAttribute
	public String getName(){
		return name;
	}
	
	public String getResourceAssignmentExpressionAsString(){
		return resourceAssignmentExpression == null ? null : resourceAssignmentExpression.getFormalExpression();
	}
	
	@Override
	public String toString() {
		return id+"/"+name+(resourceAssignmentExpression == null ? "" : "/"+resourceAssignmentExpression.toString());
	}
}

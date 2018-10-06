package ci.gouv.dgbf.system.workflow.server.representation.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.collection.CollectionHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Task implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String status;
	private String executedBy;
	private String executionDate;
	
	private Collection<User> users;
	
	@XmlElement(name="user")
	public Collection<User> getUsers(){
		return users;
	}
	
	public Task addUsers(Collection<String> identifiers) {
		if(identifiers!=null && !identifiers.isEmpty()) {
			if(users == null)
				users = new ArrayList<>();
			for(String index : identifiers)
				users.add(new User().setIdentifier(index));
		}
		return this;
	}
	
	public Task addUsers(String...identifiers) {
		return addUsers(DependencyInjection.inject(CollectionHelper.class).instanciate(identifiers));
	}
}

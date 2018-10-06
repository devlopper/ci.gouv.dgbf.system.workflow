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
public class Tasks implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Collection<Task> collection;
	
	@XmlElement(name="task")
	public Collection<Task> getCollection(){
		return collection;
	}
	
	public Tasks add(Task task) {
		if(task!=null) {
			if(collection == null)
				collection = new ArrayList<>();
			collection.add(task);
		}
		return this;
	}
	
	public Tasks add(String name,Collection<String> userIdentifiers) {
		if(name!=null)
			add(new Task().setName(name).addUsers(userIdentifiers));
		return this;
	}
	
	public Task getAt(Integer index) {
		return DependencyInjection.inject(CollectionHelper.class).getElementAt(collection, index);
	}
}

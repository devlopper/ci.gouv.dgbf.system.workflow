package ci.gouv.dgbf.system.workflow.server.persistence.entities;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;

import org.cyk.utility.server.persistence.jpa.AbstractEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
@Access(AccessType.FIELD)
public class User extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**/
	
	@Override
	public User setIdentifier(Long identifier) {
		return (User) super.setIdentifier(identifier);
	}
	
	@Override
	public User setCode(String code) {
		return (User) super.setCode(code);
	}
	
	/**/
	
}

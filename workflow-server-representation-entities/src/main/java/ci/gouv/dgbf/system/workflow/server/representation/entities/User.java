package ci.gouv.dgbf.system.workflow.server.representation.entities;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private String identifier;
	
}

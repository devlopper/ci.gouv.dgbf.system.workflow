package ci.gouv.dgbf.system.workflow.client.controller.entities;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractPage implements Serializable {
	private static final long serialVersionUID = 1L;

	private String title;
	
}

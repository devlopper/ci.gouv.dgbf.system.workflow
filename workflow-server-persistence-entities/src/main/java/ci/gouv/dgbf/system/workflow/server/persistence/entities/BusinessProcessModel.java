package ci.gouv.dgbf.system.workflow.server.persistence.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
@Embeddable @Deprecated
public class BusinessProcessModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(nullable=false,unique=true)
	@NotNull
	private String identifier;
	
	@Column(nullable=false)
	@NotNull
	private String text;
	
}

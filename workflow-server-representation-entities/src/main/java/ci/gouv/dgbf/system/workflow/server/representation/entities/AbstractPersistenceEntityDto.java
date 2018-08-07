package ci.gouv.dgbf.system.workflow.server.representation.entities;

import java.io.Serializable;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractPersistenceEntityDto<ENTITY extends AbstractEntity> extends AbstractDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long identifier;
	
}

package ci.gouv.dgbf.system.workflow.server.representation.impl;

import java.io.Serializable;

import ci.gouv.dgbf.system.workflow.business.api.EntityBusiness;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.AbstractEntity;
import ci.gouv.dgbf.system.workflow.server.representation.api.PersistenceEntityRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.entities.AbstractPersistenceEntityDto;

public abstract class AbstractPersistenceEntityRepresentationImpl<ENTITY extends AbstractEntity,DTO extends AbstractPersistenceEntityDto<ENTITY>> extends AbstractRepresentationImpl implements PersistenceEntityRepresentation<DTO>, Serializable {
	private static final long serialVersionUID = 3272472501607816817L;

	protected abstract EntityBusiness<ENTITY> getBusiness();
	
}

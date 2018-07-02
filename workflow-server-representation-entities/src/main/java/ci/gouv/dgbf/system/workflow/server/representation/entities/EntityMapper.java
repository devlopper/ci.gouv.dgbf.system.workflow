package ci.gouv.dgbf.system.workflow.server.representation.entities;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.AbstractEntity;

public interface EntityMapper<ENTITY extends AbstractEntity,DTO extends AbstractDto> {

	DTO getRepresentationFromPersistence(ENTITY entity);
    
    ENTITY getPersistenceFromRepresentation(DTO dto);
}
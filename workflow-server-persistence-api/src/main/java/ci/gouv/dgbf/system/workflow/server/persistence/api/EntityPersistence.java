package ci.gouv.dgbf.system.workflow.server.persistence.api;

import java.util.Collection;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.AbstractEntity;

public interface EntityPersistence<ENTITY extends AbstractEntity> extends Persistence {

	/* Create */
	EntityPersistence<ENTITY> create(ENTITY entity);
	
	/* Read */
	ENTITY read(Long identifier);
	Collection<ENTITY> readAll();
	
	/* Update */
	EntityPersistence<ENTITY> update(ENTITY entity);
	
	/* Delete */
	EntityPersistence<ENTITY> delete(ENTITY entity);
	
	/* Count */
	Long countAll();
}

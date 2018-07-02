package ci.gouv.dgbf.system.workflow.business.api;

import java.util.Collection;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.AbstractEntity;

public interface EntityBusiness<ENTITY extends AbstractEntity> extends Business {

	/* Create */
	EntityBusiness<ENTITY> create(ENTITY entity);
	
	/* Find */
	ENTITY find(Long identifier);
	Collection<ENTITY> findAll();
	
	/* Update */
	EntityBusiness<ENTITY> update(ENTITY entity);
	
	/* Delete */
	EntityBusiness<ENTITY> delete(ENTITY entity);
	EntityBusiness<ENTITY> deleteByIdentifier(Long identifier);
	
	/* Count */
	Long countAll();

}

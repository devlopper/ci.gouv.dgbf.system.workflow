package ci.gouv.dgbf.system.workflow.server.persistence.impl;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class Producer {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Produces
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
}

package ci.gouv.dgbf.system.workflow.server.persistence.impl;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import org.kie.api.task.UserGroupCallback;

import ci.gouv.dgbf.system.workflow.server.persistence.api.Persistence;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractPersistenceImpl implements Persistence, Serializable {
	private static final long serialVersionUID = 1L;

	@PersistenceUnit protected EntityManagerFactory entityManagerFactory;
	@PersistenceContext protected EntityManager entityManager;
	protected UserGroupCallback userGroupCallback;
	
}

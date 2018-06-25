package ci.gouv.dgbf.system.workflow.server.persistence.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import javax.annotation.PostConstruct;

import ci.gouv.dgbf.system.workflow.server.persistence.api.EntityPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.AbstractEntity;

public abstract class AbstractEntityPersistenceImpl<ENTITY extends AbstractEntity> extends AbstractPersistenceImpl implements EntityPersistence<ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;

	protected Class<ENTITY> entityClass;
	
	@PostConstruct
	public void __listenPostConstruct__(){
		entityClass = __getEntityClass__();
	}
	
	@Override
	public EntityPersistence<ENTITY> create(ENTITY entity) {
		entityManager.persist(entity);
		return this;
	}
	
	@Override
	public ENTITY read(Long identifier) {
		return entityManager.find(entityClass, identifier);
	}
	
	@Override
	public Collection<ENTITY> readAll() {
		return entityManager.createQuery("SELECT tuple FROM "+entityClass.getSimpleName()+" tuple", entityClass).getResultList();
	}
	
	@Override
	public EntityPersistence<ENTITY> update(ENTITY entity) {
		entityManager.merge(entity);
		return this;
	}
	
	@Override
	public EntityPersistence<ENTITY> delete(ENTITY entity) {
		entityManager.remove(entityManager.merge(entity));
		return this;
	}
	
	@Override
	public Long countAll() {
		return entityManager.createQuery("SELECT COUNT(tuple) FROM "+entityClass.getSimpleName()+" tuple", Long.class).getSingleResult();
	}
	
	/**/
	
	@SuppressWarnings("unchecked")
	protected Class<ENTITY> __getEntityClass__(){
		return (Class<ENTITY>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
}

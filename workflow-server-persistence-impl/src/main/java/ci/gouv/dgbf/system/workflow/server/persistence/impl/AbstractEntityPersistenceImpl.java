package ci.gouv.dgbf.system.workflow.server.persistence.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;

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
		Collection<ENTITY> collection = null;
		if(entityClass.getAnnotation(Entity.class)!=null)
			collection = entityManager.createNamedQuery(entityClass.getSimpleName()+".readAll", entityClass).getResultList();
		return collection;
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
		Long count = null;
		if(entityClass.getAnnotation(Entity.class)!=null)
			count = entityManager.createQuery("SELECT COUNT(tuple) FROM "+entityClass.getSimpleName()+" tuple", Long.class).getSingleResult();
		return count;
	}
	
	/**/
	
	@SuppressWarnings("unchecked")
	protected Class<ENTITY> __getEntityClass__(){
		return (Class<ENTITY>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
}

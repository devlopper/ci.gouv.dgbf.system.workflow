package ci.gouv.dgbf.system.workflow.business.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import ci.gouv.dgbf.system.workflow.business.api.EntityBusiness;
import ci.gouv.dgbf.system.workflow.server.persistence.api.EntityPersistence;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.AbstractEntity;

public abstract class AbstractEntityBusinessImpl<ENTITY extends AbstractEntity> extends AbstractBusinessImpl implements EntityBusiness<ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;

	protected Class<ENTITY> entityClass;
	
	@PostConstruct
	public void __listenPostConstruct__(){
		entityClass = __getEntityClass__();
	}
	
	protected abstract EntityPersistence<ENTITY> getPersistence();
	
	@Override @Transactional
	public EntityBusiness<ENTITY> create(ENTITY entity) {
		getPersistence().create(entity);
		return this;
	}
	
	@Override
	public ENTITY find(Long identifier) {
		return getPersistence().read(identifier);
	}
	
	@Override
	public Collection<ENTITY> findAll() {
		return getPersistence().readAll();
	}
	
	@Override
	public EntityBusiness<ENTITY> update(ENTITY entity) {
		getPersistence().update(entity);
		return this;
	}
	
	@Override
	public EntityBusiness<ENTITY> delete(ENTITY entity) {
		getPersistence().delete(entity);
		return this;
	}
	
	@Override
	public Long countAll() {
		return getPersistence().countAll();
	}
	
	/**/
	
	@SuppressWarnings("unchecked")
	protected Class<ENTITY> __getEntityClass__(){
		return (Class<ENTITY>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
}

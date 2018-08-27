package ci.gouv.dgbf.system.workflow.server.business.impl;

import java.io.Serializable;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.AbstractEntity;

@Deprecated
public abstract class AbstractEntityBusinessImpl<ENTITY extends AbstractEntity> extends AbstractBusinessImpl implements /*BusinessEntity<ENTITY>,*/Serializable {
	private static final long serialVersionUID = 1L;
	/*
	protected Class<ENTITY> entityClass;
	
	@PostConstruct
	public void __listenPostConstruct__(){
		entityClass = __getEntityClass__();
	}
	
	protected abstract EntityPersistence<ENTITY> getPersistence();
	
	@Override @Transactional
	public BusinessEntity<ENTITY> create(ENTITY entity) {
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
	
	@Override @Transactional
	public BusinessEntity<ENTITY> update(ENTITY entity) {
		getPersistence().update(entity);
		return this;
	}
	
	@Override @Transactional
	public BusinessEntity<ENTITY> delete(ENTITY entity) {
		getPersistence().delete(entity);
		return this;
	}
	
	@Override @Transactional
	public BusinessEntity<ENTITY> deleteByIdentifier(Long identifier) {
		ENTITY entity = getPersistence().read(identifier);
		if(entity == null){
			//TODO throw an exception
		}else{
			delete(entity);	
		}
		return this;
	}
	
	@Override
	public Long countAll() {
		return getPersistence().countAll();
	}
	
	/**/
	
	/*@SuppressWarnings("unchecked")
	protected Class<ENTITY> __getEntityClass__(){
		return (Class<ENTITY>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}*/
}

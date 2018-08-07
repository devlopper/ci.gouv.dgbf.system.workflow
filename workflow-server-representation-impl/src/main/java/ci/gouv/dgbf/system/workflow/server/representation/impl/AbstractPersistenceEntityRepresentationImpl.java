package ci.gouv.dgbf.system.workflow.server.representation.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.Response;

import ci.gouv.dgbf.system.workflow.business.api.EntityBusiness;
import ci.gouv.dgbf.system.workflow.server.persistence.entities.AbstractEntity;
import ci.gouv.dgbf.system.workflow.server.representation.api.PersistenceEntityRepresentation;
import ci.gouv.dgbf.system.workflow.server.representation.entities.AbstractPersistenceEntityDto;
import ci.gouv.dgbf.system.workflow.server.representation.entities.EntityMapper;

public abstract class AbstractPersistenceEntityRepresentationImpl<ENTITY extends AbstractEntity,DTO extends AbstractPersistenceEntityDto<ENTITY>> extends AbstractRepresentationImpl implements PersistenceEntityRepresentation<DTO>, Serializable {
	private static final long serialVersionUID = 3272472501607816817L;

	@Override
	public Response createOne(DTO dto) {
		getBusiness().create(getPersistenceFromRepresentation(dto));
		return Response.ok().status(Response.Status.CREATED).build();
	}
	
	@Override
	public DTO getByIdentifier(Long identifier) {
		return getRepresentationFromPersistence(getBusiness().find(identifier));
	}

	@Override
	public Collection<DTO> getAll() {
		Collection<DTO> collection = new ArrayList<>();
		for(ENTITY index : getBusiness().findAll())
			collection.add(getRepresentationFromPersistence(index));
		return collection;
	}

	@Override
	public Long countAll() {
		return getBusiness().countAll();
	}
	
	@Override
	public Response updateOne(DTO dto) {
		getBusiness().update(getPersistenceFromRepresentation(dto));
		return Response.ok().status(Response.Status.OK).build();
	}
	
	@Override
	public Response deleteByIdentifier(Long identifier) {
		getBusiness().deleteByIdentifier(identifier);
		return Response.ok().status(Response.Status.OK).build();
	}
	
	/**/
	
	protected ENTITY getPersistenceFromRepresentation(DTO dto){
		return getEntityMapper().getPersistenceFromRepresentation(dto);
	}
	
	protected DTO getRepresentationFromPersistence(ENTITY entity){
		return getEntityMapper().getRepresentationFromPersistence(entity);
	}
	
	protected Collection<DTO> getRepresentationFromPersistence(Collection<ENTITY> entities){
		Collection<DTO> collection = new ArrayList<>();
		for(ENTITY index : entities)
			collection.add(getRepresentationFromPersistence(index));
		return collection;
	}
	
	protected abstract EntityBusiness<ENTITY> getBusiness();
	
	protected abstract EntityMapper<ENTITY,DTO> getEntityMapper();
}

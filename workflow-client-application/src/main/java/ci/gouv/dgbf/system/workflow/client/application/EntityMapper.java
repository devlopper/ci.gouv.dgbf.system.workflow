package ci.gouv.dgbf.system.workflow.client.application;
import org.cyk.utility.server.representation.AbstractEntityFromPersistenceEntityDto;

import ci.gouv.dgbf.system.workflow.client.controller.entities.AbstractData;

public interface EntityMapper<DTO extends AbstractEntityFromPersistenceEntityDto,DATA extends AbstractData> {

	DATA getDataFromRepresentation(DTO dto);
    
    DTO getRepresentationFromData(DATA data);
}
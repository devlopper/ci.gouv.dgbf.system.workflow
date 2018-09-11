package ci.gouv.dgbf.system.workflow.client.application;
import org.cyk.utility.server.representation.AbstractDto;

import ci.gouv.dgbf.system.workflow.client.controller.entities.AbstractData;

public interface EntityMapper<DTO extends AbstractDto,DATA extends AbstractData> {

	DATA getDataFromRepresentation(DTO dto);
    
    DTO getRepresentationFromData(DATA data);
}
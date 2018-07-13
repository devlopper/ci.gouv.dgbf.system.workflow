package ci.gouv.dgbf.system.workflow.client.controller.entities;
import ci.gouv.dgbf.system.workflow.server.representation.entities.AbstractDto;

public interface EntityMapper<DTO extends AbstractDto,DATA extends AbstractData> {

	DATA getDataFromRepresentation(DTO dto);
    
    DTO getRepresentationFromData(DATA data);
}
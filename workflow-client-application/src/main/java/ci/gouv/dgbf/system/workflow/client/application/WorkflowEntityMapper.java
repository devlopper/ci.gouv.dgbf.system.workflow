package ci.gouv.dgbf.system.workflow.client.application;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import ci.gouv.dgbf.system.workflow.client.controller.entities.WorkflowData;
import ci.gouv.dgbf.system.workflow.server.representation.entities.WorkflowDto;

@Mapper
public interface WorkflowEntityMapper extends EntityMapper<WorkflowDto,WorkflowData> {

    WorkflowEntityMapper INSTANCE = Mappers.getMapper(WorkflowEntityMapper.class);

    /*@Mapping(source = "identifier", target = "identifier")
    @Mapping(source = "code", target = "code")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "model", target = "model")
    */
    WorkflowData getDataFromRepresentation(WorkflowDto dto);
    
    /*@Mapping(source = "identifier", target = "identifier")
    @Mapping(source = "code", target = "code")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "model", target = "model")
    */
    WorkflowDto getRepresentationFromData(WorkflowData data);
}
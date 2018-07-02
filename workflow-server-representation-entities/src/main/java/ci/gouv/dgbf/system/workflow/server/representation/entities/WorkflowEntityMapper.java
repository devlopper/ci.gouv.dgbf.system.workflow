package ci.gouv.dgbf.system.workflow.server.representation.entities;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.Workflow;

@Mapper
public interface WorkflowEntityMapper extends EntityMapper<Workflow,WorkflowDto> {

    WorkflowEntityMapper INSTANCE = Mappers.getMapper(WorkflowEntityMapper.class);

    /*@Mapping(source = "identifier", target = "identifier")
    @Mapping(source = "code", target = "code")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "model", target = "model")
    */
    WorkflowDto getRepresentationFromPersistence(Workflow workflow);
    
    /*@Mapping(source = "identifier", target = "identifier")
    @Mapping(source = "code", target = "code")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "model", target = "model")
    */
    Workflow getPersistenceFromRepresentation(WorkflowDto dto);
}
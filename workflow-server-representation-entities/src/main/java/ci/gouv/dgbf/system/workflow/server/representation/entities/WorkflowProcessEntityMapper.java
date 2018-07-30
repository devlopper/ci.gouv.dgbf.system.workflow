package ci.gouv.dgbf.system.workflow.server.representation.entities;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcess;

@Mapper
public interface WorkflowProcessEntityMapper extends EntityMapper<WorkflowProcess,WorkflowProcessDto> {

    WorkflowProcessEntityMapper INSTANCE = Mappers.getMapper(WorkflowProcessEntityMapper.class);

    WorkflowProcessDto getRepresentationFromPersistence(WorkflowProcess workflowProcess);
    
    WorkflowProcess getPersistenceFromRepresentation(WorkflowProcessDto dto);
}
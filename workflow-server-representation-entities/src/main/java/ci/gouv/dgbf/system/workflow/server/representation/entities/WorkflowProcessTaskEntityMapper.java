package ci.gouv.dgbf.system.workflow.server.representation.entities;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.WorkflowProcessTask;

@Mapper
public interface WorkflowProcessTaskEntityMapper extends EntityMapper<WorkflowProcessTask,WorkflowProcessTaskDto> {

    WorkflowProcessTaskEntityMapper INSTANCE = Mappers.getMapper(WorkflowProcessTaskEntityMapper.class);

    WorkflowProcessTaskDto getRepresentationFromPersistence(WorkflowProcessTask workflowProcessTask);
    
    WorkflowProcessTask getPersistenceFromRepresentation(WorkflowProcessTaskDto dto);
}
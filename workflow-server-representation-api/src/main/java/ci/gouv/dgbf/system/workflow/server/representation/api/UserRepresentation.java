package ci.gouv.dgbf.system.workflow.server.representation.api;

import javax.ws.rs.Path;

import org.cyk.utility.server.representation.RepresentationEntity;

import ci.gouv.dgbf.system.workflow.server.persistence.entities.User;
import ci.gouv.dgbf.system.workflow.server.representation.entities.UserDto;
import ci.gouv.dgbf.system.workflow.server.representation.entities.UserDtoCollection;

@Path(UserRepresentation.PATH)
public interface UserRepresentation extends RepresentationEntity<User,UserDto,UserDtoCollection> {
	
	String PATH = "/user";
	
}
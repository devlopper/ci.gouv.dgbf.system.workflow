package ci.gouv.dgbf.system.workflow.server.representation.api;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ci.gouv.dgbf.system.workflow.server.representation.entities.AbstractDto;

public interface PersistenceEntityRepresentation<DTO extends AbstractDto> extends Representation {
	
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_XML)
	Response createOne(DTO dto);

	@GET
	@Path("/get/identifier/{identifier}")
	@Produces({ MediaType.APPLICATION_XML })
	DTO getByIdentifier(@PathParam("identifier") Long identifier);
	
	@GET
	@Path("/get/all")
	@Produces({ MediaType.APPLICATION_XML })
	Collection<DTO> getAll();

	@GET
	@Path("/count")
	@Produces({ MediaType.TEXT_PLAIN })
	Long countAll();
	
	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_XML)
	Response updateOne(DTO dto);
	
	@GET
	@Path("/delete/identifier/{identifier}")
	@Produces({ MediaType.APPLICATION_XML })
	Response deleteByIdentifier(@PathParam("identifier") Long identifier);
	
}
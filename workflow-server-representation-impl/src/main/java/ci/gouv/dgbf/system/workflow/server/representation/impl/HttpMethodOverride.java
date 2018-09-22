package ci.gouv.dgbf.system.workflow.server.representation.impl;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class HttpMethodOverride implements ContainerRequestFilter {
   public void filter(ContainerRequestContext ctx) throws IOException {
     System.out.println("HttpMethodOverride.filter() : "+ctx.getUriInfo().getBaseUri()+" ::: "+ctx.getUriInfo().getPath()+" ::: "+ctx.getUriInfo());
   }
}
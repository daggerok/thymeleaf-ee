package daggerok.mvc;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.InputStream;

import static java.lang.String.format;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("")
@Stateless
public class StaticResourcesResource {

  @GET
  @Path("{path:(webjars|assets)\\/.*}")
  public Response staticResources(@PathParam("path") final String path,
                                  @HeaderParam("Accept-Encoding") final String encoding) {

    final String absolutePath = format("/META-INF/resources/%s", path);
    final InputStream resource = getClass().getClassLoader().getResourceAsStream(absolutePath);

    return null == resource
        ? Response.status(NOT_FOUND).build()
        : Response.ok().encoding(encoding).entity(resource).build();
  }
}

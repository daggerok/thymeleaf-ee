package daggerok.mvc;

import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.Objects;

import static java.lang.String.format;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

//tag::content[]
@Slf4j
@Path("")
@RequestScoped
public class StaticResourcesResource {

  @Inject ServletContext context;

  /**
   * Serving webjar dependencies
   *
   * see: https://www.webjars.org
   */

  @GET
  @Path("{path: ^webjars\\/.*}")
  public Response webjars(@PathParam("path") final String path) {
    log.info("handling static resource: '{}'", path);
    String absolutePath = format("/META-INF/resources/%s", path);
    InputStream resource = getClass().getClassLoader().getResourceAsStream(absolutePath);
    return Objects.isNull(resource)
        ? Response.status(NOT_FOUND).build()
        : Response.ok().entity(resource).build();
  }

  /**
   * Serving static files form folders:
   *
   * /WEB-INF/resources
   * /WEB-INF/static
   * /WEB-INF/public
   * /WEB-INF/assets
   */

  @GET
  @Path("{path: ^(assets|public|static|resources)\\/.*}")
  public Response staticResources(@PathParam("path") final String path) {
    log.info("handling asset: '{}'", path);
    InputStream resource = context.getResourceAsStream(format("/WEB-INF/%s", path));
    return null == resource
        ? Response.status(NOT_FOUND).build()
        : Response.ok().entity(resource).build();
  }
}
//end::content[]

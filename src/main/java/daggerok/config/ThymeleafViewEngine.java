package daggerok.config;

import io.vavr.control.Try;
import org.mvcspec.ozark.engine.ViewEngineBase;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.mvc.engine.ViewEngineContext;
import javax.mvc.engine.ViewEngineException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//tag::content[]
@ApplicationScoped
public class ThymeleafViewEngine extends ViewEngineBase {

  @Inject
  ServletContext servletContext;

  @Inject
  TemplateEngine templateEngine;

  @Override
  public boolean supports(final String view) {
    return !view.contains(".");
  }

  @Override
  public void processView(ViewEngineContext context) throws ViewEngineException {

    HttpServletRequest req = context.getRequest();
    HttpServletResponse res = context.getResponse();

    Try.of(() -> new WebContext(req, res, servletContext, req.getLocale()))
       .andThenTry(webContext -> webContext.setVariables(context.getModels()))
       .andThenTry(() -> req.setAttribute("view", context.getView()))
       .andThenTry(webContext -> templateEngine.process(
               /*default layout*/ "default", webContext, res.getWriter()))
       .getOrElseThrow(ViewEngineException::new);
  }
}
//end::content[]

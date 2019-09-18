package daggerok.thymeleafmvc;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.mvcspec.ozark.engine.ViewEngineBase;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.mvc.engine.ViewEngineContext;
import javax.mvc.engine.ViewEngineException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//tag::content[]
@Slf4j
@ApplicationScoped
public class ThymeleafViewEngine extends ViewEngineBase {

  @Inject ServletContext servletContext;
  TemplateEngine engine;

  @Override
  public boolean supports(final String view) {
    return !view.contains(".");
  }

  @PostConstruct
  public void postConstruct() {
    final ServletContextTemplateResolver resolver = new ServletContextTemplateResolver(servletContext);
    resolver.setPrefix("/WEB-INF/layouts/");
    resolver.setSuffix(".html");
    resolver.setTemplateMode(TemplateMode.HTML);
    resolver.setCacheable(false);
    engine = new TemplateEngine();
    engine.setTemplateResolver(resolver);
  }

  @Override
  public void processView(ViewEngineContext context) throws ViewEngineException {
    Try.run(() -> {

      final HttpServletRequest request = context.getRequest();
      final HttpServletResponse response = context.getResponse();
      final WebContext webContext = new WebContext(request, response, servletContext, request.getLocale());

      webContext.setVariables(context.getModels());
      request.setAttribute("view", context.getView());
      engine.process(/*default layout*/ "default", webContext, response.getWriter());

    }).getOrElseThrow(ViewEngineException::new);
  }
}
//end::content[]

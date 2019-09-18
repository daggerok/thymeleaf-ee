package daggerok.thymeleafmvc;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletContext;
import java.util.Objects;

@Slf4j
//tag::content[]
@ApplicationScoped
public class ThymeleafProducer {

  @Produces
  @Singleton
  private ServletContextTemplateResolver servletContextTemplateResolver(ServletContext servletContext) {
    // log.debug("producing lazy template resolver...");
    val resolver = new ServletContextTemplateResolver(servletContext);
    resolver.setPrefix("/WEB-INF/layouts/");
    resolver.setSuffix(".html");
    resolver.setTemplateMode(TemplateMode.HTML);
    resolver.setCacheable(false);
    return resolver;
  }

  @Produces
  @Singleton
  public TemplateEngine templateEngine(ServletContextTemplateResolver resolver) {
    // log.debug("producing lazy template engine...");
    TemplateEngine templateEngine = new TemplateEngine();
    templateEngine.setTemplateResolver(resolver);
    return templateEngine;
  }
}
//end::content[]

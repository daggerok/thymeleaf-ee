package daggerok.config;

import lombok.val;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.servlet.ServletContext;

// @lombok.extern.slf4j.Slf4j
//tag::content[]
@ApplicationScoped
public class ThymeleafProducer {

  @Produces
  @Singleton
  ServletContextTemplateResolver servletContextTemplateResolver(ServletContext servletContext) {
    // log.debug("producing lazy template resolver...");
    val resolver = new ServletContextTemplateResolver(servletContext);
    resolver.setTemplateMode(TemplateMode.HTML);
    resolver.setPrefix("/WEB-INF/layouts/");
    resolver.setSuffix(".html");
    resolver.setCacheable(false);
    return resolver;
  }

  @Produces
  @Singleton
  TemplateEngine templateEngine(ServletContextTemplateResolver resolver) {
    // log.debug("producing lazy template engine...");
    TemplateEngine templateEngine = new TemplateEngine();
    templateEngine.setTemplateResolver(resolver);
    return templateEngine;
  }
}
//end::content[]

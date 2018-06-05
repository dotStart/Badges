package tv.dotstart.badge.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.RequestToViewNameTranslator;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * Provides customizations for the template engine which is used to generate SVGs.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
@Configuration
@EnableConfigurationProperties(ThymeleafProperties.class)
public class TemplateConfiguration {

  private final ApplicationContext applicationContext;
  private final ThymeleafProperties thymeleafProperties;

  @Autowired
  public TemplateConfiguration(
      @NonNull ApplicationContext applicationContext,
      @NonNull ThymeleafProperties thymeleafProperties) {
    this.applicationContext = applicationContext;
    this.thymeleafProperties = thymeleafProperties;
  }

  /**
   * <p>Provides a view name translator which always returns "badge".</p>
   *
   * <p>View name translators are only invoked when the no template name has been explicitly given
   * by the invoked controller. In these cases, the badge XML template will be used in order to
   * permit returning of {@link tv.dotstart.badge.badge.Badge} from controller methods.</p>
   *
   * @return a view name translator.
   */
  @Bean
  @NonNull
  public RequestToViewNameTranslator viewNameTranslator() {
    return request -> {
      return "badge"; // only ever used for badge related requests
    };
  }

  /**
   * Provides an additional template resolver which is capable of rendering SVG based templates.
   *
   * @return a template resolver.
   */
  @Bean
  @NonNull
  public SpringResourceTemplateResolver svgTemplateResolver() {
    var resolver = new SpringResourceTemplateResolver();
    resolver.setApplicationContext(this.applicationContext);
    resolver.setPrefix("classpath:/templates/");
    resolver.setSuffix(".xml");
    resolver.setCharacterEncoding("UTF-8");
    resolver.setTemplateMode(TemplateMode.XML);
    resolver.setCacheable(this.thymeleafProperties.isCache());
    resolver.setCheckExistence(true);
    return resolver;
  }

  /**
   * Provides a view resolver which correctly enforces the SVG content type.
   *
   * @param engine a template engine instance.
   * @return an SVG compatible view resolver.
   */
  @Bean
  @NonNull
  public ThymeleafViewResolver svgViewResolver(@NonNull ISpringTemplateEngine engine) {
    var resolver = new ThymeleafViewResolver();
    resolver.setTemplateEngine(engine);
    resolver.setCharacterEncoding("UTF-8");
    resolver.setContentType("image/svg+xml");
    resolver.setViewNames(new String[]{"badge"});
    resolver.setForceContentType(true);
    resolver.setOrder(Ordered.HIGHEST_PRECEDENCE);
    resolver.setCache(this.thymeleafProperties.isCache());
    return resolver;
  }
}

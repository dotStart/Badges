package tv.dotstart.badge.configuration;

import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

/**
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

  /**
   * {@inheritDoc}
   */
  @Override
  public void addInterceptors(@NonNull InterceptorRegistry registry) {
    var cacheInterceptor = new WebContentInterceptor();
    cacheInterceptor.setCacheControl(
        CacheControl.maxAge(1, TimeUnit.HOURS)
            .cachePublic()
            .noTransform()
    );
    registry.addInterceptor(cacheInterceptor);
  }
}

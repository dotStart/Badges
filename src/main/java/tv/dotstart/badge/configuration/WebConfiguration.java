/*
 * Copyright 2018 Johannes Donath <johannesd@torchmind.com>
 * and other copyright owners as documented in the project's IP log.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tv.dotstart.badge.configuration;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

/**
 * Configures the Spring Web components to improve caching and serve static templates to the user
 * where required.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

  private final Duration dynamicCacheDuration;
  private final Duration staticCacheDuration;

  public WebConfiguration(
      @NonNull @Value("${badge.cache.dynamic:PT1H}") String dynamicCacheDuration,
      @NonNull @Value("${badge.cache.static:P3D}") String staticCacheDuration) {
    this.dynamicCacheDuration = Duration.parse(dynamicCacheDuration);
    this.staticCacheDuration = Duration.parse(staticCacheDuration);

    var logger = LogManager.getFormatterLogger(WebConfiguration.class);
    logger.info("Dynamic cache duration: %s", this.dynamicCacheDuration);
    logger.info("Static cache duration: %s", this.staticCacheDuration);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addInterceptors(@NonNull InterceptorRegistry registry) {
    var cacheInterceptor = new WebContentInterceptor();

    // fallback value
    cacheInterceptor.setCacheControl(
        CacheControl.maxAge(this.dynamicCacheDuration.toSeconds(), TimeUnit.SECONDS)
            .cachePublic()
            .noTransform()
    );

    // custom badges
    cacheInterceptor.addCacheMapping(
        CacheControl.maxAge(this.staticCacheDuration.toSeconds(), TimeUnit.SECONDS)
            .cachePublic()
            .noTransform(),
        "/custom/**"
    );

    registry.addInterceptor(cacheInterceptor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("index");
  }
}

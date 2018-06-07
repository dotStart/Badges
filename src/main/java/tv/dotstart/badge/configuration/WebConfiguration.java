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

import java.util.concurrent.TimeUnit;
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

  /**
   * {@inheritDoc}
   */
  @Override
  public void addInterceptors(@NonNull InterceptorRegistry registry) {
    var cacheInterceptor = new WebContentInterceptor();

    // fallback value
    cacheInterceptor.setCacheControl(
        CacheControl.maxAge(1, TimeUnit.HOURS)
            .cachePublic()
            .noTransform()
    );

    // custom badges
    cacheInterceptor.addCacheMapping(
        CacheControl.maxAge(3, TimeUnit.DAYS)
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

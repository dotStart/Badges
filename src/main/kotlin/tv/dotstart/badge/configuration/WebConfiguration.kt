/*
 * Copyright 2020 Johannes Donath <johannesd@torchmind.com>
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
package tv.dotstart.badge.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import tv.dotstart.badge.support.http.SVGHttpMessageEncoder


/**
 * Customizes some minor aspects of the webflux runtime.
 *
 * @author [Johannes Donath](mailto:johannesd@torchmind.com)
 * @date 06/05/2020
 */
@Configuration
class WebConfiguration : WebFluxConfigurer {

  @Bean
  fun staticRouter(
      @Value("classpath:/static/index.html") index: Resource) = router {
    GET("/") { _ -> ServerResponse.ok().bodyValue(index) }
  }

  override fun configureHttpMessageCodecs(configurer: ServerCodecConfigurer) {
    configurer.customCodecs()
        .register(SVGHttpMessageEncoder())
  }
}

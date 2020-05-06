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
package tv.dotstart.badge.controller.v1

import org.springframework.boot.info.BuildProperties
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import tv.dotstart.badge.model.v1.SystemHealth

/**
 * Provides basic system metadata such as application health and versioning.
 *
 * @author [Johannes Donath](mailto:johannesd@torchmind.com)
 * @date 06/05/2020
 */
@RestController
@RequestMapping("/v1")
class SystemController(private val buildProperties: BuildProperties?) {

  // at the moment we always return OK since there is no complex health check logic available
  @RequestMapping("/sys/health")
  fun health() =
      Mono.just(SystemHealth(
          SystemHealth.Status.OK,
          this.buildProperties?.version ?: "unknown"
      ))
}

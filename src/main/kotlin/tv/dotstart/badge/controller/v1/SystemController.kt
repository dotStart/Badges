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
import org.springframework.boot.info.GitProperties
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import tv.dotstart.badge.model.v1.SystemHealth
import tv.dotstart.badge.model.v1.SystemTraffic
import tv.dotstart.badge.service.connector.Connector
import tv.dotstart.badge.service.health.Health
import tv.dotstart.badge.service.health.HealthCheckManager

/**
 * Provides basic system metadata such as application health and versioning.
 *
 * @author [Johannes Donath](mailto:johannesd@torchmind.com)
 * @date 06/05/2020
 */
@RestController
@RequestMapping("/v1")
class SystemController(
    private val buildProperties: BuildProperties?,
    private val gitProperties: GitProperties?,
    private val healthCheckManager: HealthCheckManager,
    private val connectors: List<Connector>) {

  fun getVersion() = buildString {
    append(buildProperties?.version ?: "unknown")
    append("+")
    append(gitProperties?.shortCommitId?.let { "git-$it" } ?: "dev")
  }

  @GetMapping("/sys/health")
  fun health() = Mono.just(SystemHealth(
      this.getVersion(),
      this.gitProperties?.commitId,
      this.healthCheckManager.health,
      this.healthCheckManager.checkStatus))
      .map {
        val statusCode = when (it.status) {
          Health.OK -> HttpStatus.OK
          Health.DEGRADED -> HttpStatus.TOO_MANY_REQUESTS
          Health.FAILED -> HttpStatus.SERVICE_UNAVAILABLE
        }

        ResponseEntity
            .status(statusCode)
            .body(it)
      }

  @GetMapping("/sys/traffic")
  fun traffic() =
      Flux.merge(
          this.connectors
              .map {
                it.getRateLimitUsage()
                    .map { usage -> it to usage }
              })
          .collectList()
          .map {
            val connectors = it
                .map { (connector, usage) ->
                  SystemTraffic.ConnectorTraffic(connector.name, connector.rateLimit, usage)
                }

            SystemTraffic(connectors)
          }
          .toMono()
}

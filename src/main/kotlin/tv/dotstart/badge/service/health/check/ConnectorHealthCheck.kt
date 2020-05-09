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
package tv.dotstart.badge.service.health.check

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import tv.dotstart.badge.service.Connector
import tv.dotstart.badge.service.health.Health

/**
 * Provides a health check for every connector within the system.
 *
 * @author [Johannes Donath](mailto:johannesd@torchmind.com)
 * @date 09/05/2020
 */
class ConnectorHealthCheck private constructor(private val connector: Connector) : HealthCheck {

  override val id = "connector.${connector.name}"

  override fun getState(): Mono<Health> {
    val limit = this.connector.rateLimit ?: return Mono.just(Health.OK)
    val degradationLimit = limit * 0.75

    return this.connector.getRateLimitUsage()
        .map {
          when {
            it >= limit -> Health.FAILED
            it >= degradationLimit -> Health.DEGRADED
            else -> Health.OK
          }
        }
  }

  @Component
  class Factory(private val connectors: List<Connector>) : HealthCheck.Factory {

    override fun create() = this.connectors
        .map(::ConnectorHealthCheck)
  }
}

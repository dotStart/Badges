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
package tv.dotstart.badge.service.health

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import tv.dotstart.badge.service.health.check.HealthCheck
import tv.dotstart.badge.util.logger

/**
 * Provides an aggregator for health checks within the application context.
 *
 * @author [Johannes Donath](mailto:johannesd@torchmind.com)
 * @date 09/05/2020
 */
@Service
class HealthCheckManager(checks: List<HealthCheck>, factories: List<HealthCheck.Factory>) {

  private val checks = checks + factories.flatMap(
      HealthCheck.Factory::create)

  final var health: Health = Health.OK
    private set
  final var checkStatus: Map<String, Health> = emptyMap()
    private set

  @Scheduled(initialDelay = 0, fixedRateString = "\${badges.health.check-interval:10000}")
  fun update() {
    logger<HealthCheckManager>().debug("Updating system health")

    val checkStatus = Flux.merge(
        this.checks
            .map {
              it.getState()
                  .map { state -> it.id to state }
            })
        .buffer()
        .blockLast()
        ?.toMap() ?: emptyMap()
    val health = checkStatus.values.getStatus()

    this.health = health
    this.checkStatus = checkStatus
  }
}

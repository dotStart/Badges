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

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import tv.dotstart.badge.configuration.properties.GitHubProperties
import tv.dotstart.badge.service.github.GitHub
import tv.dotstart.badge.service.rate.RedisReactiveAtomicCounter
import tv.dotstart.badge.util.logger
import java.time.Clock
import java.time.Instant
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit

/**
 * Configures GitHub connectivity.
 *
 * @author [Johannes Donath](mailto:johannesd@torchmind.com)
 * @date 06/05/2020
 */
@Configuration
@EnableConfigurationProperties(GitHubProperties::class)
@ConditionalOnProperty("badges.connector.github.enabled", matchIfMissing = true)
class GitHubConfiguration(private val properties: GitHubProperties) {

  @Bean
  fun gitHub(redisTemplate: ReactiveStringRedisTemplate): GitHub {
    logger<GitHubConfiguration>()
        .info("Enabling GitHub connector with clientId ${properties.clientId}")

    val counter = RedisReactiveAtomicCounter(
        redisTemplate,
        {
          val time = OffsetDateTime.now(Clock.systemUTC())
          "rate_github_${time.hour}"
        },
        {
          Instant.now()
              .truncatedTo(ChronoUnit.HOURS)
              .plusSeconds(3600)
        }
    )

    return GitHub(this.properties.clientId,
                  this.properties.clientSecret,
                  this.properties.baseUrl,
                  counter)
  }
}

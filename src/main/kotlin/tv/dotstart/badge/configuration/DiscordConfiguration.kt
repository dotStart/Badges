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
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tv.dotstart.badge.service.connector.discord.Discord
import tv.dotstart.badge.service.counter.ReactiveCounterProvider
import java.time.Duration

/**
 * @author [Johannes Donath](mailto:johannesd@torchmind.com)
 * @date 09/05/2020
 */
@Configuration
@ConditionalOnProperty("badges.connector.discord.enabled", matchIfMissing = true)
class DiscordConfiguration {

  @Bean
  fun discord(counterProvider: ReactiveCounterProvider): Discord {
    val counter = counterProvider.create("discord_traffic", Duration.ofHours(1))

    return Discord(counter)
  }
}

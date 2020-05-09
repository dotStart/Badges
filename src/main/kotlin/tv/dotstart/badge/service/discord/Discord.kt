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
package tv.dotstart.badge.service.discord

import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import tv.dotstart.badge.service.Connector
import tv.dotstart.badge.service.discord.model.Widget
import tv.dotstart.badge.service.counter.ReactiveCounter

/**
 * Provides access to aspects of the Discord API.
 *
 * @author [Johannes Donath](mailto:johannesd@torchmind.com)
 * @date 09/05/2020
 */
class Discord(private val counter: ReactiveCounter) : Connector {

  private val client = WebClient.builder()
      .baseUrl("https://discordapp.com/api")
      .defaultHeaders { headers ->
        headers["Accept"] = "application/json"
      }
      .build()

  override val name = "discord"
  override val rateLimit = null
  override fun getRateLimitUsage() = this.counter.get()

  /**
   * Retrieves the contents of the server widget (if enabled).
   */
  fun getWidget(serverId: String) = this.counter.increment()
      .then(this.client.get()
                .uri("/guilds/{serverId}/widget.json", serverId)
                .retrieve()
                .bodyToMono<Widget>())
}

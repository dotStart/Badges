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
package tv.dotstart.badge.controller.v1.badges.discord

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tv.dotstart.badge.service.badge.annotation.BadgeCategory
import tv.dotstart.badge.service.badge.annotation.BadgeMapping
import tv.dotstart.badge.service.cache.CacheProvider
import tv.dotstart.badge.service.discord.Discord
import tv.dotstart.badge.service.discord.model.Widget
import tv.dotstart.badge.util.badge

/**
 * @author [Johannes Donath](mailto:johannesd@torchmind.com)
 * @date 09/05/2020
 */
@RestController
@BadgeCategory(
    "discord.widget",
    ["chat"],
    [
      BadgeCategory.DefaultValue("serverId", "252100053093711872")
    ]
)
@RequestMapping("/v1/badge/discord/widget/{serverId}")
class DiscordWidgetBadgeController(
    private val discord: Discord,
    cacheProvider: CacheProvider) {

  private val cache = cacheProvider.create("discord_widget", Widget::class)

  fun getWidget(serverId: String) =
      this.cache[serverId, this.discord.getWidget(serverId)]

  @BadgeMapping("/name")
  fun name(@PathVariable serverId: String) = this.getWidget(serverId)
      .map { badge("discord", it.name, brandColor) }

  @BadgeMapping("/presence")
  fun presence(@PathVariable serverId: String) = this.getWidget(serverId)
      .map { badge("online users", it.presenceCount.toString(), brandColor) }
}

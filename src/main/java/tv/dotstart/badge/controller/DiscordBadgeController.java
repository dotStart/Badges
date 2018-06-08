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
package tv.dotstart.badge.controller;

import java.util.concurrent.Callable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tv.dotstart.badge.badge.Badge;
import tv.dotstart.badge.service.discord.Discord;

/**
 * Provides badges which display dynamic information from an arbitrary Discord server.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
@Controller
@RequestMapping("/discord")
@ConditionalOnBean(Discord.class)
public class DiscordBadgeController {

  private final Discord api;

  public DiscordBadgeController(@NonNull Discord api) {
    this.api = api;
  }

  /**
   * Creates a badge which displays the total amount of online members.
   *
   * @param guildId an arbitrary guild identifier.
   * @return a badge.
   */
  @NonNull
  @ModelAttribute("badge")
  @RequestMapping("/guild/{guildId}/members")
  public Callable<Badge> onlineMembers(@NonNull @PathVariable("guildId") String guildId) {
    return () -> Badge.create(
        "discord",
        this.api.getWidget(guildId),
        (widget) -> widget.getMembers().size() + " online",
        "no such server"
    );
  }

  /**
   * Creates a badges which displays the total amount of members in voice channels.
   *
   * @param guildId an arbitrary guild identifier.
   * @return a badge.
   */
  @NonNull
  @ModelAttribute("badge")
  @RequestMapping("/guild/{guildId}/voice")
  public Callable<Badge> voiceMembers(@NonNull @PathVariable("guildId") String guildId) {
    return () -> Badge.create(
        "discord",
        this.api.getWidget(guildId),
        (widget) -> widget.getMembers().stream()
            .filter((member) -> member.getCurrentChannelId() != null)
            .count() + " talking",
        "no such server"
    );
  }
}

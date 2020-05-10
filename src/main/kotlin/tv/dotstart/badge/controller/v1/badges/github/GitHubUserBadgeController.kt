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
package tv.dotstart.badge.controller.v1.badges.github

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono
import tv.dotstart.badge.configuration.properties.annotations.ConditionalOnGitHubConnector
import tv.dotstart.badge.controller.v1.badges.github.error.NoSuchUserException
import tv.dotstart.badge.service.badge.annotation.BadgeCategory
import tv.dotstart.badge.service.badge.annotation.BadgeMapping
import tv.dotstart.badge.service.cache.CacheProvider
import tv.dotstart.badge.service.connector.github.GitHub
import tv.dotstart.badge.service.connector.github.model.User
import tv.dotstart.badge.util.Color
import tv.dotstart.badge.util.badge

/**
 * Provides GitHub user badges.
 *
 * @author [Johannes Donath](mailto:johannesd@torchmind.com)
 * @date 06/05/2020
 */
@RestController
@ConditionalOnGitHubConnector
@BadgeCategory(
    "github.user",
    ["user", "source"],
    [
      BadgeCategory.DefaultValue("username", "dotStart")
    ]
)
@RequestMapping("/v1/badge/github/user/{username}")
class GitHubUserBadgeController(
    private val github: GitHub,
    cache: CacheProvider) {

  private val userCache = cache.create("github_user", User::class)

  private fun getUser(username: String) =
      this.userCache[username, this.github.getUser(username)]
          .onErrorMap(WebClientResponseException.NotFound::class.java) {
            NoSuchUserException("No such user: $username", it)
          }

  @BadgeMapping("/name")
  fun name(@PathVariable username: String) =
      this.getUser(username)
          .flatMap { Mono.justOrEmpty(it.name) }
          .map { badge("name", it, brandColor) }
          .switchIfEmpty(Mono.just(badge("name", "unknown", Color.FALLBACK)))

  @BadgeMapping("/company")
  fun company(@PathVariable username: String) =
      this.getUser(username)
          .flatMap { Mono.justOrEmpty(it.company) }
          .map { badge("company", it, brandColor) }
          .switchIfEmpty(Mono.just(badge("company", "unknown", Color.FALLBACK)))

  @BadgeMapping("/location")
  fun location(@PathVariable username: String) =
      this.getUser(username)
          .flatMap { Mono.justOrEmpty(it.location) }
          .map { badge("location", it, brandColor) }
          .switchIfEmpty(Mono.just(badge("location", "unknown", Color.FALLBACK)))

  @BadgeMapping("/hireable")
  fun hireable(@PathVariable username: String) =
      this.getUser(username)
          .map { badge("hireable", it.hireable) }

  @BadgeMapping("/repositories")
  fun publicRepos(@PathVariable username: String) =
      this.getUser(username)
          .map { badge("repositories", it.publicRepos.toString(), brandColor) }

  @BadgeMapping("/gists")
  fun publicGists(@PathVariable username: String) =
      this.getUser(username)
          .map { badge("gists", it.publicGists.toString(), brandColor) }

  @BadgeMapping("/followers")
  fun followers(@PathVariable username: String) =
      this.getUser(username)
          .map { badge("followers", it.followers.toString(), brandColor) }

  @BadgeMapping("/following")
  fun following(@PathVariable username: String) =
      this.getUser(username)
          .map { badge("following", it.following.toString(), brandColor) }
}

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
import tv.dotstart.badge.controller.v1.badges.github.error.NoSuchOrganizationException
import tv.dotstart.badge.service.badge.annotation.BadgeCategory
import tv.dotstart.badge.service.badge.annotation.BadgeMapping
import tv.dotstart.badge.service.cache.CacheProvider
import tv.dotstart.badge.service.connector.github.GitHub
import tv.dotstart.badge.service.connector.github.model.Organization
import tv.dotstart.badge.util.Color
import tv.dotstart.badge.util.badge
import tv.dotstart.badge.util.passedTimeSince

/**
 * @author [Johannes Donath](mailto:johannesd@torchmind.com)
 * @date 09/05/2020
 */
@RestController
@ConditionalOnGitHubConnector
@BadgeCategory(
    "github.organization",
    ["organization", "source"],
    [
      BadgeCategory.DefaultValue("username", "GitHub")
    ]
)
@RequestMapping("/v1/badge/github/organization/{username}")
class GitHubOrganizationBadgeController(
    private val gitHub: GitHub,
    cacheProvider: CacheProvider) {

  private val cache = cacheProvider.create("github_organization", Organization::class)

  fun getOrganization(username: String) =
      this.cache[username, this.gitHub.getOrganization(username)]
          .onErrorMap(WebClientResponseException.NotFound::class.java) {
            NoSuchOrganizationException("No such repository: $username", it)
          }

  @BadgeMapping("/location")
  fun location(@PathVariable username: String) = this.getOrganization(username)
      .flatMap { Mono.justOrEmpty(it.location) }
      .map { badge("company", it, brandColor) }
      .switchIfEmpty(Mono.just(badge("company", "unknown", Color.FALLBACK)))

  @BadgeMapping("/verified")
  fun verified(@PathVariable username: String) = this.getOrganization(username)
      .map { badge("verified", it.isVerified) }

  @BadgeMapping("/repositories")
  fun publicRepos(@PathVariable username: String) = this.getOrganization(username)
      .map { badge("repositories", it.publicRepos.toString(), brandColor) }

  @BadgeMapping("/created")
  fun createdAt(@PathVariable username: String) = this.getOrganization(username)
      .map(Organization::createdAt)
      .map { badge("created", "${it.passedTimeSince} ago", brandColor) }
}

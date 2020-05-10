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
import tv.dotstart.badge.controller.error.github.NoSuchRepositoryException
import tv.dotstart.badge.service.badge.annotation.BadgeCategory
import tv.dotstart.badge.service.badge.annotation.BadgeMapping
import tv.dotstart.badge.service.cache.CacheProvider
import tv.dotstart.badge.service.connector.github.GitHub
import tv.dotstart.badge.service.connector.github.model.Repository
import tv.dotstart.badge.util.Color
import tv.dotstart.badge.util.badge
import tv.dotstart.badge.util.passedTimeSince

/**
 * @author [Johannes Donath](mailto:johannesd@torchmind.com)
 * @date 08/05/2020
 */
@RestController
@ConditionalOnGitHubConnector
@BadgeCategory(
    "github.repo",
    ["source"],
    arrayOf(
        BadgeCategory.DefaultValue("owner", "dotStart"),
        BadgeCategory.DefaultValue("name", "Beacon")
    )
)
@RequestMapping("/v1/badge/github/repo/{owner}/{name}")
class GitHubRepositoryBadgeController(private val gitHub: GitHub,
                                      cache: CacheProvider) {

  private val repoCache = cache.create("github_repo", Repository::class)

  fun getRepo(owner: String, name: String) =
      this.repoCache["${owner}_$name", this.gitHub.getRepository(owner, name)]
          .onErrorMap(WebClientResponseException.NotFound::class.java) {
            NoSuchRepositoryException("No such repository: $owner/$name", it)
          }

  @BadgeMapping("/fork")
  fun fork(@PathVariable owner: String, @PathVariable name: String) =
      this.getRepo(owner, name)
          .map { badge("fork", it.fork) }

  @BadgeMapping("/language")
  fun language(@PathVariable owner: String, @PathVariable name: String) =
      this.getRepo(owner, name)
          .flatMap { Mono.justOrEmpty(it.language) }
          .map { badge("language", it, Color.byHash(it)) }
          .switchIfEmpty(Mono.just(badge("language", "unknown", Color.FALLBACK)))

  @BadgeMapping("/forks")
  fun forks(@PathVariable owner: String, @PathVariable name: String) =
      this.getRepo(owner, name)
          .map { badge("forks", it.forksCount.toString(), brandColor) }

  @BadgeMapping("/stars")
  fun stargazers(@PathVariable owner: String, @PathVariable name: String) =
      this.getRepo(owner, name)
          .map { badge("stars", it.stargazersCount.toString(), brandColor) }

  @BadgeMapping("/watchers")
  fun watchers(@PathVariable owner: String, @PathVariable name: String) =
      this.getRepo(owner, name)
          .map { badge("watchers", it.watchersCount.toString(), brandColor) }

  @BadgeMapping("/issues")
  fun openIssuesCount(@PathVariable owner: String, @PathVariable name: String) =
      this.getRepo(owner, name)
          .map { badge("issues", it.openIssuesCount.toString(), brandColor) }

  @BadgeMapping("/template")
  fun template(@PathVariable owner: String, @PathVariable name: String) =
      this.getRepo(owner, name)
          .map { badge("template", it.isTemplate) }

  @BadgeMapping("/created")
  fun created(@PathVariable owner: String, @PathVariable name: String) =
      this.getRepo(owner, name)
          .map(Repository::createdAt)
          .map { badge("created", "${it.passedTimeSince} ago", brandColor) }

  @BadgeMapping("/updated")
  fun updated(@PathVariable owner: String, @PathVariable name: String) =
      this.getRepo(owner, name)
          .map(Repository::updatedAt)
          .map { badge("updated", "${it.passedTimeSince} ago", brandColor) }

  @BadgeMapping("/pushed")
  fun lastPushed(@PathVariable owner: String, @PathVariable name: String) =
      this.getRepo(owner, name)
          .flatMap { Mono.justOrEmpty(it.pushedAt) }
          .map {
            badge("last activity", "${it.passedTimeSince} ago", brandColor)
          }
          .switchIfEmpty(Mono.just(badge("last activity", "never", Color.FALLBACK)))
}

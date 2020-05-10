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
import tv.dotstart.badge.controller.v1.badges.github.error.NoSuchReleaseException
import tv.dotstart.badge.service.badge.annotation.BadgeCategory
import tv.dotstart.badge.service.badge.annotation.BadgeMapping
import tv.dotstart.badge.service.cache.CacheProvider
import tv.dotstart.badge.service.connector.github.GitHub
import tv.dotstart.badge.service.connector.github.model.Release
import tv.dotstart.badge.util.Color
import tv.dotstart.badge.util.badge

/**
 * @author [Johannes Donath](mailto:johannesd@torchmind.com)
 * @date 09/05/2020
 */
@RestController
@ConditionalOnGitHubConnector
@BadgeCategory(
    "github.release",
    ["source", "release"],
    arrayOf(
        BadgeCategory.DefaultValue("owner", "dotStart"),
        BadgeCategory.DefaultValue("name", "Beacon")
    )
)
@RequestMapping("/v1/badge/github/repo/{owner}/{name}/release")
class GitHubReleaseBadgeController(
    private val gitHub: GitHub,
    cacheProvider: CacheProvider) {

  private val cache = cacheProvider.create("github_release", Release::class)

  fun getLatest(owner: String, name: String) =
      this.cache["${owner}_$name", this.gitHub.getLatestRelease(owner, name)]
          .onErrorMap(WebClientResponseException.NotFound::class.java) {
            NoSuchReleaseException("No latest release: $owner/$name", it)
          }

  @BadgeMapping("/latest/name")
  fun latestName(@PathVariable owner: String, @PathVariable name: String) =
      this.getLatest(owner, name)
          .flatMap {
            Mono.justOrEmpty(it.name)
                .switchIfEmpty(Mono.just(it.tagName))
          }
          .map { badge("latest release", it, brandColor) }
          .onErrorResume(WebClientResponseException.NotFound::class.java) {
            Mono.just(badge("latest release", "unknown", Color.FALLBACK))
          }

  @BadgeMapping("/latest/tag")
  fun latestTag(@PathVariable owner: String, @PathVariable name: String) =
      this.getLatest(owner, name)
          .map(Release::tagName)
          .map { badge("latest release", it, brandColor) }
          .onErrorResume(WebClientResponseException.NotFound::class.java) {
            Mono.just(badge("latest release", "unknown", Color.FALLBACK))
          }
}

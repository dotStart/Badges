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
package tv.dotstart.badge.controller.legacy

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tv.dotstart.badge.configuration.properties.annotations.ConditionalOnGitHubConnector
import tv.dotstart.badge.configuration.properties.annotations.ConditionalOnLegacy
import tv.dotstart.badge.controller.v1.badges.github.GitHubReleaseBadgeController
import tv.dotstart.badge.controller.v1.badges.github.GitHubRepositoryBadgeController
import tv.dotstart.badge.service.counter.ReactiveCounter

/**
 * Provides legacy mappings for GitHub repository endpoints.
 *
 * @author [Johannes Donath](mailto:johannesd@torchmind.com)
 * @date 10/05/2020
 */
@RestController
@ConditionalOnLegacy
@ConditionalOnGitHubConnector
@Deprecated("Replaced by v1 badge endpoints")
@RequestMapping("/github/repository/{owner}/{name}")
class LegacyGitHubRepositoryBadgeController(
    private val legacyCounter: ReactiveCounter,
    private val repoTarget: GitHubRepositoryBadgeController,
    private val releaseTarget: GitHubReleaseBadgeController) {

  @GetMapping("/activity")
  fun activity(@PathVariable owner: String,
               @PathVariable name: String) = this.legacyCounter.increment()
      .then(this.repoTarget.lastPushed(owner, name))

  @GetMapping("/forks")
  fun forks(@PathVariable owner: String,
            @PathVariable name: String) = this.legacyCounter.increment()
      .then(this.repoTarget.forks(owner, name))

  @GetMapping("/issues")
  fun openIssues(@PathVariable owner: String,
                 @PathVariable name: String) = this.legacyCounter.increment()
      .then(this.repoTarget.openIssuesCount(owner, name))

  @GetMapping("/language")
  fun language(@PathVariable owner: String,
               @PathVariable name: String) = this.legacyCounter.increment()
      .then(this.repoTarget.language(owner, name))

  @GetMapping("/license")
  fun license(@PathVariable owner: String,
              @PathVariable name: String) = this.legacyCounter.increment()
      .then(this.repoTarget.license(owner, name))

  @GetMapping("/release")
  fun latestRelease(@PathVariable owner: String,
                    @PathVariable name: String) = this.legacyCounter.increment()
      .then(this.releaseTarget.latestTag(owner, name))

  @GetMapping("/stars")
  fun stargazers(@PathVariable owner: String,
                 @PathVariable name: String) = this.legacyCounter.increment()
      .then(this.repoTarget.stargazers(owner, name))

  @GetMapping("/subscribers")
  fun subscribers(@PathVariable owner: String,
                  @PathVariable name: String) = this.legacyCounter.increment()
      .then(this.repoTarget.watchers(owner, name))

  @GetMapping("/watchers")
  fun watchers(@PathVariable owner: String,
               @PathVariable name: String) = this.legacyCounter.increment()
      .then(this.repoTarget.watchers(owner, name))
}

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
import tv.dotstart.badge.controller.v1.badges.github.GitHubUserBadgeController
import tv.dotstart.badge.service.counter.ReactiveCounter

/**
 * Provides legacy request mappings for GitHub user endpoints.
 *
 * @author [Johannes Donath](mailto:johannesd@torchmind.com)
 * @date 10/05/2020
 */
@RestController
@ConditionalOnLegacy
@ConditionalOnGitHubConnector
@Deprecated("Replaced by v1 badge endpoints")
@RequestMapping("/github/user/{username}")
class LegacyGitHubUserBadgeController(
    val legacyCounter: ReactiveCounter,
    val target: GitHubUserBadgeController) {

  @GetMapping("/company")
  fun company(@PathVariable username: String) = this.legacyCounter.increment()
      .then(this.target.company(username))

  @GetMapping("/location")
  fun location(@PathVariable username: String) = this.legacyCounter.increment()
      .then(this.target.location(username))

  @GetMapping("/hireable")
  fun hireable(@PathVariable username: String) = this.legacyCounter.increment()
      .then(this.target.hireable(username))

  @GetMapping("/repositories")
  fun repositories(@PathVariable username: String) = this.legacyCounter.increment()
      .then(this.target.publicRepos(username))

  @GetMapping("/gists")
  fun gists(@PathVariable username: String) = this.legacyCounter.increment()
      .then(this.target.publicGists(username))

  @GetMapping("/followers")
  fun followers(@PathVariable username: String) = this.legacyCounter.increment()
      .then(this.target.followers(username))

  @GetMapping("/following")
  fun following(@PathVariable username: String) = this.legacyCounter.increment()
      .then(this.target.following(username))
}

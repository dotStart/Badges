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
package tv.dotstart.badge.controller.v1.badges.gitlab

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tv.dotstart.badge.configuration.properties.annotations.ConditionalOnGitHubConnector
import tv.dotstart.badge.service.badge.annotation.BadgeCategory
import tv.dotstart.badge.service.badge.annotation.BadgeMapping
import tv.dotstart.badge.service.cache.CacheProvider
import tv.dotstart.badge.service.connector.gitlab.GitLab
import tv.dotstart.badge.service.connector.gitlab.model.Project
import tv.dotstart.badge.util.badge
import tv.dotstart.badge.util.passedTimeSince

/**
 * @author [Johannes Donath](mailto:johannesd@torchmind.com)
 * @date 09/05/2020
 */
@RestController
@ConditionalOnGitHubConnector
@BadgeCategory(
    "gitlab.project",
    ["source"],
    arrayOf(
        BadgeCategory.DefaultValue("projectId", "13083")
    )
)
@RequestMapping("/v1/badge/gitlab/project/{projectId}")
class GitLabProjectBadgeController(
    private val gitLab: GitLab,
    cacheProvider: CacheProvider) {

  private val cache = cacheProvider.create("gitlab_project", Project::class)

  fun getProject(projectId: String) =
      this.cache[projectId, this.gitLab.getProject(projectId)]

  @BadgeMapping("/issues")
  fun openIssues(@PathVariable projectId: String) = this.getProject(projectId)
      .map { badge("open issues", it.openIssuesCount.toString(), brandColor) }

  @BadgeMapping("/created")
  fun createdAt(@PathVariable projectId: String) = this.getProject(projectId)
      .map(Project::createdAt)
      .map { badge("created", "${it.passedTimeSince} ago", brandColor) }

  @BadgeMapping("/activity")
  fun lastActivityAt(@PathVariable projectId: String) = this.getProject(projectId)
      .map(Project::lastActivityAt)
      .map { badge("last activity", "${it.passedTimeSince} ago", brandColor) }

  @BadgeMapping("/forks")
  fun forksCount(@PathVariable projectId: String) = this.getProject(projectId)
      .map(Project::forksCount)
      .map { badge("forks", it.toString(), brandColor) }

  @BadgeMapping("/stars")
  fun starCount(@PathVariable projectId: String) = this.getProject(projectId)
      .map(Project::starCount)
      .map { badge("stars", it.toString(), brandColor) }

  @BadgeMapping("/mirror")
  fun mirror(@PathVariable projectId: String) = this.getProject(projectId)
      .map { badge("mirror", it.mirror) }
}

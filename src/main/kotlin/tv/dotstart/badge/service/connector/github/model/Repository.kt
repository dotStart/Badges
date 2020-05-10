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
package tv.dotstart.badge.service.connector.github.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime

/**
 * @author [Johannes Donath](mailto:johannesd@torchmind.com)
 * @date 08/05/2020
 */
data class Repository(
    val id: Long,
    @JsonProperty("node_id")
    val nodeId: String,
    val name: String,
    @JsonProperty("full_name")
    val fullName: String,
    val owner: Owner,
    val private: Boolean,
    @JsonProperty("html_url")
    val htmlUrl: String,
    val description: String,
    val fork: Boolean,
    val url: String,
    @JsonProperty("archive_url")
    val archiveUrl: String,
    @JsonProperty("assignees_url")
    val assigneesUrl: String,
    @JsonProperty("blobs_url")
    val blobsUrl: String,
    @JsonProperty("branches_url")
    val branchesUrl: String,
    @JsonProperty("collaborators_url")
    val collaboratorsUrl: String,
    @JsonProperty("comments_url")
    val commentsUrl: String,
    @JsonProperty("commits_url")
    val commitsUrl: String,
    @JsonProperty("compare_url")
    val compareUrl: String,
    @JsonProperty("contents_url")
    val contentsUrl: String,
    @JsonProperty("contributors_url")
    val contributorsUrl: String,
    @JsonProperty("deployments_url")
    val deploymentsUrl: String,
    @JsonProperty("downloads_url")
    val downloadsUrl: String,
    @JsonProperty("events_url")
    val eventsUrl: String,
    @JsonProperty("forks_url")
    val forksUrl: String,
    @JsonProperty("git_commits_url")
    val gitCommitsUrl: String,
    @JsonProperty("git_refs_url")
    val gitRefsUrl: String,
    @JsonProperty("git_tags_url")
    val gitTagsUrl: String,
    @JsonProperty("git_url")
    val gitUrl: String,
    @JsonProperty("issue_comment_url")
    val issueCommentUrl: String,
    @JsonProperty("issue_events_url")
    val issueEventsUrl: String,
    @JsonProperty("issues_url")
    val issuesUrl: String,
    @JsonProperty("keys_url")
    val keysUrl: String,
    @JsonProperty("labels_url")
    val labelsUrl: String,
    @JsonProperty("languages_url")
    val languagesUrl: String,
    @JsonProperty("merges_url")
    val mergesUrl: String,
    @JsonProperty("milestones_url")
    val milestonesUrl: String,
    @JsonProperty("notifications_url")
    val notificationsUrl: String,
    @JsonProperty("pulls_url")
    val pullsUrl: String,
    @JsonProperty("releases_url")
    val releasesUrl: String,
    @JsonProperty("ssh_url")
    val sshUrl: String,
    @JsonProperty("stargazers_url")
    val stargazersUrl: String,
    @JsonProperty("statuses_url")
    val statusesUrl: String,
    @JsonProperty("subscribers_url")
    val subscribersUrl: String,
    @JsonProperty("subscription_url")
    val subscriptionUrl: String,
    @JsonProperty("tags_url")
    val tagsUrl: String,
    @JsonProperty("teams_url")
    val teamsUrl: String,
    @JsonProperty("trees_url")
    val treesUrl: String,
    @JsonProperty("clone_url")
    val cloneUrl: String,
    @JsonProperty("mirror_url")
    val mirrorUrl: String?,
    @JsonProperty("hooks_url")
    val hooksUrl: String,
    @JsonProperty("svn_url")
    val svnUrl: String,
    val homepage: String?,
    val language: String?,
    @JsonProperty("forks_count")
    val forksCount: Long,
    @JsonProperty("stargazers_count")
    val stargazersCount: Long,
    @JsonProperty("watchers_count")
    val watchersCount: Long,
    val size: Long,
    @JsonProperty("default_branch")
    val defaultBranch: String,
    @JsonProperty("open_issues_count")
    val openIssuesCount: Long,
    @JsonProperty("is_template")
    val isTemplate: Boolean,
    @JsonProperty("has_issues")
    val hasIssues: Boolean,
    @JsonProperty("has_projects")
    val hasProjects: Boolean,
    @JsonProperty("has_wiki")
    val hasWiki: Boolean,
    @JsonProperty("has_pages")
    val hasPages: Boolean,
    @JsonProperty("has_downloads")
    val hasDownloads: Boolean,
    val archived: Boolean,
    val disabled: Boolean,
    val visibility: String?, // TODO: Enum, not populated?
    @JsonProperty("pushed_at")
    val pushedAt: OffsetDateTime?,
    @JsonProperty("created_at")
    val createdAt: OffsetDateTime,
    @JsonProperty("updated_at")
    val updatedAt: OffsetDateTime,
    val permissions: Map<String, Boolean>?, // TODO: Does not seem to be present in our case
    @JsonProperty("allow_rebase_merge")
    val allowRebaseMerge: Boolean,
    @JsonProperty("template_repository")
    val templateRepository: String?, // TODO: Is this the right type?
    @JsonProperty("temp_clone_token")
    val tempCloneToken: String?, // TODO: Not present in our case
    @JsonProperty("allow_squash_merge")
    val allowSquashMerge: Boolean,
    @JsonProperty("allow_merge_commit")
    val allowMergeCommit: Boolean,
    @JsonProperty("subscribers_count")
    val subscribersCount: Long,
    @JsonProperty("network_count")
    val networkCount: Long,
    val license: License?,
    val organization: Owner?,
    val parent: Repository?,
    val source: Repository?)

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
package tv.dotstart.badge.service.connector.gitlab.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate
import java.time.OffsetDateTime

/**
 * @author [Johannes Donath](mailto:johannesd@torchmind.com)
 * @date 09/05/2020
 */
@JsonIgnoreProperties("_links", "permissions")
data class Project(
    val id: Long,
    val description: String?,
    @JsonProperty("default_branch")
    val defaultBranch: String,
    val visibility: String?, // TODO: Enum
    @JsonProperty("ssh_url_to_repo")
    val sshUrlToRepo: String,
    @JsonProperty("http_url_to_repo")
    val httpUrlToRepo: String,
    @JsonProperty("web_url")
    val webUrl: String?,
    @JsonProperty("readme_url")
    val readmeUrl: String?,
    @JsonProperty("tag_list")
    val tagList: List<String>,
    val owner: Owner?,
    val name: String,
    @JsonProperty("name_with_namespace")
    val nameWithNamespace: String,
    val path: String,
    @JsonProperty("path_with_namespace")
    val pathWithNamespace: String,
    @JsonProperty("issues_enabled")
    val issuesEnabled: Boolean,
    @JsonProperty("open_issues_count")
    val openIssuesCount: Long,
    @JsonProperty("merge_requests_enabled")
    val mergeRequestsEnabled: Boolean,
    @JsonProperty("jobs_enabled")
    val jobsEnabled: Boolean,
    @JsonProperty("wiki_enabled")
    val wikiEnabled: Boolean,
    @JsonProperty("snippets_enabled")
    val snippetsEnabled: Boolean,
    @JsonProperty("can_create_merge_request_in")
    val canCreateMergeRequestIn: Boolean,
    @JsonProperty("resolve_outdated_diff_discussions")
    val resolveOutdatedDiffDiscussions: Boolean,
    @JsonProperty("container_registry_enabled")
    val containerRegistryEnabled: Boolean,
    @JsonProperty("container_expiration_policy")
    val containerExpirationPolicy: ContainerExpirationPolicy?,
    @JsonProperty("created_at")
    val createdAt: OffsetDateTime,
    @JsonProperty("last_activity_at")
    val lastActivityAt: OffsetDateTime,
    @JsonProperty("creator_id")
    val creatorId: Long,
    @JsonProperty("namespace")
    val namespace: Namespace,
    @JsonProperty("import_status")
    val importStatus: String?, // TODO: Enum
    @JsonProperty("import_error")
    val importError: String?,
    val archived: Boolean,
    @JsonProperty("avatar_url")
    val avatarUrl: String,
    @JsonProperty("license_url")
    val licenseUrl: String?,
    val license: License?,
    @JsonProperty("shared_runners_enabled")
    val sharedRunnersEnabled: Boolean,
    @JsonProperty("forks_count")
    val forksCount: Long,
    @JsonProperty("star_count")
    val starCount: Long,
    @JsonProperty("runners_token")
    val runnersToken: String?,
    @JsonProperty("ci_default_git_depth")
    val ciDefaultGitDepth: Long,
    @JsonProperty("public_jobs")
    val publicJobs: Boolean,
    @JsonProperty("shared_with_groups")
    val sharedWithGroups: List<Group> = emptyList(),
    @JsonProperty("repository_storage")
    val repositoryStorage: String?, // TODO: Enum?
    @JsonProperty("only_allow_merge_if_pipeline_succeeds")
    val onlyAllowMergeIfPipelineSucceeds: Boolean,
    @JsonProperty("only_allow_merge_if_all_discussions_are_resolved")
    val onlyAllowMergeIfAllDiscussionsAreResolved: Boolean,
    @JsonProperty("remove_source_branch_after_merge")
    val removeSourceBranchAfterMerge: Boolean,
    @JsonProperty("printing_merge_requests_link_enabled")
    val printingMergeRequestsLinkEnabled: Boolean,
    @JsonProperty("request_access_enabled")
    val requestAccessEnabled: Boolean,
    @JsonProperty("merge_method")
    val mergeMethod: String?, // TODO: Enum
    @JsonProperty("auto_devops_enabled")
    val autoDevopsEnabled: Boolean,
    @JsonProperty("auto_devops_deploy_strategy")
    val autoDevopsDeployStrategy: String?, // TODO: Enum
    @JsonProperty("approvals_before_merge")
    val approvalsBeforeMerge: Long,
    val mirror: Boolean,
    @JsonProperty("mirror_user_id")
    val mirrorUserId: Long,
    @JsonProperty("mirror_trigger_builds")
    val mirrorTriggerBuilds: Boolean,
    @JsonProperty("only_mirror_protected_branches")
    val onlyMirrorProtectedBranches: Boolean,
    @JsonProperty("mirror_overwrites_diverged_branches")
    val mirrorOverwritesDivergedBranches: Boolean,
    @JsonProperty("external_authorization_classification_label")
    val externalAuthorizationClassificationLabel: String?,
    @JsonProperty("packages_enabled")
    val packagesEnabled: Boolean,
    @JsonProperty("service_desk_enabled")
    val serviceDeskEnabled: Boolean,
    @JsonProperty("service_desk_address")
    val serviceDeskAddress: String?,
    @JsonProperty("autoclose_referenced_issues")
    val autocloseReferencedIssues: Boolean,
    @JsonProperty("suggestion_commit_message")
    val suggestionCommitMessage: String?,
    @JsonProperty("marked_for_deletion_at")
    @Deprecated("Removal scheduled for REST API v5 in favor of marked_for_deletion_on")
    val markedForDeletionAt: LocalDate?,
    @JsonProperty("marked_for_deletion_on")
    val markedForDeletionOn: LocalDate?,
    val statistics: Statistics?) {

  data class Statistics(
      @JsonProperty("commit_count")
      val commitCount: Long,
      @JsonProperty("storage_size")
      val storageSize: Long,
      @JsonProperty("repository_size")
      val repositorySize: Long,
      @JsonProperty("wiki_size")
      val wikiSize: Long,
      @JsonProperty("lfs_objects_size")
      val lfsObjectsSize: Long,
      @JsonProperty("job_artifacts_size")
      val jobArtifactsSize: Long,
      @JsonProperty("packages_size")
      val packagesSize: Long)
}

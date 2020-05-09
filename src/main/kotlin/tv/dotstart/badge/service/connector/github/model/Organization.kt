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
 * @date 09/05/2020
 */
data class Organization(
    val login: String,
    val id: Long,
    @JsonProperty("node_id")
    val nodeId: String,
    val url: String,
    @JsonProperty("repos_url")
    val reposUrl: String,
    @JsonProperty("events_url")
    val eventsUrl: String,
    @JsonProperty("hooks_url")
    val hooksUrl: String,
    @JsonProperty("issues_url")
    val issuesUrl: String,
    @JsonProperty("members_url")
    val membersUrl: String,
    @JsonProperty("public_members_url")
    val publicMembersUrl: String,
    @JsonProperty("avatar_url")
    val avatarUrl: String,
    val description: String?,
    val name: String,
    val company: String?,
    val blog: String?,
    val location: String?,
    val email: String?,
    @JsonProperty("is_verified")
    val isVerified: Boolean,
    @JsonProperty("has_organization_projects")
    val hasOrganizationProjects: Boolean,
    @JsonProperty("has_repository_projects")
    val hasRepositoryProjects: Boolean,
    @JsonProperty("public_repos")
    val publicRepos: Long,
    @JsonProperty("public_gists")
    val publicGists: Long,
    val followers: Long,
    val following: Long,
    @JsonProperty("html_url")
    val htmlUrl: String,
    @JsonProperty("created_at")
    val createdAt: OffsetDateTime,
    val type: String, // TODO: Enum
    @JsonProperty("total_private_repos")
    val totalPrivateRepos: Long,
    @JsonProperty("owned_private_repos")
    val ownedPrivateRepos: Long,
    @JsonProperty("private_gists")
    val privateGists: Long,
    @JsonProperty("disk_usage")
    val diskUsage: Long,
    val collaborators: Long,
    val plan: Plan?,
    @JsonProperty("default_repository_permission")
    val defaultRepositoryPermission: String?, // TODO: Enum
    @JsonProperty("members_can_create_repositories")
    val membersCanCreateRepositories: Boolean,
    @JsonProperty("two_factor_requirement_enabled")
    val twoFactorRequirementEnabled: Boolean,
    @JsonProperty("members_allowed_repository_creation_type")
    val membersAllowedRepositoryCreationType: String?, // TODO: Enum
    @JsonProperty("members_can_create_public_repositories")
    val membersCanCreatePublicRepositories: Boolean,
    @JsonProperty("members_can_create_private_repositories")
    val membersCanCreatePrivateRepositories: Boolean,
    @JsonProperty("members_can_create_internal_repositories")
    val membersCanCreateInternalRepositories: Boolean)

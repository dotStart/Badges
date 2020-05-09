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
data class Release(
    val url: String,
    @JsonProperty("html_url")
    val htmlUrl: String,
    @JsonProperty("assets_url")
    val assetsUrl: String,
    @JsonProperty("upload_url")
    val uploadUrl: String,
    @JsonProperty("tarball_url")
    val tarballUrl: String,
    @JsonProperty("zipball_url")
    val zipballUrl: String,
    val id: Long,
    @JsonProperty("node_id")
    val nodeId: String,
    @JsonProperty("tag_name")
    val tagName: String,
    @JsonProperty("target_commitish")
    val targetCommitish: String,
    val name: String?,
    val body: String,
    val draft: Boolean,
    val prerelease: Boolean,
    @JsonProperty("created_at")
    val createdAt: OffsetDateTime,
    @JsonProperty("published_at")
    val publishedAt: OffsetDateTime?,
    val author: Owner,
    val assets: List<Asset>)

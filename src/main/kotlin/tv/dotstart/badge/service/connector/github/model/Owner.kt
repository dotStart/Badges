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

/**
 * @author [Johannes Donath](mailto:johannesd@torchmind.com)
 * @date 08/05/2020
 */
data class Owner(
    val login: String,
    val id: Long,
    @JsonProperty("node_id")
    val nodeId: String,
    @JsonProperty("avatar_url")
    val avatarUrl: String,
    @JsonProperty("gravatar_id")
    val gravatarId: String?,
    @JsonProperty("url")
    val url: String,
    @JsonProperty("html_url")
    val htmlUrl: String,
    @JsonProperty("followers_url")
    val followersUrl: String,
    @JsonProperty("following_url")
    val followingUrl: String,
    @JsonProperty("gists_url")
    val gistsUrl: String,
    @JsonProperty("starred_url")
    val starredUrl: String,
    @JsonProperty("subscriptions_url")
    val subscriptionsUrl: String,
    @JsonProperty("repos_url")
    val reposUrl: String,
    @JsonProperty("events_url")
    val eventsUrl: String,
    @JsonProperty("received_events_url")
    val receivedEventsUrl: String,
    val type: String, // TODO: Enum
    @JsonProperty("site_admin")
    val siteAdmin: Boolean)

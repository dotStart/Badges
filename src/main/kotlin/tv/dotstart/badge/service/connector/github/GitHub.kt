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
package tv.dotstart.badge.service.connector.github

import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import tv.dotstart.badge.service.connector.Connector
import tv.dotstart.badge.service.connector.github.model.Organization
import tv.dotstart.badge.service.connector.github.model.Release
import tv.dotstart.badge.service.connector.github.model.Repository
import tv.dotstart.badge.service.connector.github.model.User
import tv.dotstart.badge.service.counter.ReactiveCounter

/**
 * @author [Johannes Donath](mailto:johannesd@torchmind.com)
 * @date 06/05/2020
 */
class GitHub(private val clientId: String?,
             private val clientSecret: String?,
             baseUrl: String = "https://api.github.com",
             private val counter: ReactiveCounter) :
    Connector {

  private val client = WebClient.builder()
      .baseUrl(baseUrl)
      .defaultHeaders { headers ->
        headers["Accept"] = "application/vnd.github.v3+json"

        if (clientId != null && clientSecret != null) {
          headers.setBasicAuth(clientId, clientSecret)
        }
      }
      .build()

  override val name = "github"
  override val rateLimit = 5000L

  init {
    if (this.clientId == null || this.clientSecret == null) {
      require(this.clientId == null && this.clientSecret == null) {
        "Must specify both clientId and clientSecret"
      }
    }
  }

  override fun getRateLimitUsage() = this.counter.get()

  /**
   * Retrieves a given user profile.
   */
  fun getUser(username: String) = this.counter.increment()
      .then(this.client.get()
                .uri("/users/{username}", username)
                .retrieve()
                .bodyToMono<User>())

  /**
   * Retrieves a given organization profile.
   */
  fun getOrganization(username: String) = this.counter.increment()
      .then(this.client.get()
                .uri("/orgs/{username}", username)
                .retrieve()
                .bodyToMono<Organization>())

  /**
   * Retrieves a given repository.
   */
  fun getRepository(owner: String, name: String) = this.counter.increment()
      .then(this.client.get()
                .uri("/repos/{owner}/{name}", owner, name)
                .retrieve()
                .bodyToMono<Repository>())

  /**
   * Retrieves the latest published release for a given repository.
   */
  fun getLatestRelease(owner: String, name: String) = this.counter.increment()
      .then(this.client.get()
                .uri("/repos/{owner}/{name}/releases/latest", owner, name)
                .retrieve()
                .bodyToMono<Release>())
}

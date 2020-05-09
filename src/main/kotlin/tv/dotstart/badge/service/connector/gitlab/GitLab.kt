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
package tv.dotstart.badge.service.connector.gitlab

import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import tv.dotstart.badge.service.connector.Connector
import tv.dotstart.badge.service.connector.gitlab.model.Project
import tv.dotstart.badge.service.counter.ReactiveCounter

/**
 * @author [Johannes Donath](mailto:johannesd@torchmind.com)
 * @date 09/05/2020
 */
class GitLab(
    baseUrl: String = "https://gitlab.com/api",
    private val counter: ReactiveCounter) : Connector {

  private val client = WebClient.builder()
      .baseUrl(baseUrl)
      .defaultHeaders { headers ->
        headers["Accept"] = "application/json"
      }
      .build()

  override val name = "gitlab"
  override val rateLimit: Long = 10

  override fun getRateLimitUsage() = this.counter.get()

  fun getProject(projectId: String) = this.client.get()
      .uri("/v4/projects/{projectId}", projectId)
      .retrieve()
      .bodyToMono<Project>()
}

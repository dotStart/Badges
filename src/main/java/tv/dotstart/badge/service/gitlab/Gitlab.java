/*
 * Copyright 2018 Johannes Donath <johannesd@torchmind.com>
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
package tv.dotstart.badge.service.gitlab;

import java.io.FileNotFoundException;
import java.util.Optional;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import tv.dotstart.badge.configuration.integration.GitlabProperties;
import tv.dotstart.badge.service.AbstractRestService;

/**
 * Provides access to Gitlab related data.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
@Service
@EnableConfigurationProperties(GitlabProperties.class)
public class Gitlab extends AbstractRestService {

  private final GitlabProperties properties;

  public Gitlab(@NonNull GitlabProperties properties) {
    this.properties = properties;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void customizeHeaders(@NonNull MultiValueMap<String, String> headers) {
    var token = this.properties.getToken();

    if (token == null) {
      return;
    }

    switch (this.properties.getTokenType()) {
      case OAUTH_TOKEN:
        headers.add("Authorization", "Bearer " + token);
        break;
      case PRIVATE_TOKEN:
        headers.add("Authorization", "Private-Token " + token);
        break;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String translateUrl(String endpointUrl) {
    return super.translateUrl(this.properties.getBaseUrl() + "/api/v4" + endpointUrl);
  }

  /**
   * Retrieves a project based on its globally unique identifier.
   *
   * @param projectId a project identifier.
   * @return a Gitlab project or, if none was found with the given identifier, an empty optional.
   */
  @NonNull
  @Cacheable(cacheNames = "gitlab-project", key = "#projectId")
  public Optional<GLProject> getProject(@NonNull String projectId) {
    try {
      return Optional.of(this.get("/projects/{projectId}", GLProject.class, projectId));
    } catch (FileNotFoundException ex) {
      return Optional.empty();
    }
  }
}

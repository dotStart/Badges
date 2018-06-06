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
package tv.dotstart.badge.service.github;

import java.io.FileNotFoundException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import tv.dotstart.badge.configuration.integration.GithubProperties;
import tv.dotstart.badge.service.AbstractRestService;

/**
 * Provides access to Github related data (e.g. organizations, repositories, etc).
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
@Service
@EnableConfigurationProperties(GithubProperties.class)
public class Github extends AbstractRestService {

  /**
   * Defines the main URL at which the GitHub API is available.
   */
  private static final String BASE_URL = "https://api.github.com";

  private final String authenticationParameters;
  private final RestTemplate rest = new RestTemplate();

  public Github(@NonNull GithubProperties properties) {
    var clientId = properties.getClientId();
    var clientSecret = properties.getClientSecret();

    if (clientId == null || clientSecret == null) {
      this.authenticationParameters = "";
    } else {
      this.authenticationParameters =
          "?client_id=" + URLEncoder.encode(clientId, StandardCharsets.UTF_8) + "&client_secret="
              + URLEncoder.encode(clientSecret, StandardCharsets.UTF_8);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void customizeHeaders(MultiValueMap<String, String> headers) {
    headers.add("Accept", "application/vnd.github.v3+json");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String translateUrl(@NonNull String endpointUrl) {
    return BASE_URL + endpointUrl + this.authenticationParameters;
  }

  /**
   * Retrieves an organization object for the given login name.
   *
   * @param loginName a login name.
   * @return an organization.
   * @throws RuntimeException when the GitHub query fails.
   */
  @NonNull
  @Cacheable(cacheNames = "github-organization", key = "{#loginName}")
  public Optional<GHOrganization> getOrganization(@NonNull String loginName) {
    try {
      return Optional.of(this.get("/orgs/{loginName}", GHOrganization.class, loginName));
    } catch (FileNotFoundException ex) {
      return Optional.empty();
    }
  }

  /**
   * Retrieves a repository object for the given owner and repository name.
   *
   * @param ownerName an owner login name.
   * @param repositoryName a repository name.
   * @return a repository.
   * @throws RuntimeException when the GitHub query fails.
   */
  @NonNull
  @Cacheable(cacheNames = "github-repository", key = "{#ownerName,#repositoryName}")
  public Optional<GHRepository> getRepository(@NonNull String ownerName,
      @NonNull String repositoryName) {
    try {
      return Optional.of(this
          .get("/repos/{ownerName}/{repositoryName}", GHRepository.class, ownerName,
              repositoryName));
    } catch (FileNotFoundException ex) {
      return Optional.empty();
    }
  }

  /**
   * Retrieves the latest release for a given owner and repository name.
   *
   * @param ownerName an owner login name.
   * @param repositoryName a repository name.
   * @return a release.
   */
  @NonNull
  @Cacheable(cacheNames = "github-release", key = "{#ownerName,#repositoryName}")
  public Optional<GHRelease> getLatestRelease(@NonNull String ownerName,
      @NonNull String repositoryName) {
    try {
      return Optional.of(this
          .get("/repos/{ownerName}/{repositoryName}/releases/latest", GHRelease.class, ownerName,
              repositoryName));
    } catch (FileNotFoundException ex) {
      return Optional.empty();
    }
  }
}

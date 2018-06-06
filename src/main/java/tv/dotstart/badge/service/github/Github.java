package tv.dotstart.badge.service.github;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import tv.dotstart.badge.configuration.integration.GithubProperties;

/**
 * Provides access to Github related data (e.g. organizations, repositories, etc).
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
@Service
public class Github {

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
          "?clientId=" + URLEncoder.encode(clientId, StandardCharsets.UTF_8) + "?clientSecret="
              + URLEncoder.encode(clientSecret, StandardCharsets.UTF_8);
    }
  }

  /**
   * Performs an HTTP get request on an arbitrary endpoint.
   *
   * @param endpoint an endpoint (relative to the API base URL).
   * @param type a response type.
   * @param urlVariables an array of values with which URL variables will be replaced.
   * @param <E> a response type.
   * @return a parsed response.
   * @throws IOException when the request completes with an invalid response.
   * @throws FileNotFoundException when the request completes with a "Not Found" error.
   */
  @NonNull
  private <E> E get(@NonNull String endpoint, @NonNull Class<E> type, String... urlVariables)
      throws IOException {
    var headers = new LinkedMultiValueMap<String, String>();
    headers.add("Accept", "application/vnd.github.v3+json");

    var response = this.rest
        .exchange(BASE_URL + endpoint + this.authenticationParameters, HttpMethod.GET,
            new HttpEntity<>(headers), type, (Object[]) urlVariables);

    if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
      throw new FileNotFoundException("No such entity: " + endpoint);
    } else if (response.getStatusCode() != HttpStatus.OK) {
      throw new IOException("Illegal response code: " + response.getStatusCode());
    }

    return response.getBody();
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
    } catch (IOException ex) {
      throw new RuntimeException("Cannot fetch organization from GitHub: " + loginName, ex);
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
    } catch (IOException ex) {
      var fullName = ownerName + "/" + repositoryName;
      throw new RuntimeException("Cannot fetch repository from GitHub: " + fullName, ex);
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
    } catch (IOException ex) {
      var fullName = ownerName + "/" + repositoryName;
      throw new RuntimeException(
          "Cannot fetch latest release for repository from GitHub: " + fullName, ex);
    }
  }
}

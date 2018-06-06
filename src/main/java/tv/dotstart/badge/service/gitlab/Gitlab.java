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

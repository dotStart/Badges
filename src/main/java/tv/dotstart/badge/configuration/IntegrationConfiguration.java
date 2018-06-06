package tv.dotstart.badge.configuration;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gitlab.api.GitlabAPI;
import org.kohsuke.github.GitHub;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import tv.dotstart.badge.configuration.integration.GithubProperties;
import tv.dotstart.badge.configuration.integration.GitlabProperties;

/**
 * Provides integration related beans (typically API clients) for badge generation to the
 * application.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
@Configuration
@EnableConfigurationProperties({
    GithubProperties.class,
    GitlabProperties.class
})
public class IntegrationConfiguration {

  private static final Logger logger = LogManager
      .getFormatterLogger(IntegrationConfiguration.class);

  private final GithubProperties githubProperties;
  private final GitlabProperties gitlabProperties;

  public IntegrationConfiguration(
      @NonNull GithubProperties githubProperties,
      @NonNull GitlabProperties gitlabProperties) {
    this.githubProperties = githubProperties;
    this.gitlabProperties = gitlabProperties;
  }

  /**
   * Provides a Github API client which will either use anonymous authentication or a clientId if
   * specified.
   *
   * @return a Github API client.
   */
  @Bean
  @NonNull
  public GitHub githubApi() throws IOException {
    var clientId = this.githubProperties.getClientId();
    logger.info("Github integration has been enabled");
    if (clientId == null) {
      logger.warn("Github integration is using anonymous authentication");
      logger.warn(
          "Restrictive rate limits are in effect and may cause badge generation to randomly fail!");
    }

    return clientId == null ? GitHub.connectAnonymously() : GitHub.connectUsingOAuth(clientId);
  }

  /**
   * Provides a Gitlab API client (given that a baseUrl has been passed to the application upon
   * startup).
   *
   * @return a Gitlab API client.
   */
  @Bean
  @NonNull
  public GitlabAPI gitlabApi() {
    logger.info("Gitlab integration has been enabled (using token of type %s)",
        this.gitlabProperties.getTokenType());
    return GitlabAPI.connect(this.gitlabProperties.getBaseUrl(), this.gitlabProperties.getToken(),
        this.gitlabProperties.getTokenType());
  }
}

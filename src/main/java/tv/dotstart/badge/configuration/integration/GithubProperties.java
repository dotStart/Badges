package tv.dotstart.badge.configuration.integration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Provides configuration properties with which the GitHub integration may be enabled and
 * customized.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
@ConfigurationProperties("badge.integration.github")
public class GithubProperties {

  private String clientId;
  private String clientSecret;

  public String getClientId() {
    return this.clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getClientSecret() {
    return this.clientSecret;
  }

  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
  }
}

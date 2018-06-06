package tv.dotstart.badge.configuration.integration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Provides properties with which the Gitlab integration can be enabled and customized.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
@ConfigurationProperties("badge.integration.gitlab")
public class GitlabProperties {

  private String baseUrl = "https://gitlab.com";
  private String token;
  private TokenType tokenType = TokenType.PRIVATE_TOKEN;

  public String getBaseUrl() {
    return this.baseUrl;
  }

  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public String getToken() {
    return this.token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public TokenType getTokenType() {
    return this.tokenType;
  }

  public void setTokenType(TokenType tokenType) {
    this.tokenType = tokenType;
  }

  /**
   * Provides a list of valid Gitlab token types.
   */
  public enum TokenType {
    OAUTH_TOKEN,
    PRIVATE_TOKEN
  }
}

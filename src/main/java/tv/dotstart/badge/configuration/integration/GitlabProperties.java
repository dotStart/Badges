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

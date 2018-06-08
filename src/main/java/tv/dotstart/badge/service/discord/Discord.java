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
package tv.dotstart.badge.service.discord;

import java.io.FileNotFoundException;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import tv.dotstart.badge.service.AbstractRestService;

/**
 * Provides access to Discord related data.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
@Service
//@EnableConfigurationProperties(DiscordProperties.class)
//@ConditionalOnProperty("badge.integration.discord.botToken")
public class Discord extends AbstractRestService {

  private static final String BASE_URL = "https://discordapp.com/api/v6";
  // private final String authenticationHeader;

  /*
  public Discord(@NonNull DiscordProperties properties) {
    // guaranteed to be non null as the service does not start otherwise
    this.authenticationHeader = "Bot " + properties.getBotToken();
  }
  */

  /**
   * {@inheritDoc}
   */
  @Override
  protected String translateUrl(@NonNull String endpointUrl) {
    return BASE_URL + endpointUrl;
  }

  // No Purpose for this yet
  /*
  @Override
  protected void customizeHeaders(MultiValueMap<String, String> headers) {
    headers.add("Authentication", this.authenticationHeader);
  }*/

  /**
   * Retrieves the widget contents for the specified server.
   *
   * @param guildId a guild identifier.
   * @return a widget or, if no such guild exists (or the widget is disabled), an empty optional.
   */
  @NonNull
  @Cacheable(cacheNames = "discord-widget", key = "#guildId")
  public Optional<DWidget> getWidget(@NonNull String guildId) {
    try {
      return Optional.of(this.get("/guilds/{guildId}/widget.json", DWidget.class, guildId));
    } catch (FileNotFoundException ex) {
      return Optional.empty();
    } catch (HttpClientErrorException ex) {
      if (ex.getStatusCode() == HttpStatus.FORBIDDEN) {
        return Optional.empty();
      }

      throw ex;
    }
  }
}

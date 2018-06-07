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
package tv.dotstart.badge;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * Exposes basic version metadata to the application.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
@Service
public final class BadgeVersion {

  private final String name;
  private final String version;
  private final String revision;
  private final String shortRevision;
  private final Instant timestamp;

  public BadgeVersion() {
    var logger = LogManager.getFormatterLogger(BadgeVersion.class);
    var properties = new Properties();

    try (InputStream inputStream = BadgeVersion.class.getResourceAsStream("/version.properties")) {
      if (inputStream == null) {
        throw new FileNotFoundException("Cannot load version information: No such resource");
      }

      properties.load(inputStream);
    } catch (IOException ex) {
      logger.warn("Cannot load application version metadata", ex);
    }

    this.name = properties.getProperty("application.name", "unknown");
    this.version = properties.getProperty("application.version", "0.0.0");
    this.revision = properties.getProperty("application.revision", "+dev");

    // jgit does not support the generation of shortened ids through the maven plugin
    if (this.revision.length() > 7) {
      this.shortRevision = this.revision.substring(0, 7);
    } else {
      this.shortRevision = this.revision;
    }

    var parsedTimestamp = Instant.EPOCH;
    try {
      parsedTimestamp = Instant.parse(properties.getProperty("application.timestamp"));
    } catch (DateTimeParseException ex) {
      logger.warn("Cannot parse application build timestamp", ex);
    }
    this.timestamp = parsedTimestamp;
  }

  @NonNull
  public String getName() {
    return this.name;
  }

  @NonNull
  public String getVersion() {
    return this.version;
  }

  @NonNull
  public String getRevision() {
    return this.revision;
  }

  @NonNull
  public String getShortRevision() {
    return this.shortRevision;
  }

  @NonNull
  public Instant getTimestamp() {
    return this.timestamp;
  }
}

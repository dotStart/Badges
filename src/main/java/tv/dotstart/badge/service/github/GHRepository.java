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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * Represents a repository.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
public class GHRepository implements Serializable {

  private final long id;
  private final String name;
  private final String fullName;
  private final Instant createdAt;
  private final Instant updatedAt;
  private final Instant pushedAt;

  private final GHLicense license;
  private final String language;
  private final long size;
  private final int stargazers;
  private final int watchers;
  private final int subscriberCount;
  private final int forks;
  private final int openIssues;

  @JsonCreator
  public GHRepository(
      @NonNull @JsonProperty(value = "id", required = true) long id,
      @NonNull @JsonProperty(value = "name", required = true) String name,
      @NonNull @JsonProperty(value = "full_name", required = true) String fullName,
      @NonNull @JsonProperty(value = "created_at", required = true) Instant createdAt,
      @NonNull @JsonProperty(value = "updated_at", required = true) Instant updatedAt,
      @Nullable @JsonProperty("pushed_at") Instant pushedAt,
      @Nullable @JsonProperty("license") GHLicense license,
      @Nullable @JsonProperty("language") String language,
      @JsonProperty("size") long size,
      @JsonProperty("stargazers_count") int stargazers,
      @JsonProperty("watchers_count") int watchers,
      @JsonProperty("subscriber_count") int subscriberCount,
      @JsonProperty("forks") int forks,
      @JsonProperty("open_issues") int openIssues) {
    this.id = id;
    this.name = name;
    this.fullName = fullName;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.pushedAt = pushedAt;
    this.license = license;
    this.language = language;
    this.size = size;
    this.stargazers = stargazers;
    this.watchers = watchers;
    this.subscriberCount = subscriberCount;
    this.forks = forks;
    this.openIssues = openIssues;
  }

  @NonNull
  public long getId() {
    return this.id;
  }

  @NonNull
  public String getName() {
    return this.name;
  }

  @NonNull
  public String getFullName() {
    return this.fullName;
  }

  @NonNull
  public Instant getCreatedAt() {
    return this.createdAt;
  }

  @NonNull
  public Instant getUpdatedAt() {
    return this.updatedAt;
  }

  @Nullable
  public Instant getPushedAt() {
    return this.pushedAt;
  }

  @Nullable
  public GHLicense getLicense() {
    return this.license;
  }

  @Nullable
  public String getLanguage() {
    return this.language;
  }

  public long getSize() {
    return this.size;
  }

  public int getStargazers() {
    return this.stargazers;
  }

  public int getWatchers() {
    return this.watchers;
  }

  public int getSubscriberCount() {
    return this.subscriberCount;
  }

  public int getForks() {
    return this.forks;
  }

  public int getOpenIssues() {
    return this.openIssues;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof GHRepository)) {
      return false;
    }
    GHRepository that = (GHRepository) o;
    return this.id == that.id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }
}

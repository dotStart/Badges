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
 * Represents a release which has been attached to a given repository.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
public class GHRelease implements Serializable {

  private final long id;
  private final String tagName;
  private final String name;
  private final boolean draft;
  private final boolean prerelease;
  private final Instant createdAt;
  private final Instant publishedAt;

  @JsonCreator
  public GHRelease(
      @JsonProperty(value = "id", required = true) long id,
      @NonNull @JsonProperty(value = "tag_name", required = true) String tagName,
      @Nullable @JsonProperty("name") String name,
      @JsonProperty("draft") boolean draft,
      @JsonProperty("prerelease") boolean prerelease,
      @NonNull @JsonProperty(value = "created_at", required = true) Instant createdAt,
      @NonNull @JsonProperty(value = "published_at", required = true) Instant publishedAt) {
    this.id = id;
    this.tagName = tagName;
    this.name = name;
    this.draft = draft;
    this.prerelease = prerelease;
    this.createdAt = createdAt;
    this.publishedAt = publishedAt;
  }

  public long getId() {
    return this.id;
  }

  @NonNull
  public String getTagName() {
    return this.tagName;
  }

  @Nullable
  public String getName() {
    return this.name;
  }

  public boolean isDraft() {
    return this.draft;
  }

  public boolean isPrerelease() {
    return this.prerelease;
  }

  @NonNull
  public Instant getCreatedAt() {
    return this.createdAt;
  }

  @NonNull
  public Instant getPublishedAt() {
    return this.publishedAt;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof GHRelease)) {
      return false;
    }
    GHRelease ghRelease = (GHRelease) o;
    return this.id == ghRelease.id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }
}

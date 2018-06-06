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
package tv.dotstart.badge.service.gitlab;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * Represents an arbitrary Gitlab project.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
public class GLProject implements Serializable {

  private final long id;
  private final String name;
  private final int openIssues;
  private final int forks;
  private final int stars;
  private final Instant createdAt;
  private final Instant lastActivityAt;

  @JsonCreator
  public GLProject(
      @JsonProperty(value = "id", required = true) long id,
      @NonNull @JsonProperty(value = "name", required = true) String name,
      @JsonProperty("open_issue_count") int openIssues,
      @JsonProperty("forks_count") int forks,
      @JsonProperty("star_count") int stars,
      @NonNull @JsonProperty(value = "created_at", required = true) Instant createdAt,
      @Nullable @JsonProperty("last_activity_at") Instant lastActivityAt) {
    this.id = id;
    this.name = name;
    this.openIssues = openIssues;
    this.forks = forks;
    this.stars = stars;
    this.createdAt = createdAt;
    this.lastActivityAt = lastActivityAt;
  }

  public long getId() {
    return this.id;
  }

  @NonNull
  public String getName() {
    return this.name;
  }

  public int getOpenIssues() {
    return this.openIssues;
  }

  public int getForks() {
    return this.forks;
  }

  public int getStars() {
    return this.stars;
  }

  @NonNull
  public Instant getCreatedAt() {
    return this.createdAt;
  }

  @NonNull
  public Instant getLastActivityAt() {
    return this.lastActivityAt;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof GLProject)) {
      return false;
    }
    GLProject glProject = (GLProject) o;
    return this.id == glProject.id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }
}

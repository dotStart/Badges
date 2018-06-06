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
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
public class GHUser implements Serializable {

  private final long id;
  private final String avatarUrl;
  private final String gravatarId;
  private final String name;
  private final String company;
  private final String location;
  private final String email;
  private final boolean hireable;
  private final int publicRepositories;
  private final int publicGists;
  private final int followers;
  private final int following;
  private final Instant createdAt;
  private final Instant updatedAt;

  @JsonCreator
  public GHUser(
      @JsonProperty(value = "id", required = true) long id,
      @Nullable @JsonProperty("avatar_url") String avatarUrl,
      @Nullable @JsonProperty("gravatar_id") String gravatarId,
      @NonNull @JsonProperty(value = "name", required = true) String name,
      @Nullable @JsonProperty("company") String company,
      @Nullable @JsonProperty("location") String location,
      @Nullable @JsonProperty("email") String email,
      @JsonProperty("hireable") boolean hireable,
      @JsonProperty("public_repos") int publicRepositories,
      @JsonProperty("public_gists") int publicGists,
      @JsonProperty("followers") int followers,
      @JsonProperty("following") int following,
      @NonNull @JsonProperty(value = "created_at", required = true) Instant createdAt,
      @NonNull @JsonProperty(value = "updated_at", required = true) Instant updatedAt) {
    this.id = id;
    this.avatarUrl = avatarUrl;
    this.gravatarId = gravatarId;
    this.name = name;
    this.company = company;
    this.location = location;
    this.email = email;
    this.hireable = hireable;
    this.publicRepositories = publicRepositories;
    this.publicGists = publicGists;
    this.followers = followers;
    this.following = following;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public long getId() {
    return this.id;
  }

  @Nullable
  public String getAvatarUrl() {
    return this.avatarUrl;
  }

  @Nullable
  public String getGravatarId() {
    return this.gravatarId;
  }

  @NonNull
  public String getName() {
    return this.name;
  }

  @Nullable
  public String getCompany() {
    return this.company;
  }

  @Nullable
  public String getLocation() {
    return this.location;
  }

  @Nullable
  public String getEmail() {
    return this.email;
  }

  public boolean isHireable() {
    return this.hireable;
  }

  public int getPublicRepositories() {
    return this.publicRepositories;
  }

  public int getPublicGists() {
    return this.publicGists;
  }

  public int getFollowers() {
    return this.followers;
  }

  public int getFollowing() {
    return this.following;
  }

  @NonNull
  public Instant getCreatedAt() {
    return this.createdAt;
  }

  @NonNull
  public Instant getUpdatedAt() {
    return this.updatedAt;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof GHUser)) {
      return false;
    }
    GHUser ghUser = (GHUser) o;
    return this.id == ghUser.id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }
}

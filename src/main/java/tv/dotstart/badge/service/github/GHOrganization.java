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
 * Represents an organization profile.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
public class GHOrganization implements Serializable {

  private final long id;
  private final String login;
  private final String name;
  private final String description;
  private final String company;
  private final String location;
  private final String email;
  private final int publicRepositories;
  private final int publicGists;
  private final int followers;
  private final int following;
  private final int collaborators;
  private final Instant createdAt;

  @JsonCreator
  public GHOrganization(
      @NonNull @JsonProperty(value = "id", required = true) long id,
      @NonNull @JsonProperty(value = "login", required = true) String login,
      @NonNull @JsonProperty(value = "name", required = true) String name,
      @Nullable @JsonProperty("description") String description,
      @Nullable @JsonProperty("company") String company,
      @Nullable @JsonProperty("location") String location,
      @Nullable @JsonProperty("email") String email,
      @JsonProperty("public_repos") int publicRepositories,
      @JsonProperty("public_gists") int publicGists,
      @JsonProperty("followers") int followers,
      @JsonProperty("following") int following,
      @JsonProperty("collaborators") int collaborators,
      @NonNull @JsonProperty(value = "created_at", required = true) Instant createdAt) {
    this.id = id;
    this.login = login;
    this.name = name;
    this.description = description;
    this.company = company;
    this.location = location;
    this.email = email;
    this.publicRepositories = publicRepositories;
    this.publicGists = publicGists;
    this.followers = followers;
    this.following = following;
    this.collaborators = collaborators;
    this.createdAt = createdAt;
  }

  @NonNull
  public long getId() {
    return this.id;
  }

  @NonNull
  public String getLogin() {
    return this.login;
  }

  @NonNull
  public String getName() {
    return this.name;
  }

  @Nullable
  public String getDescription() {
    return this.description;
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

  public int getCollaborators() {
    return this.collaborators;
  }

  @NonNull
  public Instant getCreatedAt() {
    return this.createdAt;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof GHOrganization)) {
      return false;
    }
    GHOrganization that = (GHOrganization) o;
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

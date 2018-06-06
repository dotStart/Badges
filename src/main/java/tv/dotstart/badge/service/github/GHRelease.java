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

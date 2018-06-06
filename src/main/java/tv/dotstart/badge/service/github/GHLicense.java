package tv.dotstart.badge.service.github;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Objects;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * Represents the license information which has been attached to a given repository.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
public class GHLicense implements Serializable {

  private final String key;
  private final String name;
  private final String spdxId;

  @JsonCreator
  public GHLicense(
      @NonNull @JsonProperty(value = "key", required = true) String key,
      @NonNull @JsonProperty(value = "name", required = true) String name,
      @Nullable @JsonProperty("spdx_id") String spdxId) {
    this.key = key;
    this.name = name;
    this.spdxId = spdxId;
  }

  @NonNull
  public String getKey() {
    return this.key;
  }

  @NonNull
  public String getName() {
    return this.name;
  }

  @Nullable
  public String getSpdxId() {
    return this.spdxId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof GHLicense)) {
      return false;
    }
    GHLicense ghLicense = (GHLicense) o;
    return Objects.equals(this.key, ghLicense.key) &&
        Objects.equals(this.name, ghLicense.name) &&
        Objects.equals(this.spdxId, ghLicense.spdxId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.key, this.name, this.spdxId);
  }
}

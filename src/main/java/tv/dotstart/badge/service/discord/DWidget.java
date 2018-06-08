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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * Provides a representation of the Widget data.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
public class DWidget implements Serializable {

  private final String id;
  private final List<Channel> channels;
  private final List<Member> members;

  @JsonCreator
  public DWidget(
      @NonNull @JsonProperty(value = "id", required = true) String id,
      @NonNull @JsonProperty(value = "channels", required = true) List<Channel> channels,
      @NonNull @JsonProperty(value = "members", required = true) List<Member> members) {
    this.id = id;
    this.channels = channels;
    this.members = members;
  }

  @NonNull
  public String getId() {
    return this.id;
  }

  @NonNull
  public List<Channel> getChannels() {
    return Collections.unmodifiableList(this.channels);
  }

  @NonNull
  public List<Member> getMembers() {
    return Collections.unmodifiableList(this.members);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DWidget)) {
      return false;
    }
    DWidget dWidget = (DWidget) o;
    return Objects.equals(this.id, dWidget.id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

  /**
   * <p>Provides a list of valid member statuses.</p>
   *
   * <p>Note that offline users will generally be omitted from the listing and as such there is no
   * offline state.</p>
   */

  public enum MemberStatus {
    ONLINE,
    IDLE,
    DND
  }

  public static class Channel implements Serializable {

    private final String id;
    private final String name;
    private final int position;

    @JsonCreator
    public Channel(
        @NonNull @JsonProperty(value = "id", required = true) String id,
        @NonNull @JsonProperty(value = "name", required = true) String name,
        @JsonProperty(value = "position", required = true) int position) {
      this.id = id;
      this.name = name;
      this.position = position;
    }

    @NonNull
    public String getId() {
      return this.id;
    }

    @NonNull
    public String getName() {
      return this.name;
    }

    public int getPosition() {
      return this.position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Channel)) {
        return false;
      }
      Channel channel = (Channel) o;
      return Objects.equals(this.id, channel.id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
      return Objects.hash(this.id);
    }
  }

  public static class Member implements Serializable {

    private final String id;
    private final String name;
    private final String discriminator;
    private final String avatarId;
    private final String avatarUrl;
    private final MemberStatus status;

    // Voice Status
    private final String currentChannelId;
    private final boolean deafened;
    private final boolean muted;
    private final boolean suppressed;
    private final boolean selfDeafened;
    private final boolean selfMuted;

    public Member(
        @NonNull @JsonProperty(value = "id", required = true) String id,
        @NonNull @JsonProperty(value = "username", required = true) String name,
        @NonNull @JsonProperty(value = "discriminator", required = true) String discriminator,
        @Nullable @JsonProperty("avatar") String avatarId,
        @Nullable @JsonProperty("avatar_url") String avatarUrl,
        @NonNull @JsonProperty(value = "status", required = true) MemberStatus status,
        // Voice Status
        @JsonProperty("channel_id") String currentChannelId,
        @JsonProperty("deaf") boolean deafened,
        @JsonProperty("mute") boolean muted,
        @JsonProperty("suppressed") boolean suppressed,
        @JsonProperty("self_deaf") boolean selfDeafened,
        @JsonProperty("self_mute") boolean selfMuted) {
      this.id = id;
      this.name = name;
      this.discriminator = discriminator;
      this.avatarId = avatarId;
      this.avatarUrl = avatarUrl;
      this.status = status;

      // Voice Status
      this.currentChannelId = currentChannelId;
      this.deafened = deafened;
      this.muted = muted;
      this.suppressed = suppressed;
      this.selfDeafened = selfDeafened;
      this.selfMuted = selfMuted;
    }

    @NonNull
    public String getId() {
      return this.id;
    }

    @NonNull
    public String getName() {
      return this.name;
    }

    @NonNull
    public String getDiscriminator() {
      return this.discriminator;
    }

    @Nullable
    public String getAvatarId() {
      return this.avatarId;
    }

    @Nullable
    public String getAvatarUrl() {
      return this.avatarUrl;
    }

    @NonNull
    public MemberStatus getStatus() {
      return this.status;
    }

    @Nullable
    public String getCurrentChannelId() {
      return this.currentChannelId;
    }

    public boolean isDeafened() {
      return this.deafened;
    }

    public boolean isMuted() {
      return this.muted;
    }

    public boolean isSuppressed() {
      return this.suppressed;
    }

    public boolean isSelfDeafened() {
      return this.selfDeafened;
    }

    public boolean isSelfMuted() {
      return this.selfMuted;
    }
  }
}

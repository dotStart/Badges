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
package tv.dotstart.badge.configuration;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.lang.NonNull;

/**
 * Configures the caching backend to enable persistent external storage of values through Spring's
 * integrated annotation based cache system.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
@Configuration
public class CacheConfiguration {

  private final Duration dynamicCacheDuration;

  public CacheConfiguration(
      @NonNull @Value("${badge.cache.dynamic:PT1H}") String dynamicCacheDuration) {
    this.dynamicCacheDuration = Duration.parse(dynamicCacheDuration);
  }

  /**
   * Provides a customized cache manager implementation which uses an arbitrary Redis server for
   * semi-permanent storage.
   *
   * @param connectionFactory a connection factory.
   * @return a cache manager implementation.
   */
  @Bean
  @NonNull
  public RedisCacheManager cacheManager(@NonNull RedisConnectionFactory connectionFactory) {
    return RedisCacheManager.builder(connectionFactory)
        .cacheDefaults(
            RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(this.dynamicCacheDuration)
        )
        .build();
  }
}

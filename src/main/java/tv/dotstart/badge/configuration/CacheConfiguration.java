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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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

  private static final Logger logger = LogManager.getFormatterLogger(CacheConfiguration.class);

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
  @Profile("!development")
  @Qualifier("cacheManager")
  public RedisCacheManager redisCacheManager(@NonNull RedisConnectionFactory connectionFactory) {
    return RedisCacheManager.builder(connectionFactory)
        .cacheDefaults(
            RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(this.dynamicCacheDuration)
        )
        .build();
  }

  /**
   * Provides a no-op cache manager which causes the application to continuously pull new data in
   * development mode.
   *
   * @return a cache manager implementation.
   */
  @Bean
  @NonNull
  @Profile("development")
  @Qualifier("cacheManager")
  public NoOpCacheManager mockCacheManager() {
    logger.warn("");
    logger.warn("+==============================+");
    logger.warn("| NOOP Cache Manager Enabled   |");
    logger.warn("+------------------------------+");
    logger.warn("| Response performance will be |");
    logger.warn("| degraded significantly and   |");
    logger.warn("| rate limits may be exceeded  |");
    logger.warn("| faster than usual!           |");
    logger.warn("|                              |");
    logger.warn("| Only use this mode during    |");
    logger.warn("| development!                 |");
    logger.warn("+==============================+");
    logger.warn("");

    return new NoOpCacheManager();
  }
}

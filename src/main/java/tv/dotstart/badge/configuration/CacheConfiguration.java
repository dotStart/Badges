package tv.dotstart.badge.configuration;

import java.time.Duration;
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
                .entryTtl(Duration.ofHours(1))
        )
        .build();
  }
}

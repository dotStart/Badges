/*
 * Copyright 2020 Johannes Donath <johannesd@torchmind.com>
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
package tv.dotstart.badge.service.rate

import org.springframework.data.redis.core.ReactiveRedisTemplate
import reactor.core.publisher.Mono
import java.time.Instant

/**
 * Provides a basic redis backed atomic counter.
 *
 * @author [Johannes Donath](mailto:johannesd@torchmind.com)
 * @date 09/05/2020
 */
class RedisReactiveAtomicCounter(
    private val template: ReactiveRedisTemplate<String, String>,
    private val key: () -> String,
    private val expiration: () -> Instant?) : ReactiveAtomicCounter {

  override fun increment(): Mono<Long> {
    val key = this.key()
    val expiration = this.expiration()

    val query = this.template
        .opsForValue()
        .increment(this.key())
    if (expiration == null) {
      return query
    }

    return query
        .flatMap {
          this.template.expireAt(key, expiration)
              .thenReturn(it)
        }
  }

  override fun get() = this.template
      .opsForValue()
      .get(this.key())
      .map { it.toLongOrNull(10) ?: 0 }
}

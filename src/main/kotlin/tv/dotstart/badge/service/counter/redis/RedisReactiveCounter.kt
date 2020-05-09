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
package tv.dotstart.badge.service.counter.redis

import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import reactor.core.publisher.Mono
import tv.dotstart.badge.service.counter.ReactiveCounter
import java.time.Duration
import java.time.Instant

/**
 * Provides a basic redis backed atomic counter.
 *
 * @author [Johannes Donath](mailto:johannesd@torchmind.com)
 * @date 09/05/2020
 */
class RedisReactiveCounter(
    private val template: ReactiveStringRedisTemplate,
    private val scope: String,
    private val resetsEvery: Duration? = null) : ReactiveCounter {

  private val key: String = "counter_$scope"

  private val nextReset: Instant?
    get() = this.resetsEvery?.let {
      val time = System.currentTimeMillis()
      val partOfPeriod = time % it.toMillis()

      val nextReset = time + (it.toMillis() - partOfPeriod)
      return Instant.ofEpochMilli(nextReset)
    }

  override fun increment(): Mono<Long> {
    val nextReset = this.nextReset

    val query = this.template
        .opsForValue()
        .increment(key)

    if (nextReset == null) {
      return query
    }

    return query
        .flatMap {
          this.template.expireAt(key, nextReset)
              .thenReturn(it)
        }
  }

  override fun get() = this.template
      .opsForValue()
      .get(this.key)
      .map { it.toLongOrNull(10) ?: 0 }
}

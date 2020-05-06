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
package tv.dotstart.badge.service.cache.redis

import com.fasterxml.jackson.databind.ObjectReader
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.data.redis.core.ReactiveRedisOperations
import reactor.core.publisher.Mono
import tv.dotstart.badge.service.cache.CacheScope
import java.time.Duration
import kotlin.reflect.KClass

/**
 * @author [Johannes Donath](mailto:johannesd@torchmind.com)
 * @date 06/05/2020
 */
class RedisCacheScope<T : Any>(
    private val scope: String,
    private val operations: ReactiveRedisOperations<String, String>,
    private val type: KClass<T>,
    private val timeout: Duration) : CacheScope<T> {

  private val reader: ObjectReader
  private val writer: ObjectWriter

  init {
    val mapper = jacksonObjectMapper()
        .findAndRegisterModules()

    this.reader = mapper.readerFor(this.type.java)
    this.writer = mapper.writerFor(this.type.java)
  }

  override fun get(key: String, generator: Mono<T>) =
      this.operations.opsForValue()["${scope}_$key"]
          .map { reader.readValue<T>(it) }
          .switchIfEmpty(
              generator.doOnNext {
                val encoded = this.writer.writeValueAsString(it)
                this.operations.opsForValue().set("${scope}_$key", encoded, this.timeout)
                    .subscribe()
              }
          )
}

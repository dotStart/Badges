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

import reactor.core.publisher.Mono

/**
 * Provides a reactive counter implementation.
 *
 * @author [Johannes Donath](mailto:johannesd@torchmind.com)
 * @date 09/05/2020
 */
interface ReactiveAtomicCounter {

  /**
   * Increments the value within the counter and returns the respective result.
   */
  fun increment(): Mono<Long>

  /**
   * Retrieves the current value of the counter.
   */
  fun get(): Mono<Long>
}
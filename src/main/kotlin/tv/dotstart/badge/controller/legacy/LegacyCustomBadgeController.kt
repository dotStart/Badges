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
package tv.dotstart.badge.controller.legacy

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tv.dotstart.badge.configuration.properties.annotations.ConditionalOnLegacy
import tv.dotstart.badge.controller.v1.badges.CustomBadgeController
import tv.dotstart.badge.service.counter.ReactiveCounter

/**
 * Provides legacy versions of the custom badge endpoints.
 *
 * @author [Johannes Donath](mailto:johannesd@torchmind.com)
 * @date 10/05/2020
 */
@RestController
@ConditionalOnLegacy
@Deprecated("Replaced by v1 badge endpoints")
@RequestMapping("/custom/{color}/{title}/{value}")
class LegacyCustomBadgeController(
    private val legacyCounter: ReactiveCounter,
    private val target: CustomBadgeController) {

  @GetMapping
  fun customBadge(@PathVariable color: String, @PathVariable title: String,
                  @PathVariable value: String) = this.legacyCounter.increment()
      .then(this.target.custom(color, title, value))
}

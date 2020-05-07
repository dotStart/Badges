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
package tv.dotstart.badge.service.badge

import org.springframework.stereotype.Component
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping
import tv.dotstart.badge.service.badge.annotation.BadgeCategory
import tv.dotstart.badge.service.badge.annotation.BadgeMapping
import kotlin.reflect.full.findAnnotation

/**
 * Automatically assembles an index of badges, their paths and parameters.
 *
 * @author [Johannes Donath](mailto:johannesd@torchmind.com)
 * @date 07/05/2020
 */
@Component
class BadgeIndex(private val mapping: RequestMappingHandlerMapping) : Iterable<BadgeScope> {

  companion object {
    private val parameterPattern = Regex("""\{([^}]+)\}""")
  }

  private val scopes: List<BadgeScope> by lazy {
    mapping.handlerMethods
        .filter { (_, handler) ->
          handler.hasMethodAnnotation(BadgeMapping::class.java)
        }
        .mapNotNull { (path, handler) ->
          handler.beanType.kotlin.findAnnotation<BadgeCategory>()
              ?.let { category ->
                val pattern = path.patternsCondition
                    .patterns
                    .firstOrNull()
                    ?.toString()

                pattern?.let { category to it }
              }
        }
        .groupBy(Pair<BadgeCategory, String>::first)
        .map { (category, paths) ->
          val pathList = paths.map(Pair<BadgeCategory, String>::second)
          val parameters = pathList
              .flatMap {
                parameterPattern.findAll(it)
                    .map { it.groupValues[1] }
                    .toList()
              }
              .distinct()
          val parameterDefaults = category.parameters
              .map { it.name to it.value }
              .toMap()

          BadgeScope(category.name,
                     category.tags.toList(),
                     parameters,
                     parameterDefaults,
                     pathList)
        }
  }

  override fun iterator() = this.scopes.iterator()
}

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
package tv.dotstart.badge.util

/**
 * Provides various default colors as exposed by the API.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 * @date 06/05/2020
 */
enum class Color(val value: Int, val textValue: Int = 0xFFFFFF) {

  RED(0xf44336),
  PINK(0xE91E63),
  PURPLE(0x9C27B0),
  DEEP_PURPLE(0x673AB7),
  INDIGO(0x3F51B5),
  BLUE(0x2196F3),
  LIGHT_BLUE(0x03A9F4, 0x000000),
  CYAN(0x00BCD4, 0x000000),
  TEAL(0x009688),
  GREEN(0x4CAF50),
  LIGHT_GREEN(0x8BC34A, 0x000000),
  LIME(0xCDDC39, 0x000000),
  YELLOW(0xFFEB3B, 0x000000),
  AMBER(0xFFC107, 0x000000),
  ORANGE(0xFF9800),
  DEEP_ORANGE(0xFF5722),
  BROWN(0x795548),
  GREY(0x9E9E9E),
  BLUE_GREY(0x607D8B),

  // Named Values
  DEFAULT(BLUE.value, BLUE.textValue),
  FALLBACK(GREY.value, GREY.textValue),
  PENDING(TEAL.value, TEAL.textValue),
  SUCCESS(GREEN.value, GREEN.textValue),
  WARNING(ORANGE.value, ORANGE.textValue),
  FAILURE(RED.value, RED.textValue);

  val hexValue: String
    get() = "#%06X".format(this.value)

  val hexTextValue: String
    get() = "#%06X".format(this.textValue)
}

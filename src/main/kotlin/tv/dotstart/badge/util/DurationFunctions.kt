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

import java.time.Duration
import java.time.Period

/**
 * Provides functions which simplify the interaction with durations.
 *
 * @author [Johannes Donath](mailto:johannesd@torchmind.com)
 * @date 09/05/2020
 */

fun Duration.toHumanReadableString(): String {
  if (this.toDaysPart() > Int.MAX_VALUE) {
    return "forever"
  }

  val period = Period.ofDays(this.toDaysPart().toInt())
  return when {
    period.years > 0 -> "${period.years} years"
    this.toDaysPart() > 0 -> "${toDaysPart()} days"
    this.toHoursPart() > 0 -> "${toHoursPart()} hours"
    this.toMinutesPart() > 0 -> "${toMinutesPart()} minutes"
    this.toSecondsPart() > 0 -> "${toSecondsPart()} seconds"
    else -> "now"
  }
}

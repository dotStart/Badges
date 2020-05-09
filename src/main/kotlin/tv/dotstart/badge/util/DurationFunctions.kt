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
import java.time.OffsetDateTime
import java.time.Period

/**
 * Provides functions which simplify the interaction with durations.
 *
 * @author [Johannes Donath](mailto:johannesd@torchmind.com)
 * @date 09/05/2020
 */

/**
 * Retrieves a human readable string which represents the amount of time which has passed since a
 * given date and time.
 */
val OffsetDateTime.passedTimeSince: String
  get() {
    val inputDate = this.toLocalDate()

    val now = OffsetDateTime.now()
    val nowDate = now.toLocalDate()

    val period = Period.between(inputDate, nowDate)
    if (period.years > 0) {
      return "${period.years} years"
    }

    val duration = Duration.between(this, now)
    return when {
      duration.toDaysPart() > 0 -> "${duration.toDaysPart()} days"
      duration.toHoursPart() > 0 -> "${duration.toHoursPart()} hours"
      duration.toMinutesPart() > 0 -> "${duration.toMinutesPart()} minutes"
      duration.toSecondsPart() > 0 -> "${duration.toSecondsPart()} seconds"
      else -> "now"
    }
  }

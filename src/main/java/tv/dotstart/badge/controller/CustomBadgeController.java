/*
 * Copyright 2018 Johannes Donath <johannesd@torchmind.com>
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
package tv.dotstart.badge.controller;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tv.dotstart.badge.badge.Badge;

/**
 * Provides badges which are defined directly via the request URL.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
@Controller
@RequestMapping("/custom")
public class CustomBadgeController {

  /**
   * Generates a badge based on a user specified color, title and value (e.g. via the request URL).
   *
   * @param color an arbitrary color.
   * @param title an arbitrary title.
   * @param value an arbitrary badge value.
   * @return the resulting badge.
   */
  @NonNull
  @RequestMapping("/{color}/{title}/{value}")
  public Badge custom(
      @NonNull @PathVariable("color") Badge.Color color,
      @NonNull @PathVariable("title") String title,
      @NonNull @PathVariable("value") String value) throws Exception {
    return new Badge(
        title,
        value,
        color
    );
  }
}

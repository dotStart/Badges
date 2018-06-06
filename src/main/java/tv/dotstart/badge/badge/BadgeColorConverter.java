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
package tv.dotstart.badge.badge;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import tv.dotstart.badge.badge.Badge.Color;

/**
 * <p>Converts strings (typically from request or path parameters) into badge colors.</p>
 *
 * <p>To give the request parameters a slightly more natural feel, we'll treat underscores and
 * hyphens equally and ignore the input casing.</p>
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
@Component
public class BadgeColorConverter implements Converter<String, Color> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Color convert(@NonNull String source) {
    source = source.toUpperCase();
    source = StringUtils.replace(source, "-", "_");

    return Color.valueOf(source);
  }
}

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

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * <p>Represents a renderable badge.</p>
 *
 * <p>The purpose of this implementation is to provide an implementation agnostic representation of
 * the badge which will ultimately be served to the requesting party.</p>
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
public class Badge implements Serializable {

  private final String title;
  private final String value;
  private final Color color;

  public Badge(@NonNull String title, @NonNull String value, @NonNull Color color) {
    this.title = title;
    this.value = value;
    this.color = color;
  }

  public static <I> Badge create(
      @NonNull String title,
      @NonNull Optional<I> input,
      @NonNull Mapper<I, String> valueMapper,
      @NonNull Mapper<I, Color> colorMapper,
      @NonNull String fallbackValue,
      @NonNull Color fallbackColor) {
    return input
        .flatMap((in) -> {
          try {
            return Optional.ofNullable(valueMapper.map(in))
                .map((val) -> Map.entry(in, val));
          } catch (Throwable ex) {
            throw new RuntimeException("Could not convert input to badge", ex);
          }
        })
        .map((val) -> {
          Color color;
          try {
            color = colorMapper.map(val.getKey());
          } catch (Throwable ex) {
            throw new RuntimeException("Could not convert input to badge", ex);
          }

          return new Badge(
              title,
              val.getValue(),
              color
          );
        })
        .orElseGet(() -> new Badge(
            title,
            fallbackValue,
            fallbackColor
        ));
  }

  @NonNull
  public static <I> Badge create(
      @NonNull String title,
      @NonNull Color mainColor,
      @NonNull Optional<I> input,
      @NonNull Mapper<I, String> mapper,
      @NonNull String fallbackValue,
      @NonNull Color fallbackColor) {
    return create(title, input, mapper, (in) -> mainColor, fallbackValue, fallbackColor);
  }

  @NonNull
  public static <I> Badge create(
      @NonNull String title,
      @NonNull Color mainColor,
      @NonNull Optional<I> input,
      @NonNull Mapper<I, String> mapper,
      @NonNull String fallbackValue) {
    return create(title, mainColor, input, mapper, fallbackValue, Color.FALLBACK);
  }

  @NonNull
  public static <I> Badge create(
      @NonNull String title,
      @NonNull Optional<I> input,
      @NonNull Mapper<I, String> mapper,
      @NonNull String fallbackValue) {
    return create(title, Color.DEFAULT, input, mapper, fallbackValue);
  }

  @NonNull
  public String getTitle() {
    return this.title;
  }

  public int getTitleWidth() {
    return this.title.length() * 6;
  }

  @NonNull
  public String getValue() {
    return this.value;
  }

  public int getValueWidth() {
    return this.value.length() * 6;
  }

  @NonNull
  public Color getColor() {
    return this.color;
  }

  @NonNull
  public String getBackgroundColorCode() {
    return String.format("#%06X", this.color.backgroundColor);
  }

  @NonNull
  public String getTextColorCode() {
    return String.format("#%06X", this.color.textColor);
  }

  /**
   * Provides a list of valid badge colors (and named aliases for quick access).
   */
  public enum Color {
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
    DEFAULT(BLUE.backgroundColor, BLUE.textColor),
    FALLBACK(GREY.backgroundColor, GREY.textColor),
    PENDING(TEAL.backgroundColor, TEAL.textColor),
    SUCCESS(GREEN.backgroundColor, GREEN.textColor),
    WARNING(ORANGE.backgroundColor, ORANGE.textColor),
    FAILURE(RED.backgroundColor, RED.textColor);

    private final int backgroundColor;
    private final int textColor;

    Color(int backgroundColor) {
      this(backgroundColor, 0xFFFFFF);
    }

    Color(int backgroundColor, int textColor) {
      this.backgroundColor = backgroundColor;
      this.textColor = textColor;
    }

    public int getBackgroundColor() {
      return this.backgroundColor;
    }

    public int getTextColor() {
      return this.textColor;
    }
  }

  @FunctionalInterface
  public interface Mapper<IN, OUT> {

    @Nullable
    OUT map(IN input) throws Throwable;
  }
}

package tv.dotstart.badge.badge;

import java.io.Serializable;
import org.springframework.lang.NonNull;

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
  public interface Factory {

    @NonNull
    Badge create() throws Exception;
  }
}

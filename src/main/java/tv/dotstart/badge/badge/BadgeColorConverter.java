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

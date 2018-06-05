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

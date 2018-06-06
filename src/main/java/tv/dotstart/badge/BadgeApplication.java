package tv.dotstart.badge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.lang.NonNull;

/**
 * Provides a root configuration and JVM Entry Point to the application.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
@EnableCaching
@SpringBootApplication
public class BadgeApplication {

  /**
   * JVM Entry Point
   *
   * @param arguments an array of command line arguments.
   */
  public static void main(@NonNull String[] arguments) {
    SpringApplication.run(BadgeApplication.class, arguments);
  }
}

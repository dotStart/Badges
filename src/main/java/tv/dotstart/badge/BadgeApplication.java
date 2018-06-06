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

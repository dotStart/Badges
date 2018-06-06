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

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Callable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tv.dotstart.badge.badge.Badge;
import tv.dotstart.badge.service.gitlab.Gitlab;

/**
 * Provides badges which provide basic information about the status of a GitLab project.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
@Controller
@RequestMapping("/gitlab")
public class GitlabBadgeController {

  private final Gitlab api;

  public GitlabBadgeController(@NonNull Gitlab api) {
    this.api = api;
  }

  /**
   * Creates a badge which displays the time since the last logged activity of a given Gitlab
   * project.
   *
   * @param projectId a project identifier.
   * @return a badge callable.
   */
  @NonNull
  @ModelAttribute("badge")
  @RequestMapping("/project/{projectId}/activity")
  public Callable<Badge> activity(@NonNull @PathVariable("projectId") String projectId) {
    return () -> Badge.create(
        "last activity",
        this.api.getProject(projectId),
        (proj) -> {
          var lastActivity = proj.getLastActivityAt();

          if (lastActivity == null) {
            return "unknown";
          }

          var duration = Duration.between(lastActivity, Instant.now());

          if (duration.toDaysPart() > 0) {
            return duration.toDaysPart() + " days ago";
          } else if (duration.toHoursPart() > 0) {
            return duration.toHoursPart() + " hours ago";
          } else if (duration.toMinutesPart() > 0) {
            return duration.toMinutesPart() + " minutes ago";
          } else {
            return "just now";
          }
        },
        "no such project"
    );
  }

  /**
   * Creates a badge which displays the total amount of forks of a given Gitlab project.
   *
   * @param projectId a project identifier.
   * @return a badge callable.
   */
  @NonNull
  @ModelAttribute("badge")
  @RequestMapping("/project/{projectId}/forks")
  public Callable<Badge> forks(@NonNull @PathVariable("projectId") String projectId) {
    return () -> Badge.create(
        "forks",
        this.api.getProject(projectId),
        (proj) -> Integer.toString(proj.getForks()),
        "no such project"
    );
  }

  /**
   * Creates a badge which displays the total amount of open issues in a given Gitlab project.
   *
   * @param projectId a project identifier.
   * @return a badge callable.
   */
  @NonNull
  @ModelAttribute("badge")
  @RequestMapping("/project/{projectId}/issues")
  public Callable<Badge> issues(@NonNull @PathVariable("projectId") String projectId) {
    return () -> Badge.create(
        "issues",
        this.api.getProject(projectId),
        (proj) -> Integer.toString(proj.getOpenIssues()),
        "no such project"
    );
  }

  /**
   * Creates a badge which displays the total amount of stars a given Gitlab project has received.
   *
   * @param projectId a project identifier.
   * @return a badge callable.
   */
  @NonNull
  @ModelAttribute("badge")
  @RequestMapping("/project/{projectId}/stars")
  public Callable<Badge> stars(@NonNull @PathVariable("projectId") String projectId) {
    return () -> Badge.create(
        "stars",
        this.api.getProject(projectId),
        (proj) -> Integer.toString(proj.getStars()),
        "no such project"
    );
  }
}
